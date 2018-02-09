package com.frick.maximilian.coffeetime.core;

public final class Injector {
   private static AppComponent appComponent;

   public static AppComponent getAppComponent() {
      return appComponent;
   }

   static void init(BaseApplication application) {
      appComponent = DaggerAppComponent.builder()
            .applicationModule(new ApplicationModule(application))
            .build();
   }

   private Injector() {
      //should not be instantiated
   }
}
