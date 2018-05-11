package com.frick.maximilian.coffeetime.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.status.views.StatusView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class UpdateWidgetService extends Service {
   private RemoteViews remoteViews;

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
      for (final int widgetId : allWidgetIds) {
         remoteViews = new RemoteViews(this.getApplicationContext()
               .getPackageName(), R.layout.coffeetime_widget);
         final FirebaseUser user = FirebaseAuth.getInstance()
               .getCurrentUser();
         final DatabaseBO databaseBO = new DatabaseBO(FirebaseDatabase.getInstance()
               .getReference());
         final AppWidgetManager manager = AppWidgetManager.getInstance(UpdateWidgetService.this);
         if (user != null) {
            databaseBO.getGroupOfUser(user.getUid())
                  .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }

                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                        String groupId = (String) dataSnapshot.getValue();
                        if (groupId != null) {
                           loadStatusAndUpdateWidget(groupId, widgetId, databaseBO, manager);
                        }
                     }
                  });
         }
      }
      stopSelf();
      return super.onStartCommand(intent, flags, startId);
   }

   private void loadStatusAndUpdateWidget(String groupId, final int widgetId, DatabaseBO databaseBO,
         final AppWidgetManager manager) {
      databaseBO.getGroupsRef()
            .child(groupId)
            .child("session")
            .child("status")
            .addValueEventListener(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {
               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  if (dataSnapshot == null) {
                     Timber.e("[WIDGET] datasnapshot == null");
                     return;
                  }
                  Long status = (Long) dataSnapshot.getValue();
                  if (status == null) {
                     return;
                  }
                  Timber.d("[Widget] Coffeetime Service status changed: %d", status);
                  int drawableRes;

                  if (status == StatusView.CoffeeStatus.COFFEENESS) {
                     drawableRes = R.drawable.ic_widget_full;
                  } else if (status == StatusView.CoffeeStatus.PATIENCE) {
                     drawableRes = R.drawable.ic_widget_process;
                  } else {
                     drawableRes = R.drawable.ic_widget_empty;
                  }
                  remoteViews.setImageViewResource(R.id.img_in_widget, drawableRes);

                  manager.updateAppWidget(widgetId, remoteViews);
               }
            });
   }
}
