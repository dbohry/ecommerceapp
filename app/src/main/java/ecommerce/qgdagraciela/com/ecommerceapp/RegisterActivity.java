package ecommerce.qgdagraciela.com.ecommerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ecommerce.qgdagraciela.com.ecommerceapp.clients.RegisterClient;
import ecommerce.qgdagraciela.com.ecommerceapp.dtos.UsuarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ecommerce.qgdagraciela.com.ecommerceapp.LoginActivity.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etCpf = (EditText) findViewById(R.id.etCpf);
        final EditText etNome = (EditText) findViewById(R.id.etNome);
        final EditText etEndereco = (EditText) findViewById(R.id.etEndereco);
        final EditText etEstado = (EditText) findViewById(R.id.etEstado);
        final EditText etCidade = (EditText) findViewById(R.id.etCidade);
        final EditText etTelefone = (EditText) findViewById(R.id.etTelefone);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etSenha);

        final Button bRegister = (Button) findViewById(R.id.bSalvar);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String cpf = etCpf.getText().toString();
                final String endereco = etEndereco.getText().toString();
                final String estado = etEstado.getText().toString();
                final String cidade = etCidade.getText().toString();
                final String telefone = etTelefone.getText().toString();
                final String nome = etNome.getText().toString();

                validate(email, password, nome, etEmail, etPassword, etNome);

                UsuarioDTO dto = createDto(email, password, cpf, endereco, estado, cidade, telefone, nome);

                if (!email.isEmpty() && !password.isEmpty() && !nome.isEmpty()) {
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    RegisterClient registerClient = retrofit.create(RegisterClient.class);
                    Call<UsuarioDTO> call = registerClient.register(dto);

                    call.enqueue(new Callback<UsuarioDTO>() {
                        @Override
                        public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                            UsuarioDTO usuarioCriado = response.body();
                            Toast.makeText(RegisterActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Falha ao cadastrar novo usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private UsuarioDTO createDto(String email, String password, String cpf, String endereco, String estado, String cidade, String telefone, String nome) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCidade(cidade);
        dto.setCpf(cpf);
        dto.setEmail(email);
        dto.setEndereco(endereco);
        dto.setEstado(estado);
        dto.setNome(nome);
        dto.setSenha(password);
        dto.setTelefone(telefone);
        return dto;
    }

    private void validate(String email, String password, String nome, EditText etEmail, EditText etPassword, EditText etNome) {
        if (email.isEmpty())
            etEmail.setError("Por favor informe o email");

        if (password.isEmpty())
            etPassword.setError("Por favor informe a senha");

        if (nome.isEmpty())
            etNome.setError("Por favor informe o nome");
    }
}
