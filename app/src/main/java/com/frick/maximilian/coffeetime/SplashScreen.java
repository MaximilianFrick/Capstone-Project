package com.frick.maximilian.coffeetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.frick.maximilian.coffeetime.core.Injector;
import com.frick.maximilian.coffeetime.framework.data.DatabaseBO;
import com.frick.maximilian.coffeetime.framework.models.User;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class SplashScreen extends AppCompatActivity {

   private static final int RC_SIGN_IN = 123;
   @Inject
   DatabaseBO databaseBO;
   List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(),
         new AuthUI.IdpConfig.EmailBuilder().build());
   private FirebaseAuth auth;

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == RC_SIGN_IN) {
         IdpResponse response = IdpResponse.fromResultIntent(data);
         if (resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
               Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_LONG)
                     .show();
               databaseBO.addUserToDb(user.getUid(), new User(user.getDisplayName()));
               startHomeScreen();
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
      auth = FirebaseAuth.getInstance();
      if (auth.getCurrentUser() != null) {
         startHomeScreen();
      } else {
         startActivityForResult(AuthUI.getInstance()
               .createSignInIntentBuilder()
               .setLogo(R.drawable.ic_logo)
               .setAvailableProviders(providers)
               .build(), RC_SIGN_IN);
      }
   }

   private void startHomeScreen() {
      startActivity(new Intent(this, HomeActivity.class));
      finish();
   }
}
