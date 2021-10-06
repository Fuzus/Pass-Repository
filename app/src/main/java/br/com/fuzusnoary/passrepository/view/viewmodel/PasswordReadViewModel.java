package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.PasswordRepository;

public class PasswordReadViewModel extends AndroidViewModel {

    private PasswordRepository _repository;
    private MutableLiveData<PasswordModel> _password = new MutableLiveData<>();
    public final LiveData<PasswordModel> password = this._password;

    public PasswordReadViewModel(@NonNull Application application) {
        super(application);
        _repository = new PasswordRepository(application.getApplicationContext());
    }

    public void load(int id){
        this._password.setValue(this._repository.load(id));
    }


}
