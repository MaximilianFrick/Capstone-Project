package com.frick.maximilian.coffeetime.status.views.patience;

public class RemainingTimeViewModel {
   public static final class Builder {
      private String displayedText;
      private int percantage;
      private Long remainingTime;

      public Builder() {
      }

      public RemainingTimeViewModel build() {
         return new RemainingTimeViewModel(this);
      }

      public Builder displayedText(String val) {
         displayedText = val;
         return this;
      }

      public Builder percantage(int val) {
         percantage = val;
         return this;
      }

      public Builder remainingTime(Long val) {
         remainingTime = val;
         return this;
      }
   }
   private String displayedText;
   private int percantage;
   private Long remainingTime;

   private RemainingTimeViewModel(Builder builder) {
      percantage = builder.percantage;
      displayedText = builder.displayedText;
      remainingTime = builder.remainingTime;
   }

   public String getDisplayedText() {
      return displayedText;
   }

   public int getPercantage() {
      return percantage;
   }

   public Long getRemainingTime() {
      return remainingTime;
   }
}
