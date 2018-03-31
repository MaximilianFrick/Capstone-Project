package com.frick.maximilian.coffeetime.status.views.coffeeness;

import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class CoffeenessPresenter {
   @Inject
   DatabaseBO databaseBO;
   private CoffeenessContract.View view;

   CoffeenessPresenter(CoffeenessContract.View view) {
      this.view = view;
      Injector.getAppComponent()
            .inject(this);
   }

   public void resetSession() {
      databaseBO.resetSession();
   }

   public void retrieveAmountOfCups() {
      databaseBO.getCupDrinkers()
            .addValueEventListener(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  Long amountOfCups = dataSnapshot.getChildrenCount();
                  if (amountOfCups == 0) {
                     databaseBO.resetSession();
                  }
                  view.showAmountOfCups(amountOfCups);
               }
            });
   }

   public void takeACup() {
      databaseBO.takeACup();
   }
}
