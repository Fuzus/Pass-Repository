package br.com.fuzusnoary.passrepository.repository.remote;

import br.com.fuzusnoary.passrepository.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("Authentication/create")
    Call<UserModel> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Authentication/login")
    Call<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
