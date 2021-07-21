package com.shinchannohara.personalinfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("everything")
    Call<Headlines> getHeadlines(
            @Query("q") String q,
            @Query("language") String language,
            @Query("apiKey") String apiKey
    );

}
