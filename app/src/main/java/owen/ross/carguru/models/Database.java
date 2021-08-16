package owen.ross.carguru.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database implements FirebaseCallback {

    private static DatabaseReference questionsReference = FirebaseDatabase.getInstance().getReference().child("Questions");



    // a method that will get all the questions of a specific category, and return them in a list
    public static void getQuestions(String questionCategory, FirebaseCallback firebaseCallback) {

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
            String answerNumber = "";
            String description = "";
            String value = "";
            String answer = "";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot QuestionCategory : snapshot.child(questionCategory).getChildren()) {
                    String categoryString = QuestionCategory.getKey();

                    Log.d("QuestionnaireCategory", "The Category string is " + categoryString);
                    for (DataSnapshot questionInfo : snapshot.child(questionCategory).child(categoryString).getChildren()) {

                        questionData = questionInfo.getKey();

                        Log.d("QuestionnaireInfo", "The INFO string is " + questionData);
                        if (questionData.equals("Description")) {
                            //Debug
                            Log.d("QuestionnaireQuestion", "The Question String: " + QuestionCategory.getValue().toString());
                            // storing the description of the question to this variable
                            description = questionInfo.getValue().toString();
                            // setting the question description to the question object
                            question.setQuestion(description);
                        } else if (questionData.equals("DBField")) {
                            // storing the question category in this variable
                            category = questionInfo.getValue().toString();
                            // setting the question category to the question object
                            question.setQuestion(category);
                        } else if (questionData.equals("Answers")) {
                            Log.d("TestAnswerLoop", "This should loop for answers: " + questionData);

                            for (DataSnapshot QuestionAnswers : snapshot.child(questionCategory).child(categoryString).child(questionData).getChildren()) {

                                Log.d("QuestionnaireAnswerQ", "The AnswerQuestion string is " + questionData);
                                String answerNumber = QuestionAnswers.getKey();
                                Log.d("QuestionnaireAnswerQ", "The AnswerNumber string is " + answerNumber);
                                for (DataSnapshot QuestionAnswer : snapshot.child(questionCategory).child(categoryString).child(questionData).child(answerNumber).getChildren()) {
                                    questionAnswers = QuestionAnswer.getKey();

                                    if (questionAnswers.equals("Description")) {
                                        //debug
                                        Log.d("QuestionnaireAnswer", "TestAnswer " + QuestionAnswer.getValue().toString());
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
                            questions.add(question);
                            // setting the value of the question object to a new question object
                            question = new Question();
                            // setting the value of the answers arraylist to a new arraylist
                            answers = new ArrayList<>();
                        }
                    }
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
