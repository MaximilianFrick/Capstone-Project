package com.frick.maximilian.coffeetime.status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.data.models.Group;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity {

   private static final String EXTRA_GROUP = "extra.group";
   @Inject
   DatabaseBO databaseBO;
   private Group group;
   private String groupId;

   public static Intent newIntent(Context context, String groupId) {
      Intent intent = new Intent(context, StatusActivity.class);
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
   }

   @OnClick (R.id.ask_button)
   void onAskClicked() {
      sendAskNotificationToAllMembers();
   }

   private void sendAskNotificationToAllMembers() {
      databaseBO.setStatus(groupId, CoffeeStatus.ASKING);
   }

   @OnClick(R.id.reset_button)
   void onResetClicked() {
      databaseBO.setStatus(groupId, CoffeeStatus.IDLE);
   }

   private void finishAndStartHome() {
      startActivity(new Intent(this, HomeActivity.class));
      finish();
   }

   private void loadExtrasFromIntent() {
      Bundle extras = getIntent().getExtras();
      if (extras == null) {
         return;
      }
      if (extras.containsKey(EXTRA_GROUP)) {
         groupId = extras.getString(EXTRA_GROUP);
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
