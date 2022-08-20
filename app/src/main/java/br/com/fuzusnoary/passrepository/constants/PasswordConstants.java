package br.com.fuzusnoary.passrepository.constants;

public class PasswordConstants {

    private PasswordConstants(){}

    public static final String ID = "id";

    public static class PassType {
        public static final int TEXT = 0;
        public static final int NUMERIC = 1;
    }

    public static class SHARED {
        public static final String USER_NAME = "user_name";
        public static final String USER_TOKEN = "user_token";
        public static final String USER_EMAIL = "user_email";
    }

    public static class HTTP {
        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int NO_CONTENT = 204;
        public static final int NOT_FOUND = 404;
        public static final int FORBIDDEN = 403;
    }

}
