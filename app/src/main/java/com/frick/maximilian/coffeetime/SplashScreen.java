package com.frick.maximilian.coffeetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.frick.maximilian.coffeetime.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

   private static final int RC_SIGN_IN = 123;
   List<AuthUI.IdpConfig> providers =
         Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
               new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == RC_SIGN_IN) {
         IdpResponse response = IdpResponse.fromResultIntent(data);
         if (resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance()
                  .getCurrentUser();
            if (user != null) {
               Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_LONG)
                     .show();
            }
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            // ...
         } else {
            // Sign in failed, check response for error code
            // ...
         }
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splashscreen);
      startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.ic_logo)
            .setAvailableProviders(providers)
            .build(), RC_SIGN_IN);
   }
}
