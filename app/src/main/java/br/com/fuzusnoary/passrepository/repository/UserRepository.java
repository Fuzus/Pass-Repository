package br.com.fuzusnoary.passrepository.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import br.com.fuzusnoary.passrepository.listeners.APIListener;
import br.com.fuzusnoary.passrepository.model.UserModel;
import br.com.fuzusnoary.passrepository.repository.local.SecurityPreferences;
import br.com.fuzusnoary.passrepository.repository.remote.RetrofitClient;
import br.com.fuzusnoary.passrepository.repository.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository extends BaseRepository {

    private final UserService _service;
    private final SecurityPreferences _preferences;

    public UserRepository(Context context) {
        super(context);
        _service = RetrofitClient.createService(UserService.class);
        _preferences = new SecurityPreferences(context);
    }

    private <S> void callAPI(Call<S> call, APIListener<S> listener) {
        if (!isConnectionAvailable()) {
            listener.onFailure(_context.getString(R.string.MANDATORY_CONNECTION));
            return;
        }

        call.enqueue(new Callback<S>() {
            @Override
            public void onResponse(@NonNull Call<S> call, @NonNull Response<S> response) {
                switch (response.code()) {
                    case PasswordConstants.HTTP.SUCCESS:
                    case PasswordConstants.HTTP.CREATED:
                        listener.onSuccess(response.body());
                        break;
                    case PasswordConstants.HTTP.FORBIDDEN:
                    case PasswordConstants.HTTP.NOT_FOUND:
                        listener.onFailure(handleFailure(response.errorBody()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<S> call, Throwable t) {
                listener.onFailure(_context.getString(R.string.ERROR_UNEXPECTED));
            }
        });
    }

    public void login(String email, String password, APIListener<UserModel> listener) {
        Call<UserModel> call = _service.login(email, password);
        this.callAPI(call, listener);
    }

    public void createUser(String name, String email, String password, APIListener<UserModel> listener) {
        Call<UserModel> call = _service.createUser(name, email, password);
        this.callAPI(call, listener);
    }

    public void saveLocalData(UserModel user) {
        _preferences.storeString(PasswordConstants.SHARED.USER_NAME, user.getName());
        _preferences.storeString(PasswordConstants.SHARED.USER_EMAIL, user.getEmail());
        _preferences.storeString(PasswordConstants.SHARED.USER_TOKEN, user.getToken());

        RetrofitClient.saveHeaders(user.getToken());
    }

    public void deleteLocalData() {
        _preferences.remove(PasswordConstants.SHARED.USER_NAME);
        _preferences.remove(PasswordConstants.SHARED.USER_EMAIL);
        _preferences.remove(PasswordConstants.SHARED.USER_TOKEN);
    }

    public UserModel getLocalData() {
        UserModel user = new UserModel();
        user.setEmail(_preferences.getStoredString(PasswordConstants.SHARED.USER_EMAIL));
        user.setName(_preferences.getStoredString(PasswordConstants.SHARED.USER_NAME));
        user.setToken(_preferences.getStoredString(PasswordConstants.SHARED.USER_TOKEN));

        return user;
    }
}
