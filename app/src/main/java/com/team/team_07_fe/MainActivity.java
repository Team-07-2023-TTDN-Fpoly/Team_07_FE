package com.team.team_07_fe;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private Toolbar toolbar;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.bottomNavigationView);
        setSupportActionBar(toolbar);

        // Passing each menu ID as a set of Ids because each
        //NavigationView
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contract_update, R.id.navigation_dress,
                R.id.navigation_customer,R.id.navigation_settings)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //BottomNavigationView
        NavigationUI.setupWithNavController(navView, navController);

    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //Hiển thị thanh bottomBar
    public void showBottomBar(){
        navView.setVisibility(View.VISIBLE);
    }
    //Ẩn thanh bottomBar
    public void hiddenBottomBar(){
        navView.setVisibility(View.GONE);
    }
}