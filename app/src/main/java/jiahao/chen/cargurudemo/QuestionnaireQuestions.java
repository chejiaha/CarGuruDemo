package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuestionnaireQuestions extends AppCompatActivity {
    //creating Page context
    Context context;
    //Creating View Objects
    Button btnNext;
    Button btnBack;
    ProgressBar pbQuestionProgress;
    RadioGroup rgQuestions;
    RadioButton rbAnswer1;
    RadioButton rbAnswer2;
    RadioButton rbAnswer3;
    RadioButton rbAnswer4;
    //This list will hold the Question IDs so we can go forwards and back to get the questions.
    int[] questionIDs;
    String[] questionAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_question_page);
        //Creating context
        context = getApplicationContext();

        //Connecting the Objects with the view
        btnNext = findViewById(R.id.btnViewByCategory);
        btnBack = findViewById(R.id.btnViewPreviousResults);
        rgQuestions = findViewById(R.id.rgQuestions);
        rbAnswer1 = findViewById(R.id.rbAnswer1);
        rbAnswer2 = findViewById(R.id.rbAnswer2);
        rbAnswer3 = findViewById(R.id.rbAnswer3);
        rbAnswer4 = findViewById(R.id.rbAnswer4);
        pbQuestionProgress = findViewById(R.id.pbProgress);

       /* btnNext.setOnClickListener(this::onClickNextQuestion);
        btnBack.setOnClickListener(this::onClickPreviousQuestion);*/
    }

/*    public void OnClickAnswerQuestion(View view){

    }*/

    // Navigate to ChooseCarByCategory Page
    public void onClickNextQuestion (View view){
        Intent intent = new Intent(context, BrowseByCategory.class);
        startActivity(intent);
    }
    // Navigate to ChooseCarByCategory Page
    public void onClickPreviousQuestion (View view){
        Intent intent = new Intent(context, BrowseByCategory.class);
        startActivity(intent);
    }

    public void getNextQuestion(){

    }
}
