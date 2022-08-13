package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.listeners.APIListener;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.PasswordRepository;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository _passwordRepository;

    private MutableLiveData<PasswordModel> _password = new MutableLiveData<>();
    public final LiveData<PasswordModel> password = this._password;

    private MutableLiveData<FeedBackModel> _message = new MutableLiveData<>();
    public final LiveData<FeedBackModel> message = this._message;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        this._passwordRepository = new PasswordRepository(application.getApplicationContext());
    }

    public void save(PasswordModel pass) {

        if ("".equals(pass.getName())){
            this._message.setValue(new FeedBackModel(false,
                    getApplication().getString(R.string.name_is_mandatory)));
            return;
        }

        if ("".equals(pass.getPassword())){
            this._message.setValue(new FeedBackModel(false,
                    getApplication().getString(R.string.password_is_mandatory)));
            return;
        }

        if(pass.getId() == 0) {
            this._passwordRepository.insert(pass, new APIListener<PasswordModel>() {
                @Override
                public void onSuccess(PasswordModel result) {
                    _password.setValue(result);
                    _message.setValue(new FeedBackModel(true,
                            getApplication().getString(R.string.successfully_created)));
                }

                @Override
                public void onFailure(String message) {
                   _message.setValue(new FeedBackModel(false,
                            getApplication().getString(R.string.unexpected_error)));
                }
            });
        } else {
            this._passwordRepository.update(pass, new APIListener<PasswordModel>() {
                @Override
                public void onSuccess(PasswordModel result) {
                    _password.setValue(result);
                    _message.setValue(new FeedBackModel(true,
                            getApplication().getString(R.string.successfully_updated)));
                }

                @Override
                public void onFailure(String message) {
                    _message.setValue(new FeedBackModel(false,
                            getApplication().getString(R.string.unexpected_error)));
                }
            });
        }
    }

    public void load(Long id){
        this._passwordRepository.load(id, new APIListener<PasswordModel>() {
            @Override
            public void onSuccess(PasswordModel result) {
                _password.setValue(result);
            }

            @Override
            public void onFailure(String message) {
                _message.setValue(new FeedBackModel(false, message));
            }
        });
    }
}
