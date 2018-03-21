package com.frick.maximilian.coffeetime.status.views.emptiness;

import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;

import javax.inject.Inject;

public class EmptinessPresenter {

   @Inject
   DatabaseBO databaseBO;

   EmptinessPresenter() {
      Injector.getAppComponent()
            .inject(this);
   }

   void askGroupForRoundOfCoffee() {
      databaseBO.setStatus(CoffeeStatus.ASKING);
   }
}
