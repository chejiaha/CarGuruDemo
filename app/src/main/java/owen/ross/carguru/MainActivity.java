package owen.ross.carguru;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/*
 *
 * This is the splash screen of our app.
 */
public class MainActivity extends AppCompatActivity {

    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hides the Top Action Bar.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();


        Intent intent = new Intent(this, HomeActivity.class);
        // Navigate to the next page after 3 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
            }
        }, 3000);


//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_search_car, R.id.navigation_questionnaire, R.id.navigation_my_garage, R.id.navigation_settings)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//
//        FirebaseApp.initializeApp(this);
    }

}