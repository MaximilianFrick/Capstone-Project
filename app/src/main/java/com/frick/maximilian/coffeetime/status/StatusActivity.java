package com.frick.maximilian.coffeetime.status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.data.models.Group;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity {

   private static final String EXTRA_GROUP = "extra.group";
   @Inject
   DatabaseBO databaseBO;
   @BindView (R.id.recyclerview)
   RecyclerView recyclerView;
   private StatusAdapter adapter;
   private Group group;
   private String groupId;

   public static Intent newIntent(Context context, String groupId) {
      Intent intent = new Intent(context, StatusActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      intent.putExtra(EXTRA_GROUP, groupId);
      return intent;
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.status_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.action_logout_group) {
         databaseBO.leaveGroup(group.getId());
         finishAndStartHome();
      }
      return true;
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
            .inject(this);
      setContentView(R.layout.status_activity);
      ButterKnife.bind(this);
      loadExtrasFromIntent();
      initRecyclerView();
      displayStatus();
   }

   @OnClick (R.id.reset)
   void onResetClicked() {
      databaseBO.resetSession();
   }

   private void displayStatus() {
      databaseBO.getStatusRef()
            .addValueEventListener(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  Long status = (Long) dataSnapshot.getValue();
                  adapter.setStatus(status != null ? status.intValue() : CoffeeStatus.IDLE);
               }
            });
   }

   private void finishAndStartHome() {
      startActivity(new Intent(this, HomeActivity.class));
      finish();
   }

   private void initRecyclerView() {
      adapter = new StatusAdapter();
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(adapter);
   }

   private void loadExtrasFromIntent() {
      Bundle extras = getIntent().getExtras();
      if (extras == null) {
         return;
      }
      if (extras.containsKey(EXTRA_GROUP)) {
         groupId = extras.getString(EXTRA_GROUP);
         databaseBO.setCurrentGroup(groupId);
         databaseBO.getGroupsRef()
               .child(groupId)
               .addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                     Toast.makeText(StatusActivity.this, "Loading group failed", Toast.LENGTH_LONG)
                           .show();
                     finishAndStartHome();
                  }

                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                     group = dataSnapshot.getValue(Group.class);
                     if (group == null) {
                        finishAndStartHome();
                        return;
                     }
                     group.setId(groupId);
                     setToolbarTitle();
                  }
               });
      }
   }

   private void setToolbarTitle() {
      getSupportActionBar().setTitle(group.getName());
   }
}
