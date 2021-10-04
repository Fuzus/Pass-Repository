package br.com.fuzusnoary.passrepository.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.fuzusnoary.passrepository.model.PasswordModel;

@Database(entities = {PasswordModel.class}, version = 1)
public abstract class PasswordDatabase extends RoomDatabase {

    private static PasswordDatabase INSTANCE;
    public abstract PasswordDAO passDAO();

    public static PasswordDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, PasswordDatabase.class, "Passwords")
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }


}
