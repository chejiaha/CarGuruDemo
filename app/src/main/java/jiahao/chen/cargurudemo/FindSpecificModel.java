package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
    //Creating the context
    Context context;
    //List of carModel Objects so I can pass the one they choose to the users.
    ArrayList<CarModel> vehicleList;
    //Creating a List to hold all of the Names of the Make
    ArrayList<String> makeList;
    //Creating a List to hold all of the Names of the Models
    ArrayList<String> modelList;
    //Creating a List to hold all of the Names of the Years
    ArrayList<String> yearList;
    //Getting the Spinner(Combobox) Objects
    Spinner makeSpinner;
    Spinner modelSpinner;
    Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_specific_model_list);
        //Setting the context
        context = getApplicationContext();
        //vehicleList = getMakeModelYear();
        //getting all of the items from the Vehicle portion of the database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        //List of carModel Objects so I can pass the one they choose to the users.
        vehicleList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Make
        ArrayList<String> makeList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Models
        ArrayList<String> modelList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Years
//        yearList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
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
                String category ="";
                String make = "";
                String model = "";
                String year = "";

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    category = snapshot.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot aMake : dataSnapshot.child(category).getChildren()) {
                        //Checking if the Make is already in the list of makes
                        if (!makeList.contains(make)){
                            //if it is not in the list add it
                            makeList.add(model);
                        }
                        //setting the make so we can iterate through them
                        make = aMake.getKey();
                        //Go through each year and add the models for that year
                        for (DataSnapshot aYear : dataSnapshot.child(make).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            year = aYear.getKey();
                            //Go through each model of each make
                            for (DataSnapshot aModel : dataSnapshot.child(year).getChildren()) {
                                CarModel carModel = new CarModel();
                                //Getting the model
                                model = aModel.getKey();
                                //Adding the make to the carModel Object
                                carModel.Make = make;
                                //Adding the category to the carModel Object
                                carModel.Category = category;
                                //add the models name to the carModel Object
                                carModel.Model = model;
                                //Get the year and add it to the carModel Object
                                carModel.Year = year;
                                //Check if the model is already in the models array list
                                if (!modelList.contains(model)){
                                    //if it is not in the list add it
                                    modelList.add(model);
                                    //Adding the carModel to the carModel List
                                    vehicleList.add(carModel);
                                }

                                //otherwise do not add the model as it already exists.

                                //Do I do one more loop to get the next information?
                                //Test both but first try just the first one.
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


        //Creating the Spinner Adapters
        ArrayAdapter makeSpinnerAdapter;
        ArrayAdapter modelSpinnerAdapter;
        ArrayAdapter yearSpinnerAdapter;

        //Creating the Spinner Objects
        Spinner makeSpinner = findViewById(R.id.spMake);
        Spinner modelSpinner = findViewById(R.id.spModel);
        Spinner yearSpinner = findViewById(R.id.spYear);

        //Set the make spinner list when the user visits the page.

        makeSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, makeList);
//        modelSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, carSportString);
//        yearSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, carSportString);

        //Creating the Make Spinner List
        makeSpinner.setAdapter(makeSpinnerAdapter);


        // set the list object into spinner object
        //modelSpinner.setAdapter(spinnerAdapter);
        //yearSpinner.setAdapter(spinnerAdapter);


    }
    /*
     * This Function is created to get all of the Cars Makes Models and years, and put them in the
     * respective ComboBox's (Spinners). This will populate the public arrays from the class
     * (1. makeList, 2.  modelList, 3. YearList)
     * The selected vehicle information will be passed to the next class.
     */

    public ArrayList<CarModel> getMakeModelYear() {
        //getting all of the items from the Vehicle portion of the database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        //List of carModel Objects so I can pass the one they choose to the users.
        vehicleList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Make
        ArrayList<String> makeList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Models
         ArrayList<String> modelList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Years
//        yearList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
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
                String category ="";
                String make = "";
                String model = "";
                String year = "";

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    category = snapshot.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot aMake : dataSnapshot.child(category).getChildren()) {
                        //Checking if the Make is already in the list of makes
                        if (!makeList.contains(make)){
                            //if it is not in the list add it
                            makeList.add(model);
                        }
                        //setting the make so we can iterate through them
                        make = aMake.getKey();
                        //Go through each year and add the models for that year
                        for (DataSnapshot aYear : dataSnapshot.child(make).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            year = aYear.getKey();
                            //Go through each model of each make
                            for (DataSnapshot aModel : dataSnapshot.child(year).getChildren()) {
                                CarModel carModel = new CarModel();
                                //Getting the model
                                model = aModel.getKey();
                                //Adding the make to the carModel Object
                                carModel.Make = make;
                                //Adding the category to the carModel Object
                                carModel.Category = category;
                                //add the models name to the carModel Object
                                carModel.Model = model;
                                //Get the year and add it to the carModel Object
                                carModel.Year = year;
                                //Check if the model is already in the models array list
                                if (!modelList.contains(model)){
                                    //if it is not in the list add it
                                    modelList.add(model);
                                    //Adding the carModel to the carModel List
                                    vehicleList.add(carModel);
                                }

                                //otherwise do not add the model as it already exists.

                                //Do I do one more loop to get the next information?
                                //Test both but first try just the first one.
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
    /*
     * This Event Handler is paired so when the user clicks any of the spinners it will check if a
     * value is chosen.
     *
     * The Function is created to populate The Model and View based on what value is returned from the Make.
     * If the user chooses a make, this function will be called to populate the make, once that file is populated,
     * the function will populate the years that are available
     *
     * This function will return None
     */
    public void onClickSpinners(View view){
        //Check to see if user has chosen a make.
        String make = "";
         make = makeSpinner.getSelectedItem().toString();
        if (make.equals("")){
            //look for the models of the make
            for (CarModel car : vehicleList){
                //if the users make is the make of the user, display the cars with the make
                if (car.Make.equals(make)){
                    //add the car into the list of models to display.
                    modelList.add(car.getModel());
                }
            }
        }

    }
}
