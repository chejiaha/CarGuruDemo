package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MyGarageSplash extends AppCompatActivity {
    //creating Page context
    Context context;
    //Creating View Objects
    Button btnSignIn;
    Button btnSignUp;
    boolean loggedIn = false;

    //This list will hold the Question IDs so we can go forwards and back to get the questions.
    int[] questionIDs;
    String[] questionAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creating context
        context = getApplicationContext();

        if (loggedIn == true){
            //If the user is logged in, Send them Directly to the MyGarage Page.
            Intent intent = new Intent(context, MyGaragePage.class);
            startActivity(intent);
        }else{
            //Otherwise send them to the splash page
            setContentView(R.layout.activity_mygarage_splash);
        }


    }
}
