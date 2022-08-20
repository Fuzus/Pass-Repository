package br.com.fuzusnoary.passrepository.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.view.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private final ViewHolder _viewHolder = new ViewHolder();
    private UserViewModel _viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _viewHolder.editName = findViewById(R.id.edit_name);
        _viewHolder.editEmail = findViewById(R.id.edit_email);
        _viewHolder.editPassword = findViewById(R.id.edit_password);
        _viewHolder.buttonRegister = findViewById(R.id.button_create);

        _viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        this.setListeners();
        this.setObservers();

    }

    private void setListeners() {
        _viewHolder.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _viewHolder.editName.getText().toString();
                String email = _viewHolder.editEmail.getText().toString();
                String password = _viewHolder.editPassword.getText().toString();

                _viewModel.createNewUser(name, email, password);
            }
        });
    }

    private void setObservers() {
        _viewModel.login.observe(this, new Observer<FeedBackModel>() {
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
    }


    private static class ViewHolder {
        EditText editName;
        EditText editEmail;
        EditText editPassword;
        Button buttonRegister;
    }
}