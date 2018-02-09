package com.frick.maximilian.coffeetime.core;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class ApplicationModule {
   private final BaseApplication application;
   private final Context context;

   ApplicationModule(BaseApplication application) {
      this.application = application;
      this.context = application.getApplicationContext();
   }

   @Provides
   @Singleton
   BaseApplication provideBaseApplication() {
      return application;
   }

   @Provides
   @Singleton
   Context provideContext() {
      return context;
   }
}
