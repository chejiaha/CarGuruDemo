package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FindSpecificModel extends AppCompatActivity {

    //List of carModel Objects so I can pass the one they choose to the users.
    ArrayList<CarModel> vehicleList;
    //Creating a List to hold all of the Names of the Make
    ArrayList<String> makeList;
    //Creating a List to hold all of the Names of the Models
    ArrayList<String> modelList;
    //Creating a List to hold all of the Names of the Years
    ArrayList<String> yearList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_specific_model_list);

    }
    /*
     * This Function is created to get all of the Cars Makes Models and years, and put them in the
     * respective ComboBox's (Spinners). This will populate the public arrays from the class
     * (1. makeList, 2.  modelList, 3. YearList)
     * The selected vehicle information will be passed to the next class.
     */

    public ArrayList<CarModel> getMakeModelYear() {
        TextView testingTV = findViewById(R.id.testingResultTV);
        //getting all of the items from the Vehicle portion of the database.
        DatabaseReference listOfVehicles = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        //List of carModel Objects so I can pass the one they choose to the users.
        vehicleList = new ArrayList<>();
        //Creating a List to hold all of the Names of the Make
        makeList = new ArrayList<>();
        //Creating a List to hold all of the Names of the Models
        modelList = new ArrayList<>();
        //Creating a List to hold all of the Names of the Years
        yearList = new ArrayList<>();

        listOfVehicles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String category ="";
                String make = "";
                String model = "";
                String year = "";

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    category = snapshot.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot aMake : dataSnapshot.child(category).getChildren()) {
                        //setting the make so we can iterate trhough them
                        make = aMake.getKey();
                        //Add the make to the list of makes
                        makeList.add(make);

                        //Go through each year and add the models for that year
                        for (DataSnapshot aYear : dataSnapshot.child(make).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            year = aYear.getKey();
                            //Add the year to the list of years
                            yearList.add(year);
                            //Go through each model of each make
                            for (DataSnapshot aModel : dataSnapshot.child(year).getChildren()) {
                                CarModel carModel = new CarModel();
                                //Adding the make to the carModel Object
                                carModel.Make = make;
                                //Adding the category to the carModel Object
                                carModel.Category = category;
                                //add the models name to the carModel Object
                                carModel.Model = aModel.getKey();
                                //Get the year and add it to the carModel Object
                                carModel.Year = year;
                                //Adding the carModel to the carModel List
                                vehicleList.add(carModel);
                                //Do I do one more loop to get the next information?
                                //Test both but first try just the first one.
                            }
                        }



                        //Getting the Category from the vehicle

                        //This for loop will go through each make and add the make

                        //Add the car to the Make array.

                        //If the vehicle is already in the array dont add it.

                        //Once we have the arrays, populate the spinner when the user clicks make, only show the models with that make.

                        //Once the user chooses models, only show the model that the user is going to make.
                    }

                }

                ArrayAdapter spinnerAdapter;

                //listview
                ListView lv = findViewById(R.id.resultListView);
                if (carType.equals("Commuter")) {
                    spinnerAdapter = new ArrayAdapter<>(SortingTop3.this, android.R.layout.simple_list_item_1, carCommuterString);
                } else {
                    spinnerAdapter = new ArrayAdapter<>(SortingTop3.this, android.R.layout.simple_list_item_1, carSportString);
                }
                // set the list object into spinner object
                lv.setAdapter(spinnerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return null;
    }










    /*
    *  This Event Handler is fired when the user clicks the Search button on the Find Specific Car Page.
    *  This function will Send the user to the specific cars page and will pass the Specific Vehicle
    *  Information received from the Database
     */
    public void onClickFindModel (View view){
        //Checking if user filled in Make,

        //Checking if user filled in Model

        //Checking if user filled in Year

        //If all are true send the user to the next page.
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SpecificModel.class);
        startActivity(intent);
    }
}
