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
        String[] allCategories = {"Sport", "Commuter", "Luxury", "Beater", "Family", "Utility" };
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


}
