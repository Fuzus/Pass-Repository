package br.com.fuzusnoary.passrepository.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.enums.TypePassword;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.view.viewmodel.PasswordViewModel;

public class PasswordActivity extends AppCompatActivity {

    private PasswordViewModel _viewModel;
    private ViewHolder _viewHolder = new ViewHolder();
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

        this.setListeners();

    }

    public void setListeners(){
        this._viewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerSave();
            }
        });
    }

    public void handlerSave(){
        String passName = this._viewHolder.editNamePassword.getText().toString();
        TypePassword type = this._viewHolder.radioTypeNumeric.isChecked() ?
                TypePassword.NUMERIC : TypePassword.TEXT;
        String password = this._viewHolder.editPassword.getText().toString();

        PasswordModel pass = new PasswordModel(_passId, passName, type.getValue(), password);
        this._viewModel.save(pass);

    }

    private class ViewHolder {
        EditText editNamePassword;
        RadioButton radioTypeNumeric;
        RadioButton radioTypeText;
        EditText editPassword;
        Button btnSave;
    }
}