package br.com.fuzusnoary.passrepository.repository;

import android.content.Context;

import java.util.List;

import br.com.fuzusnoary.passrepository.model.PasswordModel;

public class PasswordRepository {

    private PasswordDAO _passwordDao;

    public PasswordRepository(Context context){
        PasswordDatabase db = PasswordDatabase.getInstance(context);
        this._passwordDao = db.passDAO();
    }

    //CRUD

    public List<PasswordModel> findAll(){
        return this._passwordDao.findAll();
    }

    public PasswordModel load(int id){
        return this._passwordDao.load(id);
    }

    public boolean insert(PasswordModel password){
        return this._passwordDao.insert(password) > 0;
    }

    public boolean update(PasswordModel password) {
        return this._passwordDao.update(password) > 0;
    }

    public boolean delete(int id){
        PasswordModel password = this.load(id);
        return this._passwordDao.delete(password) > 0;
    }

}
