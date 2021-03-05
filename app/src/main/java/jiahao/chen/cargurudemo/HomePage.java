package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
//Footer menu implementation
//https://material.io/components/bottom-navigation/android#using-bottom-navigation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    }

// Find a car Link
    public void onClickToGuru (View view){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, FindACarForYou.class);
        startActivity(intent);
    }
// Find a Specific Model Link
    public void onClickToSpecificModel (View view){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, FindSpecificModel.class);
        startActivity(intent);
    }

// Browse By Category Link
    public void onClickToCategories (View view){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, BrowseByCategory.class);
        startActivity(intent);
    }
// Car News Link
//    public void onClickToNews (View view){
//
//    }
//Settings Link
public void onClickToSettings (View view){
    Context context = getApplicationContext();
    Intent intent = new Intent(context, Settings.class);
    startActivity(intent);
}
// Home Page Link
public void onClickToHomePage (View view){
    Context context = getApplicationContext();
    Intent intent = new Intent(context, FindACarForYou.class);
    startActivity(intent);
}
// To Garage Page
//public void onClickToMyGarage (View view){
//
//
//}
}

