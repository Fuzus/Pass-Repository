package br.com.fuzusnoary.passrepository.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import br.com.fuzusnoary.passrepository.view.viewmodel.PasswordReadViewModel;

public class PasswordReadActivity extends AppCompatActivity {

    private ViewHolder _viewHolder = new ViewHolder();
    private PasswordReadViewModel _viewModel;
    private Bundle _bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_read);

        this._viewModel = new ViewModelProvider(this).get(PasswordReadViewModel.class);

        this._viewHolder.textPassName = findViewById(R.id.text_pass_name);
        this._viewHolder.radioTypeText = findViewById(R.id.radio_type_text);
        this._viewHolder.radioTypeNumeric = findViewById(R.id.radio_type_numeric);
        this._viewHolder.textPassword = findViewById(R.id.text_password);
        this._viewHolder.btnShowPassword = findViewById(R.id.button_show_password);
        this._viewHolder.btnEditPassword = findViewById(R.id.fab_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        this.setListeners();
        this.setObservers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _bundle = getIntent().getExtras();
        if (_bundle != null) {
            this._viewModel.load(_bundle.getLong(PasswordConstants.ID));
        }
    }

    public void setListeners() {
        this._viewHolder.btnShowPassword.setOnClickListener((view) -> {
            int inputType = _viewHolder.textPassword.getInputType();
            if (inputType != InputType.TYPE_CLASS_TEXT) {
                _viewHolder.textPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                _viewHolder.textPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        this._viewHolder.btnEditPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
            intent.putExtras(_bundle);

            startActivity(intent);
        });


    }

    public void setObservers() {
        this._viewModel.password.observe(this, (pass) -> {

            _viewHolder.textPassName.setText(pass.getName());
            int type = pass.getPassType();
            if (type == PasswordConstants.PassType.TEXT) {
                _viewHolder.radioTypeText.setChecked(true);
            } else if (type == PasswordConstants.PassType.NUMERIC) {
                _viewHolder.radioTypeNumeric.setChecked(true);
            }
            _viewHolder.textPassword.setText(pass.getPassword());
        });
    }

    private static class ViewHolder {
        TextView textPassName;
        RadioButton radioTypeText;
        RadioButton radioTypeNumeric;
        EditText textPassword;
        Button btnShowPassword;
        FloatingActionButton btnEditPassword;
    }
}