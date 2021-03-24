package jiahao.chen.cargurudemo;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MyGaragePage extends AppCompatActivity {
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_question_page);
        //Creating context
        context = getApplicationContext();
    }
}
