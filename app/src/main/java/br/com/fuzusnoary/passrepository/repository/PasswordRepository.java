package br.com.fuzusnoary.passrepository.repository;

import android.content.Context;

import java.util.List;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import br.com.fuzusnoary.passrepository.listeners.APIListener;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.remote.PasswordService;
import br.com.fuzusnoary.passrepository.repository.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRepository extends BaseRepository {

    private PasswordService _passwordService;

    public PasswordRepository(Context context) {
        super(context);
        _passwordService = RetrofitClient.createService(PasswordService.class);
    }

    private <S> void callAPI(Call<S> call, final APIListener<S> listener) {

        if (!super.isConnectionAvailable()) {
            listener.onFailure(_context.getString(R.string.MANDATORY_CONNECTION));
        }

        call.enqueue(new Callback<S>() {
            @Override
            public void onResponse(Call<S> call, Response<S> response) {
                switch (response.code()) {
                    case PasswordConstants.HTTP.SUCCESS:
                    case PasswordConstants.HTTP.CREATED:
                        listener.onSuccess(response.body());
                        break;
                    case PasswordConstants.HTTP.NO_CONTENT:
                        listener.onSuccess(null);
                        break;
                    case PasswordConstants.HTTP.NOT_FOUND:
                    case PasswordConstants.HTTP.FORBIDDEN:
                        listener.onFailure(handleFailure(response.errorBody()));
                        break;
                    default:
                        listener.onFailure(_context.getString(R.string.ERROR_UNEXPECTED));
                }
            }

            @Override
            public void onFailure(Call<S> call, Throwable t) {
                listener.onFailure(_context.getString(R.string.ERROR_UNEXPECTED));
            }
        });
    }

    public void findAll(APIListener<List<PasswordModel>> listener) {
        Call<List<PasswordModel>> call = _passwordService.all();
        this.callAPI(call, listener);
    }

    public void load(Long id, APIListener<PasswordModel> listener) {
        Call<PasswordModel> call = _passwordService.load(id);
        this.callAPI(call, listener);
    }

    public void insert(PasswordModel pass, APIListener<PasswordModel> listener) {
        Call<PasswordModel> call = _passwordService.create(pass.getName(), pass.getPassType(), pass.getPassword());
        this.callAPI(call, listener);
    }

    public void update(PasswordModel pass, APIListener<PasswordModel> listener) {
        Call<PasswordModel> call = _passwordService.update(pass.getId(), pass.getName(), pass.getPassType(), pass.getPassword());
        this.callAPI(call, listener);
    }

    public void delete(long id, APIListener<Void> listener) {
        Call<Void> call = _passwordService.delete(id);
        this.callAPI(call, listener);
    }

}
