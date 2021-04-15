package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.Index;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * This class is a major part of our Questionnaire Algorithm. It is this page which will pull the
 * questions that are available from the Database. The Type of question is determined by the category
 * that is passed to the method. If the method does not get anything from the arguments, then the
 * algorithm is still determining the category. If it passes "Category" to the algorithm, then we can
 * pull all of the category questions based on the value. Once it is found it will populate the first
 * question and then pass the list of questions to the next fragment.
 * TODO Find a way to use the this fragment, and one more to pass data between the files AND to NOT
 *  pull data from the database every time this fragment runs. This will improve efficiency.
 *
 * This Class will receive:
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 * NOTE: This Category argument will only be set once the users find it in their heart
 *
 * This Class will Pass:
 *      "questionsList"     ArrayList<Question> : The Array of Questions Based on the Category Passed.
 *      "questionNum"       <int> : The Question Number that will be used to get the question number from the array.
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 *      "CategoryPoints"    ArrayList<int> : The points for each of the categories.
 *      TODO "stringQuestionNum"       <int> : The Question Number that will displayed to the user.
 *
 * */
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
    //<TODO Make Category only one variable, and fix the figure out where it gets reset and why.>
    String question = "";
    String category = "";
    String passedCategory;

    // Int that will be the question Number
    int questionNum = 1;
    //<TODO Implement Question Number>
    // Int that will display the number of the question array that we will use.
    int arrayQuestionNum = 0;

    /*
     * This Method will create the view. It will assign the View Objects and get the passed arguments
     * Once it gets everything, it will Pull the data from the database based on if the Category is set.
     *
     */
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
        tvQuestionChoice = view.findViewById(R.id.tvQuestionChoice);
        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();
        answerValueList = new ArrayList<>();
        questionObj = new Question();
        // If the item is passed to this fragment, it means that it has already discovered a category.
        // This field will be checked in the onClick Method if it is null or not.
        try{
            passedCategory = getArguments().getString("Category");
        }catch (NullPointerException err){
            passedCategory = "";
        }

        // Get the Answers from the database put them in a list
        dbRef = FirebaseDatabase.getInstance().getReference().child("Questions");
        // Create a listener to get the items and store them from the database
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
             *       Will Populate
             *          int questionNum                     : Question Number
             *          String question                     : Question Description
             *          ArrayList<String> answersList       : Question Answers
             *          ArrayList<String> answerValueList   : Answer Values
             *
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Used when the user is placed in the category to go into the db to find the questions
                String DBcategory = "";
                String categoryString;
                String questionData;
                // NOTE : If there is no category chosen, The Category = MainQuestions.
                // Finding the Category
                if (passedCategory.equals("") || category == null){
                    DBcategory = "MainQuestion";
                }else if (passedCategory.equals("Commuter")){
                    DBcategory = "CommuterQuestion";
                }else if (passedCategory.equals("Sports")){
                    DBcategory = "SportQuestion";
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
                    //debug
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
        btnNext.setOnClickListener(onClickNext);
        //returning the view.
        return view;
    }
    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will also keep a count of the number of points per category. Once the number for a specific
     * category reaches 3 (THIS WILL CHANGE) we can place them into the specified category.
     * To determine this number we are checking what values the database returns for the question,
     * and are adding them to each categories totals.
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
                        //debug
                        Log.d("COMMUTER", "Determained its a COMUTTER" + commuterCategory);
                        break;
                    case "Sports":
                        sportsCategory++;
                        //debug
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
            //TESTING
            if (passedCategory != ""){
                 /*
                   LUKA NOTES
                   Continue to the next question in the same category.
                   Pass the questionList to the next page to be displayed by the user
                   Pass the questionNum
                   TODO Pass the progress bar
                */
                // Adding the arguments into the bundle
                Bundle bundle = new Bundle();
                // Adding the question number that will specify the question from the list
                bundle.putInt("QuestionNumber", questionNum);
                // Adding all of the category points into an array
                int[] categoryPointsArray= {commuterCategory, sportsCategory, beaterCategory, utilityCategory, familyCategory, luxuryCategory};
                // Passing the Counts of all of the categories
                bundle.putIntArray("CategoryPoints", categoryPointsArray);
                // Passing the List of Questions to the next value.
                bundle.putSerializable("QuestionList",(ArrayList<Question>) questionsList);
                //Passing the category that was determined
                bundle.putString("Category", passedCategory);

                // Replacing the current fragment with the next Question.
                Fragment fragment = new QuestionnaireQuestionsFragment2();
                fragment.setArguments(bundle);
                // Create a FragmentManager
                FragmentManager fm = getFragmentManager();
                // Create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                // Replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit(); // save the changes
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
                // Adding the arguments into the bundle
                Bundle bundle = new Bundle();
                // Adding the question number that will specify the question from the list
                bundle.putInt("QuestionNumber", questionNum);
                // Adding all of the category points into an array
                int[] categoryPointsArray= {commuterCategory, sportsCategory, beaterCategory, utilityCategory, familyCategory, luxuryCategory};
                // Passing the Counts of all of the categories
                bundle.putIntArray("CategoryPoints", categoryPointsArray);
                // Passing the List of Questions to the next value.
                bundle.putSerializable("QuestionList",(ArrayList<Question>) questionsList);
                //Passing the category that was determined
                bundle.putString("Category", passedCategory);


                // Replacing the current fragment with the next Question.
                Fragment fragment = new QuestionnaireQuestionsFragment2();
                fragment.setArguments(bundle);
                // Create a FragmentManager
                FragmentManager fm = getFragmentManager();
                // Create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                // Replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit(); // save the changes
            }
            //The Value has been determined and
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


    /*
     * This method is created to send a fragment from one fragment to the other.
     *
     * Takes in:
     *  Fragment FragmentName : The name of the Fragment you want to change to
     *  Bundle bundle         : The bundle with all of the objects already populated.
     *  int IdOfNavHostUI     : This is used to specify which view you want to replace.
     *          BY default in our application set to the id of the "navigation_host_fragment" ID
     *  NOTE:
     */
    public void SwitchFragments (Fragment fragmentName,  int idOfNavHostUI){
        int hostNav;
        // If idOfNavHostUI is null, then set it to the navigation_host_fragment
        idOfNavHostUI = idOfNavHostUI != 0 ? idOfNavHostUI : R.id.nav_host_fragment;
        // Create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
        fragmentTransaction.replace(idOfNavHostUI, fragmentName);
        fragmentTransaction.commit(); // save the changes
    }
    /*
     * This method is created to send a fragment from one fragment to the other.
     * This method will pass the below arguments to the next method.
     *
     * Takes in :
     *          Fragment FragmentName               : The name of the Fragment you want to change to
     *          int questionNum                     : Question Number
     *          String question                     : Question Description
     *          ArrayList<String> answersList       : Question Answers
     *          ArrayList<String> answerValueList   : Answer Values
     *          String category                     : The Category the question belongs to
     *
     */
    public void SwitchToNextQuestion(Fragment fragment, int questionNum, int[] categoryPoints, ArrayList<Question> questionList, String category){
        //Fragment fragment = new QuestionnaireQuestionFragment();

        // If the category is empty set it to an empty string.
        category = category != null ? category : "";

        // Adding the arguments into the bundle
        Bundle bundle = new Bundle();
        // Adding the question number that will specify the question from the list
        bundle.putInt("QuestionNumber", ++questionNum);
        // Passing the Counts of all of the categories
        bundle.putIntArray("CategoryPoints", categoryPoints);
        // Passing the List of Questions to the next value.
        bundle.putSerializable("QuestionList",(ArrayList<Question>) questionList);
        //Passing the category that was determined
        bundle.putString("Category", category);
        // Setting the Arguments for the Fragment by passing the bundle
        fragment.setArguments(bundle);
        // Switch from one Fragment to the other. Note: the second variable can be 0 and it should work
        SwitchFragments(fragment,R.id.nav_host_fragment);
    }



}