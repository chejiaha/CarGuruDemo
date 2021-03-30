package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuestionnaireQuestionFragment extends Fragment {


    public QuestionnaireQuestionFragment() {
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
    //Creating my referenced to the database
    DatabaseReference dbRef;
    ValueEventListener listener;

    // Variables

    Question questionObj;
    // Total list of questions
    List<Question> questionsList;
    // Setting up one question
    List<String> answersList;
    List<String> answerValueList;
    String question = "";
    String category = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_questionnaire_question, container, false);

        // Set the View Items
        rgQuestion = view.findViewById(R.id.rgQuestions);
        btnBack = view.findViewById(R.id.BtnPreviousPage);
        btnNext = view.findViewById(R.id.BtnNextPage);

        // Get the Answers from the database put them in a list
        dbRef = FirebaseDatabase.getInstance().getReference().child("Questions");
        //Create radio buttons with id's that are
        listener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 *   This method is used to get the vehicle information from the database.
                 *   The Information is stored into the VehicleList. This list will contain the make,
                 *   model, year and category. This will be used in the onCreate method to look through
                 *   the information and populate the Spinners(ComboBoxes)
                 *
                 *   Will Populate:
                 *       ArrayList<String> makeList ()
                 *   Will Return:
                 *       ArrayList <carModel> vehicleList (**only make, model, year and category are filled out**)
                 */

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
                    for (DataSnapshot QuestionInfo : dataSnapshot.child(DBcategory).child(categoryString).getChildren()){
                        // Children: Answers, DBFiled, Description
                        // Depending on the question number get the respective question.
                        questionData = QuestionInfo.getKey();
                        // Get the Question String
                        if (categoryString.equals("Description")){
                            //Debug
                            Log.d("QuestionnaireQuestion", "The Question String: " + QuestionCategory.getValue().toString());
                            question = QuestionCategory.getValue().toString();
                            questionObj.setQuestion(question);
                        } else if (categoryString.equals("DBField")){
                            // Do nothing for now
                            category = QuestionCategory.getValue().toString();
                            questionObj.setQuestion(category);
                        }
                        else if (categoryString.equals("Answers")) {
                            //for each question in this section get the answers
                            for (DataSnapshot QuestionAnswers : dataSnapshot.child(DBcategory).child(categoryString).child(questionData).getChildren()) {
                                // Children: mqa11, mqa12....
                                String answerNumber = QuestionAnswers.getKey();
                                // for each answer get the Description and value.
                                for (DataSnapshot QuestionAnswer : dataSnapshot.child(DBcategory).child(categoryString).child(questionData).child(answerNumber).getChildren()) {
                                    // I got lazy please fix name...
                                    String questionAnswer = QuestionAnswer.getKey();
                                    String answer = "";
                                    String value = "";
                                    // If the name is Description add it into the answer array.
                                    if (questionAnswer.equals("Description")) {
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
                    // Adding the questions to the question List
                    questionsList.add(questionObj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //returning the view.
        return view;
    }
}