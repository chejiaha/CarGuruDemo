package owen.ross.carguru.models;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/*
 * This class is used to get the answers from the users and provide an output based on the category
 * of questions they are answering.
 *
 * This section will include:
 *      MainQuestion Answer Algorithm
 *      SportsQuestion Answer Algorithm
 *      CommuterQuestion Answer Algorithm
 *      UtilityQuestion Answer Algorithm
 *      FamilyQuestion Answer Algorithm
 *      LuxuryQuestion Answer Algorithm
 *      TruckQuestion Answer Algorithm
 *      BeaterQuestion Answer Algorithm
 *
 * Each Algorithm is different and will be based on the questions we are asking per category.
 * 
 *
 * */
public class AnswerParser {
    //This class is created to host the answer parsers for each category.

    /*
      * This method goes through the Hash map containing the categories and pulls the highest number
      *  from all of that the user's answers contained and returns the string to put the user into
      * the specific category questions section.
      *
      *
      * returns <String> highestString: The determined highest Category to place the user into a category
     */
    public static String MainCategoryAnswerParser(HashMap carCategories) {
        //Go through each category and get the number
        //debug looking at all of the categories
        Log.d("getHighestCategory",  carCategories.toString());

        //DEBUG
        String[] allCategories = {"Sports", "Commuter", "Luxury", "Beater", "Family", "Utility" };
        //String[] allCategories = {"Sports", "Commuter"};
        int highestValue =0;
        //Returns the category that was chosen
        String highestString = "";

        // Todo If there are more then one item that has the same value. (sports and commuter are equal, how do we choose one?)
        // Possible to add a list and then add a value for each item in the list then we can get the index of the doubles and compare the names,
        // and based on those names we can ask one last question to determine the category of the user

        //Going through each item in the hash map and getting the value out of it. and comparing it with eachother.
        for (String category : allCategories) {
            try {
                // Getting the value from each category.
                int categoryValue = (int) carCategories.get(category);
                Log.d("getHighestCategory", "Category " + category + " has " + categoryValue + "Votes");
                if (categoryValue > highestValue) {
                    //The largest tally will be the next questions we will use.
                    highestString = category;
                    highestValue = categoryValue;
                    Log.d("getHighestCategory", "Category " + category + " has The Highest Votes:" + highestString );
                }
            }catch (NullPointerException err){
                //If an item in the hash table is null, this will crash
                Log.d("getHighestCategory", "Category " + category + "Is Empty!");
                continue;
            }
        }

        //Todo
        //Check if there is more then one category that has the same value if so display another question
//        if (highestValue ){
//        }
        return highestString;
    }

    /*
     * This method is used to get and return the string that we will search our database with.
     * To do this, it will check what the vehicle category that the user was placed into
     *
     *   questionCategory <String>: check what the vehicle category that the user was placed into
     *   Hashtable<String,String> questionAnswers: Will hold the { DBField, questionAnswer }
     *      DBField is what we are targeting for when the user answers the question.
     *              So if the question is do you care about gas? it DBField will be MPG
     *      questionAnswer being the answer that the user chose to the question
     *
     * returns A List of Vehicles that the program finds.
     */
    public static ArrayList<String> CategoryAnswerParser(Hashtable<String,String> questionAnswers, String questionCategory){
        ArrayList<String> listofCars = new ArrayList<>();

        // Getting keySets of Hashtable and
        // storing it into Set (only one item of the same type allowed.
        Set<String> setOfKeys = questionAnswers.keySet();


        for (String DBField :  setOfKeys) {
            //Switch Going through all possibilities of answers given
            //debug
            Log.d("CommuterCategoryParser" , "COMMUTER ANSWER STRING IS!!" +  questionAnswers.get(DBField));
            //User Answer value (Example DBField: "questionAnswer" == EV: "Yes")
            String questionAnswer = questionAnswers.get(questionAnswers.get(DBField));

            //Commuter DBFields
            //If the MPG is lt25
            boolean MPGlt25 = false;
            boolean MPGgt35 = false;
            //
            boolean MPGmid = false;
            boolean Year = false;
            boolean Convertable = false;
            boolean EV = false;

            //Sports DBFields
            boolean Weight = false;
            boolean GroundClearance = false;
//          boolean Year = false;
//          boolean Convertable = false;
            boolean Cylinders = false;

            //If the item is a commuter
            if (questionCategory.equals("CommuterQuestion")){
                //Go through every car in the database and check if it checks all of the boxes.
                //Creating all of the variables for each answer.


                switch(DBField) {
                    case "EV" :
                        if (questionAnswer.equals("Yes")){
                            EV = true;
                        }
                        break;
                    case "MPG":
                        // Check if they care about fuel efficiency
                        if (questionAnswer.equals(">35")){

                        }

                        break;
                    case "Year":
                        // Figure out how to siphon this into results
                        break;
                    case "Convertible":
                        // Figure out how to siphon this into results
                        break;
                    default:
                        // Default will Log a message
                        Log.d("CommuterCategoryParser" , "This Field was not found in the list: Commuter" + DBField);
                        throw new StringIndexOutOfBoundsException("This Field was not found in the list: Commuter" + DBField);
                }
            }else if (questionCategory.equals("SportQuestion")){
                switch(DBField) {
                    case "Weight":
                        // Figure out how to siphon this into results
                        break;
                    case "GroundClearance":
                        // Figure out how to siphon this into results
                        break;
                    case "Year":
                        // Figure out how to siphon this into results
                        break;
                    case "Convertible":
                        // Figure out how to siphon this into results
                        break;
                    case "Cylinders":
                        // Figure out how to siphon this into results
                        break;
                    default:
                        // Default will Log a message
                        Log.d("CommuterCategoryParser", "This Field was not found in the list Sports: " + DBField);
                        throw new StringIndexOutOfBoundsException("This Field was not found in the list Sports: " + DBField);
                }
            }else if (questionCategory.equals("LuxuryQuestion")){

            }else if (questionCategory.equals("UtilityQuestion")){

            }else if (questionCategory.equals("BeaterQuestion")){

            }else if (questionCategory.equals("FamilyQuestion")){

            }else{
                    continue;
            }
        }
        return listofCars;
    }

}
