package owen.ross.carguru.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
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

    // method gets the highest number from all of the categories and passes the string to put the user into the next category questions section.
    public String MainCategoryAnswerParser(HashMap carCategories) {
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

    public String CommuterCategoryAnswerParser(HashMap commuterAnswers){
        String carCategory = "";



        return carCategory;
    }





}
