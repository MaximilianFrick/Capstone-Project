package com.frick.maximilian.coffeetime.framework.core;

import android.content.Context;

import com.frick.maximilian.coffeetime.framework.BuildConfig;
import com.frick.maximilian.coffeetime.framework.R;
import com.frick.maximilian.coffeetime.framework.data.DatabaseBO;
import com.frick.maximilian.coffeetime.framework.rest.CoffeeTimeApi;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

   private static final long CACHE_SIZE = 25 * 1024 * 1024;

   @Provides
   @Singleton
   CoffeeTimeApi provideCoffeTimeApi(Retrofit retrofit) {
      return retrofit.create(CoffeeTimeApi.class);
   }

   @Provides
   @Singleton
   DatabaseBO provideDatabaseBO() {
      return new DatabaseBO(FirebaseDatabase.getInstance()
            .getReference());
   }

   @Provides
   HttpLoggingInterceptor provideHttpLoggingInterceptor() {
      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
      if (BuildConfig.DEBUG) {
         httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      } else {
         httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
      }
      return httpLoggingInterceptor;
   }


   @Provides
   @Singleton
   OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Context context) {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      final File cacheDirectory = new File(context.getCacheDir()
            .getAbsolutePath(), "HttpCache");
      final Cache cache = new Cache(cacheDirectory, CACHE_SIZE);
      builder.addInterceptor(loggingInterceptor);
      builder.cache(cache);
      //      builder.addInterceptor(new Interceptor() {
      //         @Override
      //         public Response intercept(Chain chain) throws IOException {
      //            Request original = chain.request();
      //            HttpUrl originalHttpUrl = original.url();
      //            HttpUrl url = originalHttpUrl.newBuilder()
      //                  .build();
      //
      //            // Request customization: add request headers
      //            Request.Builder requestBuilder = original.newBuilder()
      //                  .url(url);
      //
      //            Request request = requestBuilder.build();
      //            return chain.proceed(request);
      //         }
      //      });
      return builder.build();
   }

   @Provides
   @Singleton
   Retrofit provideRetrofit(Context context, OkHttpClient httpClient) {
      Retrofit.Builder builder = new Retrofit.Builder();
      builder.baseUrl(context.getString(R.string.base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient);
      return builder.build();
   }
}
