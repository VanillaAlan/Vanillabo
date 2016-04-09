package com.github.vanillabo.api;

import com.github.vanillabo.model.AccessToken;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by alan on 16/4/9.
 */
public interface WeiboAuthService {

    @POST("oauth2/access_token")
    Call<AccessToken> getAccessToken(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("grant_type") String grant_type,
            @Query("redirect_uri") String redirect_uri,
            @Query("code") String code);

}
