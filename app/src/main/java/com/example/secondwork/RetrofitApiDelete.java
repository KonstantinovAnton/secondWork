package com.example.secondwork;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitApiDelete {
    @DELETE("Persons/")
    Call<Persons> createDelete(@Query("id") int id);
}
