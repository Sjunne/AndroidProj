package com.example.andapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.andapp.R;
import com.example.andapp.viewmodel.BookingViewModel;
import com.example.andapp.viewmodel.EquipmentViewModel;
import com.example.andapp.viewmodel.LoginActivityViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationDrawer;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private LoginActivityViewModel loginActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set XML/View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        //ViewModels
        //loginVM used for logging out
        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        //local scope for running init() at home page
        EquipmentViewModel equipmentViewModel = new ViewModelProvider(this).get(EquipmentViewModel.class);
        equipmentViewModel.init();
        BookingViewModel bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        bookingViewModel.init();

        //Setting up toolbar/action bar
        initViews();
        setupNavigation();

        //vil have en logout button på min navigation drawer, har vidst ikke en implementeret funktion
        //så mit "fix" er at lave en ItemClickListener på mit menu item "nav_logout"
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });


        /**
         * DÅRLIGT FIX;
         * Firebase ADMIN SDK ville lade mig ændre på user rettighedder.
         * Andet muligt fix:
         * Lav User "root" i RealTime Database,
         * Gem Email + RettighedsLevel
         * Check på rettighedslevel her.
         *
         * Solution:
         * Brug den nemme metode, check på email, hav kun 1 Admin som er min "gym owner" i real life scenario
         * Tænker ikke et fix ved at gemme users i Realtime Database er særlig god alligevel
         */
        if(FirebaseAuth.getInstance().getCurrentUser() != null && !FirebaseAuth.getInstance().getCurrentUser().getEmail().equalsIgnoreCase("Sjunne1992@gmail.com"))
        {
            navigationView.getMenu().setGroupVisible(R.id.equipmentAdminView, false);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.my_toolbar);
        navigationDrawer = findViewById(R.id.navigation_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.admin_home)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationDrawer, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    private void logout() {
          loginActivityViewModel.logout(complete -> {
              if(complete)
              {
                  startActivity(new Intent(MainActivity.this, LoginActivity.class));
                  finish();
              }
          });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
