package com.example.secondwork;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitApi {
    @POST("Person")
    Call<Person> createPost(@Body Person dataModal);
}


