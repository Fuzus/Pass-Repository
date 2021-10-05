package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.PasswordRepository;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository _passwordRepository;
    private MutableLiveData<FeedBackModel> _message = new MutableLiveData<>();
    public final LiveData<FeedBackModel> message = this._message;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        this._passwordRepository = new PasswordRepository(application.getApplicationContext());
    }

    public void save(PasswordModel pass) {

        if ("".equals(pass.getPassName())){
            this._message.setValue(new FeedBackModel(false,
                    getApplication().getString(R.string.name_is_mandatory)));
            return;
        }

        if ("".equals(pass.getPassword())){
            this._message.setValue(new FeedBackModel(false,
                    getApplication().getString(R.string.password_is_mandatory)));
            return;
        }

        if (this._passwordRepository.insert(pass)) {
            this._message.setValue(new FeedBackModel(true,
                    getApplication().getString(R.string.succefully_createad)));
        } else {
            this._message.setValue(new FeedBackModel(false,
                    getApplication().getString(R.string.unexpected_error)));
        }
    }
}
