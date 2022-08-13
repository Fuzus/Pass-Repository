package br.com.fuzusnoary.passrepository.repository.remote;

import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //TODO: Alterar url quando o app for para prod
    private static final String BASE_URL ="http://10.0.2.2:8080/";
    private static Retrofit retrofit;
    private static String userToken = "";

    private RetrofitClient(){}

    private static Retrofit getInstance() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(PasswordConstants.SHARED.USER_TOKEN, userToken)
                    .build();
            return chain.proceed(request);
        });
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> sClass) {
        return getInstance().create(sClass);
    }

    public static void saveHeaders(String token) {
        userToken = token;
    }
}
