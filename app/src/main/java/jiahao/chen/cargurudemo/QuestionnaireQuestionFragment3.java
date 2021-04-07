package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionnaireQuestionFragment3 extends Fragment {



    public QuestionnaireQuestionFragment3() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

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
    ArrayList<Question> questionsList;
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
        view = inflater.inflate(R.layout.fragment_questionnaire_question3, container, false);

        // Set the View Items
        rgQuestion = view.findViewById(R.id.rgQuestions);
        btnBack = view.findViewById(R.id.BtnPreviousPage);
        btnNext = view.findViewById(R.id.BtnNextPage);
        tvQuestion = view.findViewById(R.id.tvQuestion);
//        tvQuestionNumber = view.findViewById(R.id.tvQuestionNumber);
        tvQuestionChoice = view.findViewById(R.id.tvQuestionChoice);

        //Get the items from the bundle.
        Bundle bundle = new Bundle();
        questionNum = ((int) getArguments().getInt("QuestionNumber"));
        //debug
        Log.d("QuestionnaireQuestions2", "QuestionNum" + questionNum);
        questionsList = (ArrayList<Question>) getArguments().getSerializable("QuestionList");
        //questionsList = ((ArrayList) getArguments().getParcelableArrayList("QuestionList"));
        //answersList = new ArrayList<>();
        //answerValueList = new ArrayList<>();

        //Placing the Question into a Question object
        questionObj = questionsList.get(questionNum);
        if (questionObj != null) {
            //Adding the Question so we can populate the interface
            tvQuestion.setText(String.format("Q%s. %s", questionNum, questionObj.getQuestion()));
            answersList = questionObj.getAnswers();
            answerValueList = questionObj.getValues();
            //Populate the radio buttons with the question descriptions.
            //rgQuestion is the radio group its being added to.
            int numAnswers = answersList.size();
            for (int i = 0; i < numAnswers; i++) {
                RadioButton rbanswer = new RadioButton(getActivity());
                rbanswer.setId(View.generateViewId());
                //rbanswer.setId("Q" + questionNum);
                rbanswer.setText(answersList.get(i));
                rgQuestion.addView(rbanswer);
            }
        }



        //Setup the Onclick Listener
        btnNext.setOnClickListener(onClickNext);
        // Inflate the layout for this fragment
        return view;
    }

    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will also keep a count of the number of points per category. Once the number for a specific
     * category reaches 3 (THIS WILL CHANGE) we can place them into the specified category.
     *
     */
    public View.OnClickListener  onClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // The number that will represent how many questions until category is determined
            int FALLTHROUGH_NUM = 3;
            boolean categoryDetermined = false;
            // If the user clicks next, check if an option was checked
            int radioId;
            try{
                radioId = rgQuestion.getCheckedRadioButtonId();
            }catch (NullPointerException err){
                radioId = 0;
                Toast.makeText(getActivity(), "Please Choose an option", Toast.LENGTH_LONG).show();
                return;
            }

            // Add up the values for each category
            int sportsCategory = 0;
            int commuterCategory = 0;
            //TODO These will be implemented once they are added in
            int familyCategory = 0;
            int utilityCategory = 0;
            int beaterCategory = 0;
            int luxuryCategory = 0;
            // Go through the values list and compare strings Values ["CategoryName", "CategoryName", "CategoryName"]
            for (String value : questionObj.getValues()){
                switch (value){
                    case "Commuter":
                        commuterCategory++;
                        Log.d("COMMUTER", "Determained its a COMUTTER" + commuterCategory);
                        break;
                    case "Sports":
                        sportsCategory++;
                        Log.d("SPORTS", "Determained its a SPORTS" + sportsCategory);
                        break;
                    case "Family":
                        familyCategory++;
                        break;
                    case "Beater":
                        beaterCategory++;
                        break;
                    case "Utility":
                        utilityCategory++;
                        break;
                    case "Luxury":
                        luxuryCategory++;
                        break;
                }
                if (commuterCategory >= FALLTHROUGH_NUM){
                    categoryDetermined = true;
                    category = "Commuter";
                    break;
                }else if (sportsCategory >= FALLTHROUGH_NUM){
                    category = "Sports";
                    categoryDetermined = true;
                    break;
                }else if (familyCategory >= FALLTHROUGH_NUM){
                    category = "Family";
                    categoryDetermined = true;
                    break;
                }else if (beaterCategory >= FALLTHROUGH_NUM){
                    category = "Beater";
                    categoryDetermined = true;
                    break;
                }else if (utilityCategory >= FALLTHROUGH_NUM){
                    category = "Utility";
                    categoryDetermined = true;
                    break;
                }else if (luxuryCategory >= FALLTHROUGH_NUM){
                    category = "Luxury";
                    categoryDetermined = true;
                    break;
                }
            }


            // If the category was determined push onto the next questions
            if (categoryDetermined){
                Fragment fragment = new HomePage_Fragment();
                // create a FragmentManager
                FragmentManager fm = getFragmentManager();
                // create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                // replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit(); // save the changes
                //TODO pass all the values and go to the next category pages.
            }else{
                /*
                   LUKA NOTES
                   Continue to the next question in the same category.
                   Pass the questionList to the next page to be displayed by the user
                   Pass the questionNum
                   TODO Pass the progress bar
                */

                //Adding the arguments into the bundle
                Bundle bundle = new Bundle();
                bundle.putInt("QuestionNumber", questionNum);
                bundle.putSerializable("QuestionList",(ArrayList<Question>) questionsList);

                //Setting up the Fragment
                Fragment fragment = new QuestionnaireQuestionsFragment2();
                fragment.setArguments(bundle);
                // create a FragmentManager
                FragmentManager fm = getFragmentManager();
                // create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                // replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit(); // save the changes

            }
            //The Value has been determined and

        }
    };

    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will subtract one from the count of points based on the category they chose last.
     * This value is passed as a string so it knows what points to take off.
     *
     */
    private View.OnTouchListener onClickBack = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return false;
        }
    };
}