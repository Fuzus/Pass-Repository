package br.com.fuzusnoary.passrepository.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fuzusnoary.passrepository.model.PasswordModel;

@Dao
public interface PasswordDAO {

    @Insert
    long insert(PasswordModel password);

    @Update
    int update(PasswordModel password);

    @Delete
    int delete(PasswordModel password);

    @Query("SELECT * FROM tb_password")
    List<PasswordModel> findAll();

    @Query("SELECT * FROM tb_password WHERE id = :id")
    PasswordModel load(int id);

}
