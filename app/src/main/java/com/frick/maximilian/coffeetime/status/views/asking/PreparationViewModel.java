package com.frick.maximilian.coffeetime.status.views.asking;

public class PreparationViewModel {

   public static final class Builder {
      private String coffeeAmount;
      private String cups;
      private long timeInMillis;
      private String timer;
      private String waterAmount;

      public Builder() {
      }

      public PreparationViewModel build() {
         return new PreparationViewModel(this);
      }

      public Builder coffeeAmount(String val) {
         coffeeAmount = val;
         return this;
      }

      public Builder cups(String val) {
         cups = val;
         return this;
      }

      public Builder timeInMillis(long val) {
         timeInMillis = val;
         return this;
      }

      public Builder timer(String val) {
         timer = val;
         return this;
      }

      public Builder waterAmount(String val) {
         waterAmount = val;
         return this;
      }
   }

   private String coffeeAmount;
   private String cups;
   private long timeInMillis;
   private String timer;
   private String waterAmount;

   private PreparationViewModel(Builder builder) {
      coffeeAmount = builder.coffeeAmount;
      cups = builder.cups;
      timer = builder.timer;
      timeInMillis = builder.timeInMillis;
      waterAmount = builder.waterAmount;
   }

   public String getCoffeeAmount() {
      return coffeeAmount;
   }

   public String getCups() {
      return cups;
   }

   public long getTimeInMillis() {
      return timeInMillis;
   }

   public String getTimer() {
      return timer;
   }

   public String getWaterAmount() {
      return waterAmount;
   }
}
