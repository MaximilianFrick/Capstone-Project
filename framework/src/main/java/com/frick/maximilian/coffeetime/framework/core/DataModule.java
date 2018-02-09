package com.frick.maximilian.coffeetime.framework.core;

import com.frick.maximilian.coffeetime.framework.data.DatabaseBO;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

   @Provides
   @Singleton
   DatabaseBO provideDatabaseBO() {
      return new DatabaseBO(FirebaseDatabase.getInstance()
            .getReference());
   }
}
