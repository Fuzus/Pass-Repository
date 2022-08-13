package br.com.fuzusnoary.passrepository.repository.remote;

import java.util.List;

import br.com.fuzusnoary.passrepository.model.PasswordModel;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PasswordService {


    @GET("passwords")
    Call<List<PasswordModel>> all();


    @GET("passwords/{id}")
    Call<PasswordModel> load(@Path(value = "id") Long id);

    @FormUrlEncoded
    @POST("passwords")
    Call<PasswordModel> create(
            @Field("name") String name,
            @Field("passType") int passType,
            @Field("pass") String password
    );

    @FormUrlEncoded
    @PUT("passwords/{id}")
    Call<PasswordModel> update(
            @Path(value = "id") Long id,
            @Field("name") String name,
            @Field("passType") int passType,
            @Field("pass") String password
    );


    @DELETE("passwords/{id}")
    Call<Void> delete(@Path("id") Long id);

}
