package com.github.vanillabo.api;

import com.github.vanillabo.model.WeiboUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alan on 16/4/12.
 */
public interface WeiboUserShowService {

    @GET("users/show.json")
    Call<WeiboUser> getWeiboUser(
            @Query("access_token") String access_token,
            @Query("uid") String uid);
}
