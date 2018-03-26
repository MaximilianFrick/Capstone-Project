package com.frick.maximilian.coffeetime.status.views.preparation;

import com.frick.maximilian.coffeetime.status.views.asking.PreparationViewModel;

public interface PreparationContract {
   interface View {
      void displayPreparationInformation(PreparationViewModel data);
   }
}
