package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.PasswordRepository;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository _passwordRepository;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        this._passwordRepository = new PasswordRepository(application.getApplicationContext());
    }

    public void save(PasswordModel pass) {
        this._passwordRepository.insert(pass);
    }
}
