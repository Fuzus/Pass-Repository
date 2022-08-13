package br.com.fuzusnoary.passrepository.model;


import com.google.gson.annotations.SerializedName;

public class PasswordModel {

    private Long id;
    private String name;
    private int passType;
    private String password;
    private String userToken;

    public PasswordModel(){}

    public PasswordModel(Long id, String name, int passType, String password) {
        this.id = id;
        this.name = name;
        this.passType = passType;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassType() {
        return passType;
    }

    public void setPassType(int passType) {
        this.passType = passType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
