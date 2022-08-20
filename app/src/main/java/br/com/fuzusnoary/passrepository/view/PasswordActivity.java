package br.com.fuzusnoary.passrepository.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.view.viewmodel.PasswordViewModel;

public class PasswordActivity extends AppCompatActivity {

    private final ViewHolder _viewHolder = new ViewHolder();
    private PasswordViewModel _viewModel;
    private Long _passId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        _passId = 0L;

        this._viewModel = new ViewModelProvider(this).get(PasswordViewModel.class);

        this._viewHolder.editNamePassword = findViewById(R.id.edit_pass_name);
        this._viewHolder.radioTypeNumeric = findViewById(R.id.radio_type_numeric);
        this._viewHolder.radioTypeText = findViewById(R.id.radio_type_text);
        this._viewHolder.editPassword = findViewById(R.id.edit_password);
        this._viewHolder.btnSave = findViewById(R.id.button_save);
        this._viewHolder.imgVisibilityOn = findViewById(R.id.img_visibility_on);
        this._viewHolder.imgVisibilityOff = findViewById(R.id.img_visibility_off);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this._viewModel.load(bundle.getLong(PasswordConstants.ID));
            this._passId = bundle.getLong(PasswordConstants.ID);
        }

        this.setListeners();
        this.setObservers();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListeners() {
        this._viewHolder.btnSave.setOnClickListener((view) -> handlerSave());

        this._viewHolder.radioTypeNumeric.setOnClickListener((view) ->
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_VARIATION_PASSWORD));

        this._viewHolder.radioTypeText.setOnClickListener((view) ->
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD));

        this._viewHolder.imgVisibilityOn.setOnClickListener((view) -> {
            _viewHolder.imgVisibilityOn.setVisibility(View.GONE);
            _viewHolder.imgVisibilityOff.setVisibility(View.VISIBLE);
            if (_viewHolder.radioTypeText.isChecked()) {
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        });

        this._viewHolder.imgVisibilityOff.setOnClickListener((view) -> {
            _viewHolder.imgVisibilityOff.setVisibility(View.GONE);
            _viewHolder.imgVisibilityOn.setVisibility(View.VISIBLE);
            if (_viewHolder.radioTypeText.isChecked()) {
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            }
        });

    }

    public void setObservers() {
        this._viewModel.message.observe(this, feedback -> {
            if (feedback.isSuccess()) {
                finish();
            }
            Toast.makeText(getApplicationContext(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
        });
        this._viewModel.password.observe(this, pass -> {
            _viewHolder.editNamePassword.setText(pass.getName());
            int type = pass.getPassType();
            if (type == PasswordConstants.PassType.TEXT) {
                _viewHolder.radioTypeText.setChecked(true);
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (type == PasswordConstants.PassType.NUMERIC) {
                _viewHolder.radioTypeNumeric.setChecked(true);
                _viewHolder.editPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            _viewHolder.editPassword.setText(pass.getPassword());
        });
    }

    public void handlerSave() {
        String passName = this._viewHolder.editNamePassword.getText().toString();
        int type = this._viewHolder.radioTypeNumeric.isChecked() ?
                PasswordConstants.PassType.NUMERIC : PasswordConstants.PassType.TEXT;
        String password = this._viewHolder.editPassword.getText().toString();

        PasswordModel pass = new PasswordModel(this._passId, passName, type, password);
        this._viewModel.save(pass);

    }

    private static class ViewHolder {
        EditText editNamePassword;
        RadioButton radioTypeNumeric;
        RadioButton radioTypeText;
        EditText editPassword;
        Button btnSave;
        ImageView imgVisibilityOn;
        ImageView imgVisibilityOff;

    }
}