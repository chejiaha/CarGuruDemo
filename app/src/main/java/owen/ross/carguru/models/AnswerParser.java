package owen.ross.carguru.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

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
     * This method is used to get and return the string that we will search our database with.
     * To do this, it will check what the vehicle category that the user was placed into
     *
     *   questionCategory <String>: check what the vehicle category that the user was placed into
     *   Hashtable<String,String> questionAnswers: Will hold the { DBField, questionAnswer }
     *      DBField is what we are targeting for when the user answers the question.
     *              So if the question is do you care about gas? it DBField will be MPG
     *      questionAnswer being the answer that the user chose to the question
     *   ArrayList<String> hashtableKeys: This is used to get the size of items in the hashtable
     *
     * returns A List of Vehicles that the program finds.
     */
    public static ArrayList<String> CategoryAnswerParser(Hashtable<String,String> questionAnswers, ArrayList<String> hashtableKeys, String questionCategory){
        ArrayList<String> ListofCars = new ArrayList<>();
        for (int i = 0; i <= hashtableKeys.size(); i ++) {
            //Switch Going through all possibilities of answers given
            //debug
            Log.d("CommuterCategoryParser" , "COMMUTER ANSWER STRING IS!!" +  questionAnswers.get(hashtableKeys.get(i)));
            //User Answer
            String questionAnswer = questionAnswers.get(hashtableKeys.get(i));
            String DBField = hashtableKeys.get(i);

            //If the item is a commuter
            if (questionCategory.equals("CommuterQuestion")){
                switch(DBField) {
                    case "EV" :
                        // Figure out how to siphon this into results
                        break;
                    case "MPG":
                        // Figure out how to siphon this into results
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


        return ListofCars;
    }

}
