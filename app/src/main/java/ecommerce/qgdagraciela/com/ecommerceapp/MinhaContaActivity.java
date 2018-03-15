package ecommerce.qgdagraciela.com.ecommerceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ecommerce.qgdagraciela.com.ecommerceapp.clients.LoginClient;
import ecommerce.qgdagraciela.com.ecommerceapp.clients.UsuarioClient;
import ecommerce.qgdagraciela.com.ecommerceapp.dtos.LoginDTO;
import ecommerce.qgdagraciela.com.ecommerceapp.dtos.UsuarioDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ecommerce.qgdagraciela.com.ecommerceapp.LoginActivity.URL;

public class MinhaContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);

        final EditText etCpf = (EditText) findViewById(R.id.etCpfEdit);
        final EditText etNome = (EditText) findViewById(R.id.etNomeEdit);
        final EditText etEndereco = (EditText) findViewById(R.id.etEnderecoEdit);
        final EditText etEstado = (EditText) findViewById(R.id.etEstadoEdit);
        final EditText etCidade = (EditText) findViewById(R.id.etCidadeEdit);
        final EditText etTelefone = (EditText) findViewById(R.id.etTelefoneEdit);
        final EditText etEmail = (EditText) findViewById(R.id.etEmailEdit);
        final EditText etPassword = (EditText) findViewById(R.id.etSenhaEdit);
        final Button bSalvar = (Button) findViewById(R.id.bSalvarEdit);
        final Button bRemover = (Button) findViewById(R.id.bRemover);

        final Bundle extras = getIntent().getExtras();

        String cpf = extras.getString("cpf");
        String nome = extras.getString("nome");
        String endereco = extras.getString("endereco");
        String estado = extras.getString("estado");
        String cidade = extras.getString("cidade");
        String telefone = extras.getString("telefone");
        String email = extras.getString("email");

        etCpf.setText(cpf);
        etNome.setText(nome);
        etEndereco.setText(endereco);
        etEstado.setText(estado);
        etCidade.setText(cidade);
        etTelefone.setText(telefone);
        etEmail.setText(email);

        bSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String cpf = etCpf.getText().toString();
                final String endereco = etEndereco.getText().toString();
                final String estado = etEstado.getText().toString();
                final String cidade = etCidade.getText().toString();
                final String telefone = etTelefone.getText().toString();
                final String nome = etNome.getText().toString();

                if (password.isEmpty())
                    etPassword.setError("Por favor confirme sua senha");
                else {
                    UsuarioDTO dto = createDto(email, password, cpf, endereco, estado, cidade, telefone, nome);

                    UsuarioClient usuarioClient = retrofit.create(UsuarioClient.class);
                    Call<UsuarioDTO> call = usuarioClient.update(
                            extras.getString("token"),
                            Long.valueOf(extras.getString("id")),
                            dto);

                    call.enqueue(new Callback<UsuarioDTO>() {
                        @Override
                        public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                            UsuarioDTO usuario = response.body();
                            if (usuario == null)
                                Toast.makeText(MinhaContaActivity.this, "Ocorreu um erro ao atualizar dados", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent = new Intent(MinhaContaActivity.this, UserAreaActivity.class);
                                intent.putExtra("id", usuario.getId());
                                intent.putExtra("nome", usuario.getNome());
                                intent.putExtra("cpf", usuario.getCpf());
                                intent.putExtra("endereco", usuario.getEndereco());
                                intent.putExtra("cidade", usuario.getCidade());
                                intent.putExtra("estado", usuario.getEstado());
                                intent.putExtra("telefone", usuario.getTelefone());
                                intent.putExtra("email", usuario.getEmail());

                                Toast.makeText(MinhaContaActivity.this, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                MinhaContaActivity.this.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                            Toast.makeText(MinhaContaActivity.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        bRemover.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MinhaContaActivity.this);
                builder.setTitle("Remover conta");
                builder.setMessage("Tem certeza que deseja remover essta conta?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MinhaContaActivity.this, "Removendo aguarde...", Toast.LENGTH_SHORT).show();
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl(URL)
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();


                        UsuarioClient usuarioClient = retrofit.create(UsuarioClient.class);
                        Call<ResponseBody> call = usuarioClient.delete(
                                extras.getString("token"),
                                Long.valueOf(extras.getString("id")));

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(MinhaContaActivity.this, "Removido com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MinhaContaActivity.this, LoginActivity.class);
                                MinhaContaActivity.this.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(MinhaContaActivity.this, "Falha ao remover", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    } });
                AlertDialog alert = builder.create();
                alert.show();
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
}
