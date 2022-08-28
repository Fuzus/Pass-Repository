package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.listeners.APIListener;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.model.UserModel;
import br.com.fuzusnoary.passrepository.repository.UserRepository;
import br.com.fuzusnoary.passrepository.repository.remote.RetrofitClient;
import br.com.fuzusnoary.passrepository.util.FingerPrintUtils;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository _repository;
    private final MutableLiveData<FeedBackModel> _login = new MutableLiveData<>();
    public LiveData<FeedBackModel> login = _login;

    private final MutableLiveData<Boolean> _isFingerPrintAvailable = new MutableLiveData<>();
    public LiveData<Boolean> isFingerPrintAvailable = _isFingerPrintAvailable;

    public UserViewModel(@NonNull Application application) {
        super(application);
        _repository = new UserRepository(application.getApplicationContext());
    }


    public void login(String email, String password) {
        _repository.login(email, password, new APIListener<UserModel>() {
            @Override
            public void onSuccess(UserModel result) {
                _repository.saveLocalData(result);
                _login.setValue(new FeedBackModel(true, getApplication().getString(R.string.login_successful)));
            }

            @Override
            public void onFailure(String message) {
                _login.setValue(new FeedBackModel(false, message));
            }
        });
    }

    public void createNewUser(String name, String email, String password) {
        _repository.createUser(name, email, password, new APIListener<UserModel>() {
            @Override
            public void onSuccess(UserModel result) {
                _repository.saveLocalData(result);
                _login.setValue(new FeedBackModel(true, getApplication().getString(R.string.user_successfully_created)));
            }

            @Override
            public void onFailure(String message) {
                _login.setValue(new FeedBackModel(false, message));
            }
        });
    }

    private boolean isUserLoggedIn() {
        boolean isLogged = !_repository.getLocalData().getEmail().equals("");
        if (isLogged)
            RetrofitClient.saveHeaders(_repository.getLocalData().getToken());

        return isLogged;
    }

    public void checkFingerPrintRequirement() {
        if (this.isUserLoggedIn()) {
           _isFingerPrintAvailable.setValue(FingerPrintUtils.isAvailable(this.getApplication()));
        }
    }
}
