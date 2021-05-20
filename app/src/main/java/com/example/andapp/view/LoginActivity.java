package com.example.andapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andapp.R;
import com.example.andapp.viewmodel.LoginActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel loginActivityViewModel;
    private static final int RC_SIGN_IN = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set XML/View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivitytwo);

        //ViewModel
        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        //checker om vi er signed in allerede i "caching"
        checkIfSignedIn();
    }

    public void onLogin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(false).build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .build();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInRequest(resultCode);
        }
    }

    private void handleSignInRequest(int resultCode) {
        if (resultCode == RESULT_OK)
            goToLoginActivity();
        else
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
    }

    private void goToLoginActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void checkIfSignedIn() {
        LiveData<FirebaseUser> currentUser = loginActivityViewModel.getCurrentUser();
        if(currentUser != null)
        {
            loginActivityViewModel.getCurrentUser().observe(this, user -> {
                if (user != null)
                    goToMainActivity();
            });
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}