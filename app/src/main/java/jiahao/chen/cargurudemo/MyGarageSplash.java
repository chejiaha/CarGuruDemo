package jiahao.chen.cargurudemo;

import android.content.Context;
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

    //This list will hold the Question IDs so we can go forwards and back to get the questions.
    int[] questionIDs;
    String[] questionAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_question_page);
        //Creating context
        context = getApplicationContext();
    }
}
