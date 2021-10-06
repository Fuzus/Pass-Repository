package br.com.fuzusnoary.passrepository.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.model.FeedBackModel;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.repository.PasswordRepository;

public class AllPasswordsViewModel extends AndroidViewModel {

    private PasswordRepository _repository;
    private MutableLiveData<List<PasswordModel>> _list = new MutableLiveData<>();
    public final LiveData<List<PasswordModel>> list = this._list;

    private MutableLiveData<FeedBackModel> _feedback = new MutableLiveData<>();
    public final LiveData<FeedBackModel> feedback = this._feedback;

    public AllPasswordsViewModel(@NonNull Application application) {
        super(application);
        _repository = new PasswordRepository(application.getApplicationContext());
    }

    public void delete(int id) {
       if(this._repository.delete(id)) {
           this._feedback.setValue(new FeedBackModel(true,
                   getApplication().getString(R.string.successfully_deleted)));
       } else {
           this._feedback.setValue(new FeedBackModel(false,
                   getApplication().getString(R.string.unexpected_error)));
       }
    }

    public void getList(){
        this._list.setValue(this._repository.findAll());
    }
}
