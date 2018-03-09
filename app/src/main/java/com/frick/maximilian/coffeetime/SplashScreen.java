package com.frick.maximilian.coffeetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.data.DatabaseBO;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.frick.maximilian.coffeetime.status.StatusActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashScreen extends AppCompatActivity {

   private static final int RC_SIGN_IN = 123;
   @Inject
   DatabaseBO databaseBO;
   List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(),
         new AuthUI.IdpConfig.EmailBuilder().build());
   private FirebaseUser user;

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == RC_SIGN_IN) {
         IdpResponse response = IdpResponse.fromResultIntent(data);
         if (resultCode == RESULT_OK) {
            // Successfully signed in
            if (user != null) {
               Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_LONG)
                     .show();
               databaseBO.addUserToDb();
               checkIfUserHasACurrentGroup(user);
            }
         } else {
            Toast.makeText(this,
                  String.format(Locale.getDefault(), "Login/Register failed! ErrorCode: %d",
                        response != null ? response.getErrorCode() : 0), Toast.LENGTH_LONG)
                  .show();
         }
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
            .inject(this);
      setContentView(R.layout.splashscreen);
      user = FirebaseAuth.getInstance()
            .getCurrentUser();
      if (user != null) {
         checkIfUserHasACurrentGroup(user);
      } else {
         startActivityForResult(AuthUI.getInstance()
               .createSignInIntentBuilder()
               .setLogo(R.drawable.ic_logo)
               .setAvailableProviders(providers)
               .build(), RC_SIGN_IN);
      }
   }

   private void checkIfUserHasACurrentGroup(FirebaseUser user) {
      getIdToken(user);
      databaseBO.getGroupOfUser(user.getUid())
            .addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onCancelled(DatabaseError databaseError) {
                  Toast.makeText(SplashScreen.this,
                        String.format("Login/Register failed! ErrorCode: %s",
                              databaseError.getMessage()), Toast.LENGTH_LONG)
                        .show();
               }

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  String groupId = (String) dataSnapshot.getValue();
                  if (groupId == null) {
                     startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                  } else {
                     startActivity(StatusActivity.newIntent(SplashScreen.this, groupId));
                  }
                  finish();
               }
            });
   }

   private void getIdToken(FirebaseUser user) {
      user.getIdToken(true)
            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
               @Override
               public void onComplete(@NonNull Task<GetTokenResult> task) {
                  if (task.isSuccessful()) {
                     Timber.d("TOKEN from task %s", task.getResult()
                           .getToken());
                  }
               }
            });
   }
}