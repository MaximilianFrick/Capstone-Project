package com.frick.maximilian.coffeetime.core;

import android.content.Context;

import com.frick.maximilian.coffeetime.SplashScreen;
import com.frick.maximilian.coffeetime.data.DataModule;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.frick.maximilian.coffeetime.status.StatusActivity;
import com.frick.maximilian.coffeetime.status.views.emptiness.EmptinessPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { ApplicationModule.class, DataModule.class })
public interface AppComponent {

   BaseApplication application();

   Context context();

   void inject(HomeActivity homeActivity);

   void inject(SplashScreen splashScreen);

   void inject(StatusActivity statusActivity);

   void inject(EmptinessPresenter emptinessPresenter);
}
