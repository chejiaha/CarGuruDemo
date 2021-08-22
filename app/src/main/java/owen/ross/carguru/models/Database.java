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
    private static DatabaseReference vehicleReference = FirebaseDatabase.getInstance().getReference().child("Vehicle");


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

    /*
     * This Function is created to get all of the Cars Makes Models and years, and put them in the
     * respective ComboBox's (Spinners). This will populate the public arrays from the class
     * (1. makeList, 2.  modelList, 3. YearList)
     * The selected vehicle information will be passed to the next class.
     */
    public static ArrayList<Car> getMakeModelYear() {
        //getting all of the items from the Vehicle portion of the database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        //List of car Objects so I can pass the one they choose to the users.

//        //Creating a List to hold all of the Names of the Make
        ArrayList<String> makeList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Models
        ArrayList<String> modelList = new ArrayList<>();

        //List of car Objects so I can pass the one they choose to the users.
        ArrayList<Car> vehicleList = new ArrayList<>();

        ValueEventListener listener = vehicleReference.addValueEventListener(new ValueEventListener() {
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
                 *       ArrayList <car> vehicleList (**only make, model, year and category are filled out**)
                 */
                String make = "";
                String model = "";
                String trim = "";
                String year = "";


                for (DataSnapshot ssMake : dataSnapshot.getChildren()) {
                    make = ssMake.getKey();
                    //Adding the Make to the Make List
                    makeList.add(make);
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot ssModel : dataSnapshot.child(make).getChildren()) {
                        //setting the make so we can iterate through them
                        model = ssModel.getKey();
                        //Go through each year and add the models for that year
                        for (DataSnapshot ssTrim : dataSnapshot.child(make).child(model).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            trim = ssTrim.getKey();
                            //debug
                            Log.d("testTrim", "The Trim is" + trim);
                            //Go through each model of each make
                            for (DataSnapshot ssYear : dataSnapshot.child(make).child(model).child(trim).getChildren()) {
                                Car car = new Car();
                                //Getting the model
                                year = ssYear.getKey();
                                //Adding the make to the car Object
                                car.setMake(make);
                                //add the models name to the car Object
                                car.setModel(model);
                                //add the models name to the car Object
                                car.setTrim(trim);
                                //Get the year and add it to the car Object
                                car.setYear(Integer.parseInt(year));
                                //Check if the model is already in the models array list
                                if (!modelList.contains(model)){
                                    //if it is not in the list add it
                                    modelList.add(model);
                                }
                                //Adding the car to the car List
                                vehicleList.add(car);
                            }
                        }
                    }
                }
                //Clearing the modelList
                modelList.clear();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return vehicleList;
    }

}
