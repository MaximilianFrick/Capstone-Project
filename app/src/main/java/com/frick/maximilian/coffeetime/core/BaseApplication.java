package com.frick.maximilian.coffeetime.core;

import android.app.Application;

import com.frick.maximilian.coffeetime.BuildConfig;

import timber.log.Timber;

public class BaseApplication extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
      Injector.init(this);
      if (BuildConfig.DEBUG) {
         Timber.plant(new Timber.DebugTree());
      }
   }
}
