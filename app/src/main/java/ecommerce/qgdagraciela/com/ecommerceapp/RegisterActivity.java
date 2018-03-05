package ecommerce.qgdagraciela.com.ecommerceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etCpf = (EditText) findViewById(R.id.etCpf);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etState = (EditText) findViewById(R.id.etState);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

    }
}
