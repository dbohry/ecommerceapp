package ecommerce.qgdagraciela.com.ecommerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ecommerce.qgdagraciela.com.ecommerceapp.clients.LoginClient;
import ecommerce.qgdagraciela.com.ecommerceapp.dtos.LoginDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static String URL = "https://ecommerce-tcc.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etSenha);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegister = (Button) findViewById(R.id.bSalvar);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if (email.isEmpty())
                    etEmail.setError("Por favor informe o email");

                if (password.isEmpty())
                    etPassword.setError("Por favor informe a senha");

                if (!email.isEmpty() && !password.isEmpty()) {
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    LoginClient loginClient = retrofit.create(LoginClient.class);
                    Call<LoginDTO> call = loginClient.login(email, password);

                    call.enqueue(new Callback<LoginDTO>() {
                        @Override
                        public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                            LoginDTO authentication = response.body();
                            if (authentication == null)
                                Toast.makeText(LoginActivity.this, "Verifique o email e senha e tente novamente", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                if (authentication != null && authentication.getUsuario() != null) {
                                    intent.putExtra("id", String.valueOf(authentication.getUsuario().getId()));
                                    intent.putExtra("nome", authentication.getUsuario().getNome());
                                    intent.putExtra("cpf", authentication.getUsuario().getCpf());
                                    intent.putExtra("endereco", authentication.getUsuario().getEndereco());
                                    intent.putExtra("cidade", authentication.getUsuario().getCidade());
                                    intent.putExtra("estado", authentication.getUsuario().getEstado());
                                    intent.putExtra("telefone", authentication.getUsuario().getTelefone());
                                    intent.putExtra("email", authentication.getUsuario().getEmail());
                                    intent.putExtra("token", authentication.getToken());
                                }

                                LoginActivity.this.startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginDTO> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }
}
