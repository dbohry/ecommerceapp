package ecommerce.qgdagraciela.com.ecommerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        final Button bProdutos = (Button) findViewById(R.id.bProdutos);
        final Button bProcurar = (Button) findViewById(R.id.bProcurar);
        final Button bCarrinho = (Button) findViewById(R.id.bCarrinho);
        final Button bConta = (Button) findViewById(R.id.bConta);
        final Button bSair = (Button) findViewById(R.id.bSair);

        bProdutos.setEnabled(false);
        bProcurar.setEnabled(false);
        bCarrinho.setEnabled(false);

        Bundle extras = getIntent().getExtras();

        tvWelcome.setText("Seja bem vindo, " + extras.getString("username"));

        bSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(UserAreaActivity.this, LoginActivity.class);
                getIntent().removeExtra("username");
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });

    }
}
