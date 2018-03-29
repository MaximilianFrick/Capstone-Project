package com.frick.maximilian.coffeetime.status.views.patience;

import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.CoffeeTimeApi;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.data.models.ServerTime;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;
import com.frick.maximilian.coffeetime.status.views.asking.Barista;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PatiencePresenter {
   @Inject
   CoffeeTimeApi coffeeTimeApi;
   @Inject
   DatabaseBO databaseBO;
   private Long remainingTime;
   private Disposable serverTimeSubscription;
   private PatienceContract.View view;

   PatiencePresenter(PatienceContract.View view) {
      Injector.getAppComponent()
            .inject(this);
      this.view = view;
   }

   public void loadCoffeenessStatus() {
      databaseBO.setStatus(CoffeeStatus.COFFEENESS);
   }

   public void unbind() {
      if (serverTimeSubscription != null && !serverTimeSubscription.isDisposed()) {
         serverTimeSubscription.dispose();
      }
   }

   void listenToRemainingTime() {
      serverTimeSubscription = coffeeTimeApi.getCurrentServerTime(databaseBO.getCurrentGroup())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(new Function<ServerTime, Observable<RemainingTimeViewModel>>() {
               @Override
               public Observable<RemainingTimeViewModel> apply(ServerTime serverTime)
                     throws Exception {
                  return startTimer(serverTime);
               }
            })
            .takeUntil(new Predicate<RemainingTimeViewModel>() {
               @Override
               public boolean test(RemainingTimeViewModel remainingTimeViewModel) throws Exception {
                  return remainingTimeViewModel.getRemainingTime() <= 0;
               }
            })
            .subscribe(new Consumer<RemainingTimeViewModel>() {
               @Override
               public void accept(RemainingTimeViewModel remainingTimeViewModel) throws Exception {
                  view.displayRemainingTime(remainingTimeViewModel);
               }
            }, new Consumer<Throwable>() {
               @Override
               public void accept(Throwable throwable) throws Exception {
                  Timber.e(throwable);
               }
            }, new Action() {
               @Override
               public void run() throws Exception {
                  databaseBO.setStatus(CoffeeStatus.COFFEENESS);
               }
            });
   }

   private Observable<RemainingTimeViewModel> startTimer(final ServerTime serverTime) {
      remainingTime = Barista.getRemainingTime(serverTime) + 2;
      return Observable.interval(1000, TimeUnit.MILLISECONDS)
            .flatMap(new Function<Long, Observable<RemainingTimeViewModel>>() {
               @Override
               public Observable<RemainingTimeViewModel> apply(Long past) throws Exception {
                  return Observable.just(Barista.buildRemainingTimeViewModel(serverTime,
                        remainingTime - (past * 1000)));
               }
            })
            .observeOn(AndroidSchedulers.mainThread());
   }
}
