package com.example.secondwork;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitApiPut {
    @PUT("Person/")
    Call<Person> createPut(@Body Person dataModal, @Query("ID") int id);
}
