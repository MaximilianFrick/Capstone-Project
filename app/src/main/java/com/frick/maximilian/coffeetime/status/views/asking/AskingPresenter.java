package com.frick.maximilian.coffeetime.status.views.asking;

import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class AskingPresenter {
   @Inject
   DatabaseBO databaseBO;
   private final AskingContract.View view;

   AskingPresenter(AskingContract.View view) {
      this.view = view;
      Injector.getAppComponent()
            .inject(this);
   }

   void addCupDrinker() {
      databaseBO.addMeToSession();
   }

   void listenToNumberOfDrinkers() {
      databaseBO.getCupDrinkers()
            .addValueEventListener(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  long cupDrinkerAmount = dataSnapshot.getChildrenCount();
                  view.enablePrepareButton(cupDrinkerAmount > 0);
                  view.displayAmountOfCups(cupDrinkerAmount);
               }
            });
   }

   void setPrepareStatus() {
      databaseBO.setStatus(CoffeeStatus.PREPARATION);
   }
}
