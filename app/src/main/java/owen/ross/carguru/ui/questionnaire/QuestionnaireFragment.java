package owen.ross.carguru.ui.questionnaire;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import owen.ross.carguru.R;
import owen.ross.carguru.models.Answer;
import owen.ross.carguru.models.AnswerParser;
import owen.ross.carguru.models.Database;
import owen.ross.carguru.models.FirebaseCallback;
import owen.ross.carguru.models.Question;

/*
 * This class is a major part of our Questionnaire Algorithm. It is this page which will pull the
 * questions that are available from the Database. The Type of questions that are pulled fromt he database
 *  is determined by the category that is passed from the previous fragment. If nothing is passed,
 * Then the default MainQuestions are displayed. Once it is found it will fetch the data from our
 * Firebase Database. Once that is fetched it will use a list Iterator called "listIt" that holds
 * the questions/answers in a list. The list will be iterated through until we reach the last question
 * in the main loop. Once we reach that, we pass the string category to the next Fragment which is a
 * New instance of the QuestionaireFragment.
 * //TODO Incorporate a way to get push the user into a vehicle section.
 *
 * This Class will receive:
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 * NOTE: This Category argument will only be set once the users find it in their heart
 *
 * This Class will Pass:
 *      "Category"          <String> : The category that the algorithm has decided to assign to the user.
 *
 * */
public class QuestionnaireFragment extends Fragment {

    // setting the global variables that will be used throughout the class
    View view;
    RadioGroup rdoGroup;
    LinkedList<Question> questions = new LinkedList<>();
    //ListIterator listIt = questions.listIterator();
    ListIterator listIt;
    // stores all of the car categories and their tally values
    HashMap<String, Integer> carCategories;
    // stores the values of the answers that the user chose in previous questions
    ArrayList<String> previousCategories;
    // The Category that we will pull the questions from the database.
    String questionCategory = "";
    // Progress bar
    ProgressBar simpleProgressBar;
    // Progress to be passed through the fragments to move the progress bar
    int progress;
    //Passing the Highest Category to the next page
    String highestCategory = "";


    //TODO (Once Approved) MOVE TO TOP
    //This variable will be appeneded to every time the user answers a specific category question.
    // This variable will contain all of the values that we will search for at the end of the questionnaire
    // Key == DBField Value == Answer Value
    Hashtable<String, String> queryDB = new Hashtable<String, String>();
    // This Array List will hold the names of the DBField that the user selects.
    ArrayList<String> previousSpecificCategoryAnswer = new ArrayList<>();

