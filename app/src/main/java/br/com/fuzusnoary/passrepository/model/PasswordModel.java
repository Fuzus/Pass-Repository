package br.com.fuzusnoary.passrepository.model;

import br.com.fuzusnoary.passrepository.enums.TypePassword;

public class PasswordModel {

    private String passName;
    private TypePassword passType;
    private String password;

    public PasswordModel(){}

    public PasswordModel(String passName, int passType, String password){
        this.passName = passName;
        setPassType(passType);
        this.password = password;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public int getPassType() {
        return passType.getValue();
    }

    public void setPassType(int passType) {
        TypePassword.getType(passType);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
