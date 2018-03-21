package com.frick.maximilian.coffeetime.status.views;

public interface StatusView<Presenter> {

   interface CoffeeStatus {
      int ASKING = 1;
      int IDLE = 0;
   }

   void setPresenter(Presenter presenter);

   int getViewType();
}
