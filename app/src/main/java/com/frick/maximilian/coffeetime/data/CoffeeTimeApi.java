package com.frick.maximilian.coffeetime.data;


import com.frick.maximilian.coffeetime.data.models.Group;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CoffeeTimeApi {
   @GET ("groups")
   Observable<List<Group>> getGroups();
}
