package com.frick.maximilian.coffeetime.status.views;

public interface StatusView<Presenter> {

   interface CoffeeStatus {
      int ASKING = 1;
      int COFFEENESS = 4;
      int IDLE = 0;
      int PATIENCE = 3;
      int PREPARATION = 2;
   }

   int getViewType();

   void setPresenter(Presenter presenter);
}
