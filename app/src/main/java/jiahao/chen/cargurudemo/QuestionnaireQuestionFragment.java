package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 *
 *
 */
public class QuestionnaireQuestionFragment extends Fragment {


    public QuestionnaireQuestionFragment() {
        // Required empty public constructor
    }


    /*
     *
     *
     */
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_questionnaire_question, container, false);

        // Set the View Items
        rgQuestion = view.findViewById(R.id.rgQuestions);
        btnBack = view.findViewById(R.id.BtnPreviousPage);
        btnNext = view.findViewById(R.id.BtnNextPage);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        //tvQuestionNumber = view.findViewById(R.id.tvQuestionNumber);
        tvQuestionChoice = view.findViewById(R.id.tvQuestionChoice);
        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();
        answerValueList = new ArrayList<>();
        questionObj = new Question();

        // Get the Answers from the database put them in a list
        dbRef = FirebaseDatabase.getInstance().getReference().child("Questions");
        //Create radio buttons with id's that are
        listener = dbRef.addValueEventListener(new ValueEventListener() {
            /*
             *   This method is used to get the Question information from the database.
             *   It will go through every question in the specified category.
             *   Each Question is stored in a questionList. This list will contain the question,
             *   category, List of answers, and List of Values.
             *   This will be used in the onCreate method to look through the questions and
             *   populate the Questionnaire page
             *
             *   Will Populate:
             *       ArrayList <Question> questionList
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //Checking if the Category is null
                /* if (category == null){
                    category = "";
                }*/

                // Used when the user is placed in the category to go into the db to find the questions
                String DBcategory = "";
                String categoryString;
                String questionData;
                // NOTE : If there is no category chosen, The Category = MainQuestions.
                // Finding the Category
                if (category.equals("") || category == null){
                    DBcategory = "MainQuestion";
                }else if (category.equals("Commuter")){
                    DBcategory = "CommuterQuestion";
                }else if (category.equals("Sports")){
                    DBcategory = "SportsQuestion";
                }

                // Get the question from the database
                //dataSnapshot Children: MainQuestions, Commuter Questions, Sports Questions...
                for (DataSnapshot QuestionCategory : dataSnapshot.child(DBcategory).getChildren()) {
                    // Children (dependent on category): mq1, mq2, mq3, or cq1, cq2 or sq1, sq2.....
                    categoryString = QuestionCategory.getKey();
                    answersList = new ArrayList<>();
                    answerValueList = new ArrayList<>();
                    questionObj = new Question();
                    //debug
                    Log.d("QuestionnaireCategory", "The Category string is " + categoryString);
                    for (DataSnapshot QuestionInfo : dataSnapshot.child(DBcategory).child(categoryString).getChildren()){
                        // Children: Answers, DBFiled, Description

                        // Depending on the question number get the respective question.
                        questionData = QuestionInfo.getKey();
                        //debug
                        Log.d("QuestionnaireInfo", "The INFO string is " + questionData);
                        // Get the Question String
                        if (questionData.equals("Description")){
                            //Debug
                            Log.d("QuestionnaireQuestion", "The Question String: " + QuestionCategory.getValue().toString());
                            question = QuestionInfo.getValue().toString();
                            questionObj.setQuestion(question);
                        } else if (questionData.equals("DBField")){
                            // Do nothing for now
                            category = QuestionInfo.getValue().toString();
                            questionObj.setQuestion(category);
                        }
                        else if (questionData.equals("Answers")) {
                            Log.d("TestAnswerLoop", "This should loop for answers: " + questionData);
                            //for each question in this section get the answers
                            for (DataSnapshot QuestionAnswers : dataSnapshot.child(DBcategory).child(categoryString).child(questionData).getChildren()) {
                                // Children: mqa11, mqa12....
                                //debug
                                Log.d("QuestionnaireAnswerQ", "The AnswerQuestion string is " + questionData);
                                String answerNumber = QuestionAnswers.getKey();
                                Log.d("QuestionnaireAnswerQ", "The AnswerNumber string is " + answerNumber);
                                // for each answer get the Description and value.
                                for (DataSnapshot QuestionAnswer : dataSnapshot.child(DBcategory).child(categoryString).child(questionData).child(answerNumber).getChildren()) {
                                    // Children: Description, value
                                    //debug
                                    Log.d("QuestionnaireAnswer", "The Answer string is " + questionData);
                                    String questionAnswer = QuestionAnswer.getKey();
                                    String answer = "";
                                    String value = "";
                                    // If the name is Description add it into the answer array.
                                    if (questionAnswer.equals("Description")) {
                                        //debug
                                        Log.d("QuestionnaireAnswer", "TestAnswer " + QuestionAnswer.getValue().toString());
                                        answer = QuestionAnswer.getValue().toString();
                                        answersList.add(answer);
                                    } else {
                                        // It will be value
                                        value = QuestionAnswer.getValue().toString();
                                        answerValueList.add(value);
                                    }
                                }
                                //Add the answers into a question List with answer array == List<Question>
                                //Question includes: String question;, List<Answers>Answers;, String Value;
                            }
                            // For every Question add the answer and Value into the answer/Value Lists
                            questionObj.setAnswers(answersList);
                            questionObj.setValues(answerValueList);
                        }
                    }
                    Log.d("Question", "The Question " + questionObj.getQuestion());
                    Log.d("Question", "The Question " + questionObj.getCategory());
                    //Resetting the category variable.
                    category = "";
                    // Adding the questions to the question List
                    questionsList.add(questionObj);
                }
                // Based on the QuestionList, Display the first question
                //Test if this will actually work
                questionObj = questionsList.get(questionNum - 1);
                tvQuestion.setText(String.format("Q%s. %s", questionNum, questionObj.getQuestion()));
                answersList = questionObj.getAnswers();
                answerValueList = questionObj.getValues();
                //Populate the radio buttons with the question descriptions.
                //rgQuestion is the radio group its being added to.
                int numAnswers = answersList.size();
                for (int i = 0; i < numAnswers ; i++) {
                    RadioButton rbanswer = new RadioButton(getActivity());
                    rbanswer.setId(View.generateViewId());
                    //rbanswer.setId("Q" + questionNum);
                    rbanswer.setText(answersList.get(i));
                    //debug
                    for (String answer : answersList) {
                        Log.d("AsnwerList", "Answer: " + answer);
                    }
                    rgQuestion.addView(rbanswer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnNext.setOnTouchListener(onClickNext);
        //returning the view.
        return view;
    }
    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will also keep a count of the number of points per category. Once the number for a specific
     * category reaches 3 (THIS WILL CHANGE) we can place them into the specified category.
     *
     */
    private View.OnTouchListener onClickNext = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
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
                return false;
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
                bundle.putInt("QuestionNumber",++questionNum);

                //bundle.putParcelableArrayList("QuestionList", (ArrayList<? extends Parcelable>) questionsList);
                //Passing the next question
                //TODO PASS THE ARRAY LIST INSTEAD OF THIS CRAP
                //Putting the object into the bundle
                bundle.putSerializable("QuestionList",(ArrayList<Question>) questionsList);

                Fragment fragment = new QuestionnaireQuestionsFragment2();
                fragment.setArguments(bundle);
                // create a FragmentManager
                FragmentManager fm = getFragmentManager();
                // create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                // replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit(); // save the changes

                //Going from Question1 to next Question fragment
//                NavHostFragment navHostFragment =
//                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//                NavController navController = navHostFragment.getNavController();




              /*  NavDirections action =
                        getActivity().SpecifyAmountFragmentDirections
                                .actionSpecifyAmountFragmentToConfirmationFragment();
                Navigation.findNavController(view).navigate(action);*/


                //NavDirections action = QuestionnaireQuestionFragmentDirections.actionFragmentQuestionnaireQuestionPageToQuestionnaireQuestionsFragment2(++questionNum);
                //Navigation.findNavController(view).navigate(action);
//                if (navController.currentDestination?.id == R.id.fragmentA) {
//                    Navigation.findNavController(view).navigate(R.id.action_fragment_questionnaire1_to_questionnaire2);
//                }

            }
            //The Value has been determined and

            //
            return false;
        }
    };

    /*
     *
     *
     */
    private View.OnTouchListener onClickBack = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };
}