    /*
     * This Method will create the view.
     * We will get the arguments from the Previous class
     *   <int> Progress Bar Progress
     *   <String> Category that we are pulling the questions from
     * Then it will get the questions based on the category chosen
     *
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        // storing the RadioGroup on teh questionnaire fragment in this variable
        rdoGroup = view.findViewById(R.id.rgQuestions);
        // storing the next and previous buttons in these variables
        Button nextBtn = view.findViewById(R.id.BtnNextPage);
        Button previousBtn = view.findViewById(R.id.BtnPreviousPage);
        simpleProgressBar= view.findViewById(R.id.pbProgress);

        //Setting the progress bar up
        try {
            progress = getArguments().getInt("progress");
            // debug
            Log.d("onCreateView", "The progress was successfully passed: " + progress);

        }catch (NullPointerException err){
            // If its the first iteration make the progress 5
            progress = 50;
            Log.d("onCreateView", " The progress is not passed to this fragment ");
        }

        // TODO How Do I update the progress bar?
        simpleProgressBar.setProgress(progress);


        //Checking if the category was passed to the Fragment (this will be done when they complete the initial questions.
        try {
            questionCategory = (getArguments().getString("highestCategory"));
            if(questionCategory.isEmpty()){
                questionCategory = "MainQuestion";
                Log.d("onCreateView", " The Category was not passed: ");
            }
            //We need to append 'Question' To the end because that is how it is in the database...
            questionCategory = questionCategory + "Question";
            // debug
            Log.d("onCreateView", "The category was successfully passed: " + questionCategory);

        }catch (NullPointerException err){
            questionCategory = "MainQuestion";
            Log.d("onCreateView", " The Category was not passed: ");
        }

        // Calling the getQuestions method from the Database class to get the questions from the database
        Database.getQuestions(questionCategory, new FirebaseCallback() {
            /* calling the onCallback method in the FirebaseCallback interface, so the question
            won't be displayed until the data is returned */
            @Override
            public void onCallback(List<Question> list) {
                // checking if the questions linkedlist is already populated with questions
                if (questions.isEmpty()) {
                    // the questions returned from the database will be added to the list if the list is empty
                    questions.addAll(list);
                }
                // display the first question from the list of questions
                displayQuestion(questions.getFirst());
                listIt = questions.listIterator();
            }
        });

        //Setup for MainQuestions
        // adding all the categories and their values to the hashmap
        carCategories = new HashMap<>();
        carCategories.put("Commuter", 0);
        carCategories.put("Sport", 0);
        carCategories.put("Utility", 0);
        carCategories.put("Family", 0);
        carCategories.put("Beater", 0);
        carCategories.put("Luxury", 0);

        previousCategories = new ArrayList<>();
        //Setup for CommuterQuestions

        // setting the listeners for the next and back buttons
        nextBtn.setOnClickListener(nextQuestion);
        previousBtn.setOnClickListener(previousQuestion);

        return view;
    }

    /*
     * This Method is used to populates the question based on the Question Passed to the method.
     */
    // will display the question, and the answers of the question that is passed into the method
    public void displayQuestion(Question question) {

        // removes all of the radio buttons that were previously added to the radio group
        rdoGroup.removeAllViews();

        int id = 0;
        // iterating through all of the answers of the question
        for (Answer answer: question.getAnswers()) {
            // creating a new radio button
            RadioButton rbAnswer = new RadioButton(getActivity());

            // setting the ID of the new radio button
            rbAnswer.setId(id);
            // setting the text of the radio button
            rbAnswer.setText(answer.getDescription());
            // adding the radio button to the radio group
            rdoGroup.addView(rbAnswer);
            id++;
        }
    }

    /*
     * This Method is activated when the user clicks the next question button.
     * This question will get the checked checkboxes value and depending on if its the Main Question
     * Category or a Specific Question Category will act accordingly
     *
     * Main Question
     *  Will calculate the highest category and send it to the next page
     *
     * Specific Category Question
     *  Will use the Category Answer Parser in the AnswerParser Class to parse the answers.
     */
    public View.OnClickListener nextQuestion = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //Makes sure the user selects an answer before displaying the next question
            if (rdoGroup.getCheckedRadioButtonId() == -1){
                //The button is not checked, ask the user to check a box
                Toast.makeText(getActivity(), "Please Choose an option", Toast.LENGTH_LONG).show();
            }else{
               // If there is a checked value then check if there is a next question
                //Add the Category or Specific Categories answer to the answer list.
                addTally(questions.get(listIt.nextIndex()).getAnswers());
                //This try Catch is not needed anymore... so we can remove it.
                listIt.next();
                // checking to see if the list has a next question
                if (listIt.hasNext()) {
                    // displaying the question that is next in the list
                    displayQuestion(questions.get(listIt.nextIndex()));
                    // setting the value of the iterator to the next question in the list
                    listIt = questions.listIterator(listIt.nextIndex());
                    //Clear the check of the radio Group so the user has to choose again
                    rdoGroup.clearCheck();
                }
                else{
                    //If there are no questions left, check if its the first round or second round of questions.
                    if (questionCategory.equals("MainQuestion")){
                        //Determine what Category the user belongs in
                        highestCategory = AnswerParser.MainCategoryAnswerParser(carCategories);
                        //debug
                        Log.d("NextPageTriggered", "\n Highest Category == " + highestCategory  );
                        //Send the user to the same fragment and pass the category to pull next.
                        Bundle bundle = new Bundle();
                        // Adding the category with the highest Tally based on the questions they answered
                        bundle.putString("highestCategory", highestCategory);
                        // Adding the progression to the bar
                        progress = simpleProgressBar.getProgress() + 5;
                        bundle.putInt("progress", progress);
                        // Creating the same fragment just updating the Question section.
                        Fragment fragment = new QuestionnaireFragment();
                        // Send the users to the next page dependent on the highest score
                        switchFragments(fragment, R.id.nav_host_fragment, bundle);
                    }else{
                        // If its on the second round of questions, use the other parser
                        Database.CategoryAnswerParser(queryDB, questionCategory);
                    }
                }
            }
        }
    };

    /*
     *  The method the is executed when the back button is pressed.
     * This method will remove the categories from the last question and display the previous
     * question
     */
    public View.OnClickListener previousQuestion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeTally();
            // checking to see if the list has a previous question
            if (listIt.hasPrevious()) {
                // displaying the question that is previous in the list
                displayQuestion(questions.get(listIt.previousIndex()));
                // setting the value of the iterator to the previous question in the list
                listIt = questions.listIterator(listIt.previousIndex());
        }
    }
    };



    /*
     *  This method gets the value of the answer that was selected and adds the tally to the category
     *  that was selected.
     *
     *  The Add Tally Main Question method will add the tally for the Main Category Questions based
     *  on the categories it Parses from the database Category values for the answers the users chose.
     *
     *  The Add Tally Specific Category Question portion will get the answer and the DBField for that
     *  category.
     *
     * <String Array> previousSpecificCategoryAnswer: adds all of the Keys(Specific Question categories//"DBField")
     *                                                to a String Array list which will be used to see
     *                                                how many answers the user has completed
     * <HashMap String, Integer> carCategories: Is used to add the Category Answer and pair it with the interfaces.
     *
     * Modifies carCategories: by adding one to the category score
     * Returns none
     */
    public void addTally (ArrayList<Answer> answers) {

        // getting the value of the answer that the user selected
        //used to be  String category = answers.get(rdoGroup.getCheckedRadioButtonId()).getValue();
        String questionAnswer = answers.get(rdoGroup.getCheckedRadioButtonId()).getValue();
        //If we are in the first iteration, add the categories and decide the category for users
        if (questionCategory.equals("MainQuestion")){
            // debug
            Log.d("addTally" , "CAR ANSWERS!!!!!!" + answers.toString());
            Log.d( "addTally" ,"Car Categories Tallies" + questionAnswer);

            // checking to see if there is more then one category
            if(questionAnswer.contains(",")) {
                // creating a list form the category string seperated by the commas
                List<String> categories = Arrays.asList(questionAnswer.split("\\s*,\\s*"));
                // iterating through the list
                for (String carCategory: categories) {
                    // removing the square brackets from the string
                    carCategory = carCategory.replaceAll("\\[", "").replaceAll("\\]", "");
                    // increasing the count of the category that was in the list
                    carCategories.put(carCategory, carCategories.get(carCategory) + 1);
                }
                //Otherwise Add the single string category
            } else {
                // increasing the count of the category that was selected
                carCategories.put(questionAnswer, carCategories.get(questionAnswer) + 1);
            }
            // adds the category that was selected to this list
            previousCategories.add(questionAnswer);
        }else{
            //If the user is already put into a category, add the tally to a different hashTable.
            // Get the Users Answer
            String commuterAnswer = answers.get(rdoGroup.getCheckedRadioButtonId()).getValue();
            //TODO FIND OUT HOW TO GET DBFIELD!!!
            //The first one is empty so skip it
            listIt.next();
            //Get the Category/Type of car that is being decided by the question
            String DBField = questions.get(listIt.nextIndex()).getDbField();
            //Adding the Name so we can delete it if the user goes back a question
            //DEBUG TODO TAKE THIS OUT ONCE YOU FIND DBField
            if (DBField == null){
                DBField = "asd";
            }
            previousSpecificCategoryAnswer.add(DBField);

            //debug
            Log.d("addTally", "DBField " + DBField);
            Log.d("addTally", "Question Answer " + commuterAnswer);

            //This variable will contain the DB field once we are put into a category
            // Add the DBField and Answer to the question to be queried after the questionnaire.
            queryDB.put(DBField, questionAnswer);

        }
    }

    //
    /*
     *  This method removes a tally form the previous answer that the user selected and removes the tally to the category
     *  that was selected.
     *
     *  The Remove Tally Main Question method will remove the tally for the Main Category Questions based
     *  on the categories it Parses from the database Category values for the answers the users chose.
     *
     *  The Remove Tally Specific Category Question portion will get the answer and the DBField for that
     *  category.
     *
     * <String Array> previousSpecificCategoryAnswer: adds all of the Keys(Specific Question categories//"DBField")
     *                                                to a String Array list which will be used to see
     *                                                how many answers the user has completed
     * <HashMap String, Integer> carCategories: Is used to add the Category Answer and pair it with the interfaces.
     *
     *
     *
     */
    public void removeTally () {
        // getting the last category in the previousCategories list
        String category = previousCategories.get(previousCategories.size() - 1);

        //If we are in the first iteration, add the categories and decide the category for users
        if (questionCategory.equals("MainQuestion")){
            // checking to see if the category string has a comma in it
            if(category.contains(",")) {
                // creating a list form the category string seperated by the commas
                List<String> categories = Arrays.asList(category.split("\\s*,\\s*"));
                // iterating through the list
                for (String carCategory: categories) {
                    // removing the square brackets from the string
                    carCategory = carCategory.replaceAll("\\[", "").replaceAll("\\]", "");
                    // decreasing the count of the category that was in the list
                    carCategories.put(carCategory, carCategories.get(carCategory) - 1);
                }
            } else {
                // decreasing the count of the category that was selected
                carCategories.put(category, carCategories.get(category) - 1);
            }
            // removing the last item from the array of previous categories
            previousCategories.remove(previousCategories.size() - 1);
        }else{
            //If the user is already put into a category, add the tally to a different hashTable.
            // Get the last answer from the user
            int sizeOfQuestionList = previousSpecificCategoryAnswer.size();
            String DBField = previousSpecificCategoryAnswer.get(sizeOfQuestionList);

            //debug
            Log.d("removeTally", "Removing DBField from Hashmap" + DBField);

            //Send the user to the same fragment and pass the category to pull next.
            Bundle bundle = new Bundle();
            // Adding the category with the highest Tally based on the questions they answered
            bundle.putString("highestCategory", highestCategory);
            // Adding the progression to the bar
            progress = simpleProgressBar.getProgress() - 5;
            bundle.putInt("progress", progress);

            //This variable will contain the DB field once we are put into a category TODO ( DBField NEEDS TO BE RESET AT THE END OF THE LOOP)
            // Remove the DBField and Answer to the question to be queried after the questionnaire.
            queryDB.remove(DBField);
        }
    }

    /*
     method gets the highest number from all of the categories and passes the string to put the user into the next category questions section.
     */
    public String getHighestCategoryTally (HashMap carCategories) {
        //Go through each category and get the number 
        //debug looking at all of the categories
        Log.d("getHighestCategory",  carCategories.toString());

        //DEBUG
        String[] allCategories = {"Sports", "Commuter", "Luxury", "Beater", "Family", "Utility" };
        //String[] allCategories = {"Sports", "Commuter"};
        int highestValue =0;
        String highestString = "";
        //Going through each item in the hash map and getting the value out of it. and comparing it with eachother.
        for (String category : allCategories) {
            try {
                int categoryValue = (int) carCategories.get(category);
                Log.d("getHighestCategory", "Category " + category + " has " + categoryValue + "Votes");
                if (categoryValue > highestValue) {
                    //The largest tally will be the next questions we will use.
                    highestString = category;
                    highestValue = categoryValue;
                    Log.d("getHighestCategory", "Category " + category + " has The Highest Votes:" + highestString );
                }
            }catch (NullPointerException err){
                Log.d("getHighestCategory", "Category " + category + "Is Empty!");
                continue;
            }
        }
        return highestString;
    }

    /*
     * This method is created to send a fragment from one fragment to the other.
     *
     * Takes in:
     *  Fragment FragmentName : The Fragment That you want to instantiate.
     *  Bundle bundle         : The bundle with all of the objects already populated.
     *  int IdOfNavHostUI     : This is used to specify which view you want to replace.
     *          BY default in our application set to the id of the "navigation_host_fragment" ID
     *  NOTE:
     */
    public void switchFragments (Fragment fragmentName,  int idOfNavHostUI, Bundle bundle){
        //If the bundle is not empty add the argument
        if (bundle.isEmpty() == false){
            fragmentName.setArguments(bundle);
        }
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




}