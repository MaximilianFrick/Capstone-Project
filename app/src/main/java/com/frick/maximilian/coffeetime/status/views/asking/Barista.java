package com.frick.maximilian.coffeetime.status.views.asking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Barista {
   private static final int CUP_SIZE_COFFEE = 16;
   private static final int CUP_SIZE_TIME_FACTOR = 30;
   private static final int CUP_SIZE_WATER = 250;
   private static final int INIT_TIME = 300;

   public static PreparationViewModel getPreparationObject(int cups) {
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
      int timeInSecs = INIT_TIME + (cups * CUP_SIZE_TIME_FACTOR);
      preparationBuilder.timeInSecs(timeInSecs);
      SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
      preparationBuilder.timer(format.format(new Date(timeInSecs * 1000)));

      return preparationBuilder.build();
   }
}
