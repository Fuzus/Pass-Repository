package br.com.fuzusnoary.passrepository.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {

    private SharedPreferences _sharedPreferences;

    public SecurityPreferences(Context context) {
        _sharedPreferences = context.getSharedPreferences("PasswordsShared", Context.MODE_PRIVATE);
    }

    public void storeString(String key, String value){
        _sharedPreferences.edit().putString(key, value).apply();
    }

    public String getStoredString(String key) {
        return _sharedPreferences.getString(key, "");
    }

    public void remove(String key) {
        _sharedPreferences.edit().remove(key).apply();
    }
}
