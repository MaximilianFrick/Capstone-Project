package com.frick.maximilian.coffeetime.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.SplashScreen;
import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.data.models.Group;
import com.frick.maximilian.coffeetime.status.StatusActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements GroupSelectedListener {
   @Inject
   DatabaseBO databaseBO;
   @BindView (R.id.no_groups_info)
   ViewGroup noGroupsInfoView;
   @BindView (R.id.recyclerview)
   RecyclerView recyclerView;
   private GroupsAdapter adapter;
   private DatabaseReference ref;

   public static Intent newIntent(Context context) {
      Intent intent = new Intent(context, StatusActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      return intent;
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.home_menu, menu);
      return true;
   }

   @Override
   public void onGroupClicked(final Group group) {
      databaseBO.joinGroup(group.getId());
      startActivity(StatusActivity.newIntent(this, group.getId()));
      finish();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.action_logout) {
         AuthUI.getInstance()
               .signOut(this)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {
                        startActivity(new Intent(HomeActivity.this, SplashScreen.class));
                        finish();
                     } else {
                        Toast.makeText(HomeActivity.this, "logout failed", Toast.LENGTH_LONG)
                              .show();
                     }
                  }
               });
      }
      return true;
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.home_activity);
      ButterKnife.bind(this);
      Injector.getAppComponent()
            .inject(this);
      initList();
   }

   @Override
   protected void onPause() {
      super.onPause();
      adapter.stopListening();
   }

   @Override
   protected void onResume() {
      super.onResume();
      adapter.startListening();
   }

   @OnClick (R.id.myfab)
   void onAddGroupClicked() {
      showAddGroupNameDialog();
   }

   private void createGroup(String groupName) {
      databaseBO.createGroup(new Group(groupName, true));
   }

   private SnapshotParser<Group> getSnapshotParser() {
      return new SnapshotParser<Group>() {
         @Override
         public Group parseSnapshot(DataSnapshot dataSnapshot) {
            Group group = dataSnapshot.getValue(Group.class);
            if (group != null) {
               noGroupsInfoView.setVisibility(View.GONE);
               group.setId(dataSnapshot.getKey());
            }
            return group;
         }
      };
   }

   private void initList() {
      SnapshotParser<Group> parser = getSnapshotParser();
      FirebaseRecyclerOptions<Group> options =
            new FirebaseRecyclerOptions.Builder<Group>().setQuery(databaseBO.getGroupsRef(), parser)
                  .build();
      adapter = new GroupsAdapter(options, this);
      if (adapter.getItemCount() > 0) {
         noGroupsInfoView.setVisibility(View.GONE);
      } else {
         noGroupsInfoView.setVisibility(View.VISIBLE);
      }
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(adapter);
   }

   private void showAddGroupNameDialog() {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      final EditText edittext = new EditText(this);
      builder.setMessage(R.string.add_group_dialog_msg);
      builder.setView(edittext);
      builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int whichButton) {
            createGroup(edittext.getText()
                  .toString());
         }
      });
      builder.setNegativeButton(R.string.abort, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int whichButton) {
         }
      });
      builder.show();
   }
}
