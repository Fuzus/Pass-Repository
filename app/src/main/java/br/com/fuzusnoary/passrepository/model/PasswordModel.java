package br.com.fuzusnoary.passrepository.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import br.com.fuzusnoary.passrepository.constants.PasswordConstants;

@Entity(tableName = "tb_password")
public class PasswordModel {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "password_name")
    private String passName;

    @ColumnInfo(name = "password_type")
    private int passType;

    @ColumnInfo(name = "password")
    private String password;

    public PasswordModel(int id, String passName, int passType, String password) {
        this.id = id;
        this.passName = passName;
        setPassType(passType);
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public int getPassType() {
        return this.passType;
    }

    public void setPassType(int passType) {
        if (passType == PasswordConstants.PassType.TEXT) {
            this.passType = passType;
        } else if (passType == PasswordConstants.PassType.NUMERIC) {
            this.passType = passType;
        } else {
            throw new IllegalArgumentException("tipo de senha inexistente: " + passType);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
