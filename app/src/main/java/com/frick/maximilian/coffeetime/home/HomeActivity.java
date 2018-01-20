package com.frick.maximilian.coffeetime.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.SplashScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.home_menu, menu);
      return true;
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
   }
}
