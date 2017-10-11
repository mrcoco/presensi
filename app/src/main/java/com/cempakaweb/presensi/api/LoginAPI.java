package com.cempakaweb.presensi.api;

import com.cempakaweb.presensi.model.LoginValue;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    @FormUrlEncoded
    @POST("login")
    Call<LoginValue> daftar(@Field("username") String username,
                            @Field("password") String password);
}
