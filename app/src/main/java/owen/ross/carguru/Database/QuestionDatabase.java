package owen.ross.carguru.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import owen.ross.carguru.Callbacks.QuestionFirebaseCallback;
import owen.ross.carguru.Models.Answer;
import owen.ross.carguru.Models.Question;

public class QuestionDatabase implements QuestionFirebaseCallback {


    //This references the Main Questions Branch
    private static DatabaseReference questionsReference = FirebaseDatabase.getInstance().getReference().child("Questions");

    // a method that will get all the questions of a specific category, and return them in a list
    public static void getQuestions(String questionCategory, QuestionFirebaseCallback firebaseCallback) {

        // declaring the arraylist that will hold all of the questions that are returned by the database
        ArrayList<Question> questions = new ArrayList<>();

        ValueEventListener listener = questionsReference.addValueEventListener(new ValueEventListener() {

            // declaring the arraylist that will hold all of the answers for a question
            ArrayList<Answer> answers = new ArrayList<>();
            // declaring the question object that will hold all of the information for a question
            Question question = new Question();
            // declaring the variables that will be used to store keys and values from the database
            String category = "";
            String questionAnswers = "";
            String questionData = "";
            String description = "";
            String value = "";
            String answer = "";
            String dbField = "";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot QuestionCategory : snapshot.child(questionCategory).getChildren()) {
                    //The values are the question numbers (Mq1, Mq2....)
                    String categoryString = QuestionCategory.getKey();
                    for (DataSnapshot questionInfo : snapshot.child(questionCategory).child(categoryString).getChildren()) {
                        questionData = questionInfo.getKey();
                        if (questionData.equals("Description")) {
                            // storing the description of the question to this variable
                            description = questionInfo.getValue().toString();
                            //debug
                            if(dbField.equals("How much do you care about air pollution?")){
                                Log.d("getQuestions", "Convertable Question is found " + description );
                            }
                            //debug
                            if(dbField.equals("Do you want to feel the wind in your hair when you drive down the road/do you want to feel the sun?")){
                                Log.d("getQuestions", "Convertable Question is found " + description );
                            }
                            // setting the question description to the question object
                            question.setQuestion(description);
                        } else if (questionData.equals("DBField")) {
                            // storing the question category in this variable
                            dbField = questionInfo.getValue().toString();
                            //debug
                            if(dbField.equals("Convertible")){
                                Log.d("getQuestions", "Convertable is found " + dbField );
                            }
                            // setting the question category to the question object
                            question.setDbField(dbField);
                            //Todo Get the DB field from the question and pass it
                        } else if (questionData.equals("Answers")) {
                            for (DataSnapshot QuestionAnswers : snapshot.child(questionCategory).child(categoryString).child(questionData).getChildren()) {
                                String answerNumber = QuestionAnswers.getKey();
                                for (DataSnapshot QuestionAnswer : snapshot.child(questionCategory).child(categoryString).child(questionData).child(answerNumber).getChildren()) {
                                    questionAnswers = QuestionAnswer.getKey();
                                    if (questionAnswers.equals("Description")) {
                                        // storing the answer description in this variable
                                        answer = QuestionAnswer.getValue().toString();
                                    } else {
                                        // storing the value of the answer into this variable
                                        value = QuestionAnswer.getValue().toString();
                                    }
                                }
                                // adding a new answer object to the answers arraylist with the description and value of the answer
                                answers.add(new Answer(answer, value));
                            }
                            // setting the arraylist of answers to the question object
                            question.setAnswers(answers);
                            // adding the question object to the arraylist of questions
                            //questions.add(question);
                            // setting the value of the question object to a new question object
                            //question = new Question();
                            // setting the value of the answers arraylist to a new arraylist
                            answers = new ArrayList<>();
                        }
                    }
                    questions.add(question);
                    question = new Question();
                }
                // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
                firebaseCallback.onCallback(questions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

    @Override
    public void onCallback(List<Question> list) {
    }


}
