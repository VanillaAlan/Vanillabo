package com.github.vanillabo.api;

import com.github.vanillabo.model.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alan on 16/4/13.
 */
public interface StatusService {

    @GET("statuses/home_timeline.json")
    Call<StatusResponse> getHomeTimeline(@Query("access_token") String access_token,
                                         @Query("since_id") long since_id,
                                         @Query("max_id") long max_id,
                                         @Query("count") int count,
                                         @Query("page") int page);

}
