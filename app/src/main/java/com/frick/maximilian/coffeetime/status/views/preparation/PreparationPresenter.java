package com.frick.maximilian.coffeetime.status.views.preparation;

import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;
import com.frick.maximilian.coffeetime.status.views.asking.Barista;
import com.frick.maximilian.coffeetime.status.views.asking.PreparationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class PreparationPresenter {
   @Inject
   DatabaseBO databaseBO;
   private PreparationContract.View view;

   PreparationPresenter(PreparationContract.View view) {
      Injector.getAppComponent()
            .inject(this);
      this.view = view;
   }

   public void setAskingStatus() {
      databaseBO.setStatus(CoffeeStatus.ASKING);
   }

   void getAmountOfCups() {
      databaseBO.getCupDrinkers()
            .addValueEventListener(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  int cupsAmount = (int) dataSnapshot.getChildrenCount();
                  PreparationViewModel preparationObject = Barista.buildPreparatioViewModel(cupsAmount);
                  databaseBO.setTimeToBrew(preparationObject.getTimeInMillis());
                  view.displayPreparationInformation(preparationObject);
               }
            });
   }

   void setPatienceStatus() {
      databaseBO.setStatus(CoffeeStatus.PATIENCE);
      databaseBO.setStartTime();
   }
}
