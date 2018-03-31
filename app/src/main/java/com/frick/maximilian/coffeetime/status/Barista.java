package com.frick.maximilian.coffeetime.status;

import com.frick.maximilian.coffeetime.data.models.ServerTime;
import com.frick.maximilian.coffeetime.status.views.preparation.PreparationViewModel;
import com.frick.maximilian.coffeetime.status.views.patience.RemainingTimeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Barista {
   private static final int CUP_SIZE_COFFEE = 16;
   private static final int CUP_SIZE_TIME_FACTOR = 30;
   private static final int CUP_SIZE_WATER = 250;
   private static final int INIT_TIME = 300;
   private static SimpleDateFormat format = new SimpleDateFormat("~ mm:ss", Locale.getDefault());

   public static PreparationViewModel buildPreparatioViewModel(int cups) {
      PreparationViewModel.Builder preparationBuilder = new PreparationViewModel.Builder();
      // set amount of cups
      preparationBuilder.cups(String.valueOf(cups));
      // calc water amount
      int waterAmountInMl = cups * CUP_SIZE_WATER;
      preparationBuilder.waterAmount(String.valueOf(waterAmountInMl) + "ml");
      // calc coffee amount
      int coffeeAmountInGramm = cups * CUP_SIZE_COFFEE;
      preparationBuilder.coffeeAmount(String.valueOf(coffeeAmountInGramm) + "g");
      // calc estimated brewing time
      int timeInMillis = (INIT_TIME + (cups * CUP_SIZE_TIME_FACTOR)) * 1000;
      preparationBuilder.timeInMillis(timeInMillis);
      preparationBuilder.timer(format.format(new Date(timeInMillis)));

      return preparationBuilder.build();
   }

   public static RemainingTimeViewModel buildRemainingTimeViewModel(ServerTime serverTime,
         Long remainingTime) {
      RemainingTimeViewModel.Builder builder = new RemainingTimeViewModel.Builder();
      builder.percantage((int) ((int) (100 * remainingTime) / serverTime.getBrewingTime()));
      builder.displayedText(format.format(new Date(remainingTime)));
      builder.remainingTime(remainingTime);
      return builder.build();
   }

   public static Long getRemainingTime(ServerTime serverTime) {
      return serverTime.getBrewingTime() -
            (serverTime.getServerTimeInMillis() - serverTime.getStartTime());
   }
}
