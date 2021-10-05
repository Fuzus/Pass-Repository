package br.com.fuzusnoary.passrepository.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.enums.TypePassword;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.view.viewmodel.PasswordViewModel;

public class PasswordActivity extends AppCompatActivity {

    private final ViewHolder _viewHolder = new ViewHolder();
    private PasswordViewModel _viewModel;
    private int _passId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        _passId = 0;

        this._viewModel = new ViewModelProvider(this).get(PasswordViewModel.class);

        this._viewHolder.editNamePassword = findViewById(R.id.edit_pass_name);
        this._viewHolder.radioTypeNumeric = findViewById(R.id.radio_type_numeric);
        this._viewHolder.radioTypeText = findViewById(R.id.radio_type_text);
        this._viewHolder.editPassword = findViewById(R.id.edit_password);
        this._viewHolder.btnSave = findViewById(R.id.button_save);
        this._viewHolder.imgVisibilityOn = findViewById(R.id.img_visibility_on);
        this._viewHolder.imgVisibilityOff = findViewById(R.id.img_visibility_off);

        this.setListeners();
        this.setObservers();

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

    public void setObservers(){
        this._viewModel.message.observe(this, new Observer<FeedBackModel>() {
            @Override
            public void onChanged(FeedBackModel feedback) {
                if(feedback.isStatus()){
                    finish();
                }
                Toast.makeText(getApplicationContext(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlerSave() {
        String passName = this._viewHolder.editNamePassword.getText().toString();
        TypePassword type = this._viewHolder.radioTypeNumeric.isChecked() ?
                TypePassword.NUMERIC : TypePassword.TEXT;
        String password = this._viewHolder.editPassword.getText().toString();

        PasswordModel pass = new PasswordModel(this._passId, passName, type.getValue(), password);
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