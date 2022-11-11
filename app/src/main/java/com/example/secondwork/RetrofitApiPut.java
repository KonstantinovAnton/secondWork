package com.example.secondwork;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitApiPut {
    @PUT("Persons/")
    Call<Persons> createPut(@Body Persons dataModal, @Query("id") int id);
}


