package owen.ross.carguru.ui.questionnaire;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import owen.ross.carguru.R;
import owen.ross.carguru.models.Answer;
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
    View root;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        // storing the RadioGroup on teh questionnaire fragment in this variable
        rdoGroup = root.findViewById(R.id.rgQuestions);
        // storing the next and previous buttons in these variables
        Button nextBtn = root.findViewById(R.id.BtnNextPage);
        Button previousBtn = root.findViewById(R.id.BtnPreviousPage);

        //Checking if the category was passed to the Fragment (this will be done when they complete the initial questions.
        try {
            Bundle bundle = new Bundle();
            questionCategory = (getArguments().getString("highestCategory"));
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

        // adding all the categories and their values to the hashmap
        carCategories = new HashMap<>();
        carCategories.put("Commuter", 0);
        carCategories.put("Sport", 0);
        carCategories.put("Utility", 0);
        carCategories.put("Family", 0);
        carCategories.put("Beater", 0);
        carCategories.put("Luxury", 0);

        previousCategories = new ArrayList<>();

        // setting the listeners for the next and back buttons
        nextBtn.setOnClickListener(nextQuestion);
        previousBtn.setOnClickListener(previousQuestion);

        return root;
    }

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

    // method that is executed when the next button is pressed
    public View.OnClickListener nextQuestion = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //Makes sure the user selects an answer before displaying the next question
            if (rdoGroup.getCheckedRadioButtonId() == -1){
                //The button is not checked, ask the user to check a box
                Toast.makeText(getActivity(), "Please Choose an option", Toast.LENGTH_LONG).show();
            }else{

                // calling the addTally method to add the tally to the category that was chosen
                addTally(questions.get(listIt.nextIndex()).getAnswers());

                //This try Catch is not needed anymore... so we can remove it.
                listIt.next();
                // checking to see if the list has a next question (hasNext Not working Try catch workaround.)
                if (listIt.hasNext()) {

                    // displaying the question that is next in the list
                    displayQuestion(questions.get(listIt.nextIndex()));
                    // setting the value of the iterator to the next question in the list
                    listIt = questions.listIterator(listIt.nextIndex());
                    //Clear the check of the radio Group so the user has to choose again
                    rdoGroup.clearCheck();
                }
                else{
                    //
                    //Determine what Category the user belongs in
                    String highestCategory = getHighestCategoryTally(carCategories);
                    //debug
                    Log.d("NextPageTriggered", "\n Highest Category == " + highestCategory  );
                    //Send the user to the same fragment and pass the category to pull next.
                    Bundle bundle = new Bundle();
                    // Adding the category with the highest Tally based on the questions they answered
                    bundle.putString("highestCategory", highestCategory);
                    // Creating the same fragment just updating the Question section.
                    Fragment fragment = new QuestionnaireFragment();
                    // Send the users to the next page dependent on the highest score
                    switchFragments(fragment, R.id.nav_host_fragment, bundle);
                }
            }

        }
    };

    // method the is executed when the back button is pressed
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

    // method gets the value of the answer that was selected and adds the tally to the category that was selected
    public void addTally (ArrayList<Answer> answers) {
        // getting the value of the answer that the user selected
        String category = answers.get(rdoGroup.getCheckedRadioButtonId()).getValue();

        // debug
        Log.d("Car Categories Tallies", category);

        // checking to see if the category has a comma in it
        if(category.contains(",")) {
            // creating a list form the category string seperated by the commas
            List<String> categories = Arrays.asList(category.split("\\s*,\\s*"));
            // iterating through the list
            for (String carCategory: categories) {
                // removing the square brackets from the string
                carCategory = carCategory.replaceAll("\\[", "").replaceAll("\\]", "");
                // increasing the count of the category that was in the list
                carCategories.put(carCategory, carCategories.get(carCategory) + 1);
            }
        } else {
            // increasing the count of the category that was selected
            carCategories.put(category, carCategories.get(category) + 1);
        }

            // adds the category that was selected to this list
            previousCategories.add(category);

    }

    // method removes a tally form the previous answer that the user selected
    public void removeTally () {
        // getting the last category in the previousCategories list
        String category = previousCategories.get(previousCategories.size() - 1);

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

    }

    // method gets the highest number from all of the categories and passes the string to put the user into the next category questions section.
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