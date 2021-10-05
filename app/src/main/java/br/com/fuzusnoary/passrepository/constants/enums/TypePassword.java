package br.com.fuzusnoary.passrepository.constants.enums;

public enum TypePassword {
    TEXT(0),
    NUMERIC(1);

    int value;
    private TypePassword(int value) {
        this.value = value;
    }

    public static TypePassword getType(int value) {
        for (TypePassword t : TypePassword.values()) {
            if (t.getValue() == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor incorreto");
    }

    public int getValue() {
        return this.value;
    }

}