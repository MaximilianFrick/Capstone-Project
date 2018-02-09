package com.frick.maximilian.coffeetime.core;

import android.content.Context;

import com.frick.maximilian.coffeetime.SplashScreen;
import com.frick.maximilian.coffeetime.framework.core.DataModule;
import com.frick.maximilian.coffeetime.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { ApplicationModule.class, DataModule.class })
public interface AppComponent {

   BaseApplication application();

   Context context();

   void inject(HomeActivity homeActivity);

   void inject(SplashScreen splashScreen);
}
