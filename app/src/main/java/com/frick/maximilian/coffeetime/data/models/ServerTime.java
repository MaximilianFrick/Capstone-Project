package com.frick.maximilian.coffeetime.data.models;

import com.google.gson.annotations.SerializedName;

public class ServerTime {
   @SerializedName ("brewingTime")
   private Long brewingTime;
   @SerializedName ("date")
   private Long serverTimeInMillis;
   @SerializedName ("startTime")
   private Long startTime;

   public Long getBrewingTime() {
      return brewingTime;
   }

   public Long getServerTimeInMillis() {
      return serverTimeInMillis;
   }

   public Long getStartTime() {
      return startTime;
   }
}
