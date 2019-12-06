package com.rarito.movietvshow4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.fragment.MovieFavoriteFragment;
import com.rarito.movietvshow4.fragment.MovieFragment;
import com.rarito.movietvshow4.fragment.TvShowFavoriteFragment;
import com.rarito.movietvshow4.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomnav_view);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_movie_favorite, R.id.navigation_tvshow_favorite)
//                .build();
//
//        NavController navController = Navigation.findNavController(this, R.id.navigation_container_frag);
//
//        // Digunakan mengatur judul AppBar agar sesuai dengan fragment yang ditampilkan
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        // Supaya ButtomNavigation menampilkan Fragment yang sesaui dipilih
//        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    selectFragment = new MovieFragment();
                    break;
                case R.id.navigation_tvshow:
                    selectFragment = new TvShowFragment();
                    break;
                case R.id.navigation_movie_favorite:
                    selectFragment = new MovieFavoriteFragment();
                    break;
                case R.id.navigation_tvshow_favorite:
                    selectFragment = new TvShowFavoriteFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.navigation_container_frag, selectFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
