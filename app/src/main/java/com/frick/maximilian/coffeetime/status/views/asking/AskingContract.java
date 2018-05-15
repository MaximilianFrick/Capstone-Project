package com.frick.maximilian.coffeetime.status.views.asking;

interface AskingContract {
   interface View {
      void displayAmountOfCups(long cupDrinkerAmount);

      void enablePrepareButton(boolean hasCups);
   }
}
