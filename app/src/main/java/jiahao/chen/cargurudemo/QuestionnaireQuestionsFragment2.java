package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavType;

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
import java.util.List;

/*
 * This class is the second part to the Questionnaire algorithm. It is this page which will display
 * the next question from the database. This Fragment and Fragment3 are used to pass the question data
 * from page to page. This method will get the questions, questionNum, category and categorypoints
 * from the arguments. It will then populate the questions. If the algorithm determines a category it
 * will send it to the First QuestionnaireQuestionFragment so we can pull the next set of questions
 * from the database.

 *
 * This Class will receive:
 *      "questionsList"     ArrayList<Question> : The Array of Questions Based on the Category Passed.
 *      "questionNum"       <int> : The Question Number that will be used to get the question number from the array.
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 *      "CategoryPoints"    ArrayList<int> : The points for each of the categories.
 *       NOTE: The "Category" argument will only be set once the users find it in their heart
 *
 * This Class will Pass:
 *      "questionsList"     ArrayList<Question> : The Array of Questions Based on the Category Passed.
 *      "questionNum"       <int> : The Question Number that will be used to get the question number from the array.
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 *      "CategoryPoints"    ArrayList<int> : The points for each of the categories.
 *      TODO "stringQuestionNum"       <int> : The Question Number that will displayed to the user. Same for recive.
 *
 * */
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
    ArrayList<Question> questionsList;
    // Setting up one question
    ArrayList<String> answersList;
    ArrayList<String> answerValueList;
    int[] categoryPoints;
    String question = "";
    String category = "";
    String passedCategory = "";
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
        view = inflater.inflate(R.layout.fragment_questionnaire_questions2, container, false);

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
        // Getting the category points
        categoryPoints = getArguments().getIntArray("CategoryPoints");

        //Getting the Category from the arguments.
        passedCategory = getArguments().getString("Category");

        //Placing the Question into a Question object
        //If there are no more questions push it to the list of vehicles
        try{
            questionObj = questionsList.get(questionNum);
        }catch (IndexOutOfBoundsException err){
            Fragment fragment = new QuestionnnaireListFragment();
            SwitchToNextQuestion(fragment, questionNum, categoryPoints, questionsList, passedCategory);
           /* Bundle bundle1 = new Bundle();
            Passing the category that was determined
            bundle.putString("Category", passedCategory);
            fragment.setArguments(bundle1);
            //TODO This can also Be a function.
            //TODO functionName(Fragment FragmentName, Bundle bundle, int IdOfNavHostUI**Optional)
            // Adding the arguments into the bundle

            // create a FragmentManager
            FragmentManager fm = getFragmentManager();
            // create a FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            // replace the FrameLayout with new Fragment
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.commit(); // save the changes*/
            //TODO pass all the values and go to the next category pages.
        }

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
                rbanswer.setText(answersList.get(i));
                rgQuestion.addView(rbanswer);
            }
        }
        //Setup the Onclick Listener
        btnNext.setOnClickListener(onClickNext);
        // Inflate the layout for this fragment
        return view;
    }


    // The number that will represent how many questions until category is determined
    int FALLTHROUGH_NUM = 3;
    // The boolean value that will change if the category is determined
    // TODO implement this to work as a global variable.
    boolean categoryDetermined = false;
    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will also keep a count of the number of points per category. Once the number for a specific
     * category reaches 3 (Currently known as FALLTHROUGH_NUM = 3(THIS WILL CHANGE)) we can place them into the specified category.
     *
     */
    public View.OnClickListener  onClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // If the user clicks next, check if an option was checked
            int radioId;
            try{
                radioId = rgQuestion.getCheckedRadioButtonId();
            }catch (NullPointerException err){
                radioId = 0;
                Toast.makeText(getActivity(), "Please Choose an option", Toast.LENGTH_LONG).show();
                return;
            }

            // Find the correct answer
            RadioButton rbChosenAnswer = view.findViewById(radioId);
            // Variable to hold the answer index. so we can match the value index.
            int answerIndex = 0;
            // Get the answer String
            String answerDescription = rbChosenAnswer.getText().toString();
            // Variable used to hold the answer Values
            ArrayList<String> valueString = questionObj.getValues();
            //Compare the answer string with our answers to find the values to add.
            for (int i = 0; i <= valueString.size(); i++){
                // Compare the string values to find the chosen answer
                if (questionObj.getAnswers().get(i).equals(answerDescription)){
                    //If the answers are the same then return the index.
                    answerIndex = i;
                    break;
                }
            }

            // Variable used to check if its a array.
            String[] valueArray = null;
            // Go through the values list and compare strings Values ["CategoryName", "CategoryName", "CategoryName"]
            //TODO Change the items in the database to always be a List! (Array) whatever you wanna call it.

            // Can be a string or a list in a string ("[Category,Sport, ...]")
            String value = valueString.get(answerIndex);

            //Checking if the value is actually a array
            if(value.contains("[")){
                //If its a String Array, convert it to a Arra
                value = value.replace("[","");
                value = value.replace("]","");
                //Then Split it by commas to create the array.
                valueArray = value.split(",");
            }

            //If its a list go through each item, otherwise its a string and no iteration needed.
            // Go through the values list and compare strings Values ["CategoryName", "CategoryName", "CategoryName"]
            if(valueArray != null){
                /*
                 * This switch statement will add the category point directly to the array.
                 *  int[] categoryPointsArray= {commuterCategory, sportsCategory, beaterCategory, utilityCategory, familyCategory, luxuryCategory};
                 */
                // For each item in the list add the value.
                for (String arrayValue : valueArray){
                    AddPointsToPointsArray(arrayValue.trim());
                }
            }else{

                //debug
                Log.d("CHECKVALUE", "THE Value of the string is " + value.toString());
                AddPointsToPointsArray(value);
            }

            //debug
            Log.d("COMMUTER", "The COMUTTER Points " + categoryPoints[0]);
            Log.d("SPORTS", "The SPORTS Points " +  categoryPoints[1]);

            //TODO this can be moved outside the for loop.
            //TODO Check if this works.
            CheckIfCategoryDetermined();


            // If the category was determined push onto the next questions
            if (categoryDetermined){
                Fragment fragment = new QuestionnaireQuestionFragment();
                SwitchToNextQuestion(fragment, questionNum, categoryPoints, questionsList, passedCategory);
            }else{
                /*
                   LUKA NOTES
                   Continue to the next question in the same category.
                   Pass the questionList to the next page to be displayed by the user
                   Pass the questionNum
                   TODO Pass the progress bar
                */
                Fragment fragment = new QuestionnaireQuestionFragment3();
                SwitchToNextQuestion(fragment, questionNum, categoryPoints, questionsList, passedCategory);
        }
    }}; //end of OnTouch

    /*
     * This method is created to check if the user provided an answer and will move them to the next question.
     * This method will subtract one from the count of points based on the category they chose last.
     * This value is passed as a string so it knows what points to take off.
     *
     * Passes:
     *  ArrayList<Questions> : The Array List of Questions
     *  QuestionNumber <String> : The question Number subtracted by 1
     */
    private View.OnTouchListener onClickBack = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //TODO Get the last category that was added and subtract it
            //Adding the arguments into the bundle
            Bundle bundle = new Bundle();
            bundle.putInt("QuestionNumber", --questionNum);
            bundle.putSerializable("QuestionList",(ArrayList<Question>) questionsList);
            //TODO Subtract the next one
            categoryPoints = getArguments().getIntArray("CategoryPoints");
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

    /*
     * This method is created to check if the Category was Determined
     *
     * This function will loop through the categoryPoints Array and check if
     * any of the category's are above the threshold.
     * TODO (Redundecy) If there are two that are tied, ask the server for more questions.
     *
     */
    public boolean CheckIfCategoryDetermined(){
        //TODO this can be moved outside the for loop.
        if (categoryPoints[0] >= FALLTHROUGH_NUM){
            categoryDetermined = true;
            passedCategory = "Commuter";
        }else if (categoryPoints[1] >= FALLTHROUGH_NUM){
            passedCategory = "Sports";
            categoryDetermined = true;
        }else if (categoryPoints[2] >= FALLTHROUGH_NUM){
            passedCategory = "Beater";
            categoryDetermined = true;
        }else if (categoryPoints[3] >= FALLTHROUGH_NUM){
            passedCategory = "Utility";
            categoryDetermined = true;
        }else if (categoryPoints[4] >= FALLTHROUGH_NUM){
            passedCategory = "Family";
            categoryDetermined = true;
        }else if (categoryPoints[5] >= FALLTHROUGH_NUM){
            passedCategory = "Luxury";
            categoryDetermined = true;
        }
        //Return if the category is determined
        return categoryDetermined;
    }

    /*
     * This method is created to add the points to the respective category.
     *
     * This function will take the values from the answer and check what the string values are.
     * any of the category's are above the threshold.
     *
     * This function will modify the categoryPoints Array
     *
     */
    public void AddPointsToPointsArray (String arrayValue) {

        switch (arrayValue.trim()){
            case "Commuter":
                categoryPoints[0] = categoryPoints[0] + 1;
                Log.d("COMMUTER", "Determained its a COMUTTER" + categoryPoints[0]);
                break;
            case "Sport":
                categoryPoints[1] = categoryPoints[1] + 1;
                Log.d("SPORTS", "Determained its a SPORTS" +  categoryPoints[1]);
                break;
            case "Beater":
                categoryPoints[2] = categoryPoints[2] + 1;
                break;
            case "Utility":
                categoryPoints[3] = categoryPoints[3] + 1;
                break;
            case "Family":
                categoryPoints[4] = categoryPoints[4] + 1;
                break;
            case "Luxury":
                categoryPoints[5] = categoryPoints[5] + 1;
                break;
        }
    }

}