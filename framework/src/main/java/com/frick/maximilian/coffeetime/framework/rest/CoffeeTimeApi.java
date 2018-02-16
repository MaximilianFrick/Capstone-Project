package com.frick.maximilian.coffeetime.framework.rest;

import com.frick.maximilian.coffeetime.framework.models.Group;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CoffeeTimeApi {
   @GET ("groups")
   Observable<List<Group>> getGroups();
}
