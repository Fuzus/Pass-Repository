package br.com.fuzusnoary.passrepository.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.view.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder _viewHolder = new ViewHolder();
    private LoginViewModel _loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _viewHolder.editEmail = findViewById(R.id.edit_email);
        _viewHolder.editPassword = findViewById(R.id.edit_password);
        _viewHolder.loginButton = findViewById(R.id.button_login);
        _viewHolder.textRegister = findViewById(R.id.text_register);

        _loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        this.setListeners();

        //TODO: criar observers e tratar a impressao digital

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_login) {
            String email = _viewHolder.editEmail.getText().toString();
            String password = _viewHolder.editPassword.getText().toString();

            //TODO: Metodo na viewModel de login
        } if (id == R.id.text_register) {
            //TODO: criar intencao de cadastro de novo usuario
        }
    }


    private void setListeners() {
        this._viewHolder.loginButton.setOnClickListener(this);
        this._viewHolder.textRegister.setOnClickListener(this);
    }

    public static class ViewHolder {
        EditText editEmail;
        EditText editPassword;
        Button loginButton;
        TextView textRegister;
    }
}