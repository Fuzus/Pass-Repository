package br.com.fuzusnoary.passrepository.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.view.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder _viewHolder = new ViewHolder();
    private UserViewModel _userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _viewHolder.editEmail = findViewById(R.id.edit_email);
        _viewHolder.editPassword = findViewById(R.id.edit_password);
        _viewHolder.loginButton = findViewById(R.id.button_login);
        _viewHolder.textRegister = findViewById(R.id.text_register);

        _userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        this.setListeners();
        this.setObservers();

        _userViewModel.checkFingerPrintRequirement();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_login) {
            String email = _viewHolder.editEmail.getText().toString();
            String password = _viewHolder.editPassword.getText().toString();

            _userViewModel.login(email, password);
        }
        if (id == R.id.text_register) {
            startActivity(new Intent(getApplication(), RegisterActivity.class));
        }
    }

    private void setListeners() {
        this._viewHolder.loginButton.setOnClickListener(this);
        this._viewHolder.textRegister.setOnClickListener(this);
    }

    private void setObservers() {
        _userViewModel.login.observe(this, new Observer<FeedBackModel>() {
            @Override
            public void onChanged(FeedBackModel feedback) {
                if (feedback.isSuccess()) {
                    Toast.makeText(getApplication(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), MainActivity.class));
                } else {
                    Toast.makeText(getApplication(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        _userViewModel.isFingerPrintAvailable.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAvailable) {
                if (isAvailable) {
                    openBiometricAuthentication();
                }
            }
        });
    }

    private void openBiometricAuthentication() {
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(getApplication(), MainActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo info = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Password Repository")
                .setNegativeButtonText("cancel")
                .build();
        
        biometricPrompt.authenticate(info);
    }

    public static class ViewHolder {
        EditText editEmail;
        EditText editPassword;
        Button loginButton;
        TextView textRegister;
    }
}