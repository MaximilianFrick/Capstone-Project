package com.frick.maximilian.coffeetime.data;

import com.frick.maximilian.coffeetime.data.models.ServerTime;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CoffeeTimeApi {
   @GET ("currentdate")
   Observable<ServerTime> getCurrentServerTime(@Query("groupId") String groupId);
}
