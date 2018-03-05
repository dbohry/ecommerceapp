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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

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
                            .baseUrl("http://10.0.2.2:8080")
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    LoginClient loginClient = retrofit.create(LoginClient.class);
                    Call<LoginDTO> call = loginClient.login(email, password);

                    call.enqueue(new Callback<LoginDTO>() {
                        @Override
                        public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                            LoginDTO authentication = response.body();
                            Intent userAreaIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                            if (authentication != null && authentication.getUsuario() != null)
                                userAreaIntent.putExtra("username", authentication.getUsuario().getNome());
                            LoginActivity.this.startActivity(userAreaIntent);
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
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

    }
}
