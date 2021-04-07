package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavType;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class QuestionnaireQuestionsFragment2 extends Fragment {

    public QuestionnaireQuestionsFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    RadioGroup rgQuestion;
    Button btnNext;
    Button btnBack;
    RadioButton rbAnswerbutton;
    TextView tvQuestion;
    TextView tvQuestionNumber;
    TextView tvQuestionChoice;
    //Creating my referenced to the database
    DatabaseReference dbRef;
    ValueEventListener listener;

    // Variables
    Question questionObj;
    // Total list of questions
    List<Question> questionsList;
    // Setting up one question
    ArrayList<String> answersList;
    ArrayList<String> answerValueList;
    String question = "";
    String category = "";
    // Int that will be the question Number
    int questionNum = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_questions2, container, false);
        //



        // Set the View Items
//        rgQuestion = view.findViewById(R.id.rgQuestions);
//        btnBack = view.findViewById(R.id.BtnPreviousPage);
//        btnNext = view.findViewById(R.id.BtnNextPage);
//        tvQuestion = view.findViewById(R.id.tvQuestion);
//        tvQuestionNumber = view.findViewById(R.id.tvQuestionNumber);
//        tvQuestionChoice = view.findViewById(R.id.tvQuestionChoice);

        //Get the items from the bundle.
        Bundle bundle = new Bundle();
        questionNum = ((int) getArguments().getInt("QuestionNumber"));
        //debug
        Log.d("QuestionnaireQuestions2", "QuestionNum" + questionNum);
        questionsList = (ArrayList<Question>) bundle.getSerializable("QuestionList");
        //questionsList = ((ArrayList) getArguments().getParcelableArrayList("QuestionList"));
        //answersList = new ArrayList<>();
        //answerValueList = new ArrayList<>();

        //Placing the Question into a Question object
        questionObj = questionsList.get(questionNum);


        //Get the values from the bundle

        // Inflate the layout for this fragment
        return view;
    }
}