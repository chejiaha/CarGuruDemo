package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    //Creating a List to hold all of the Names of the Trims
    ArrayList<String> trimList;
    //Creating a List to hold all of the Names of the Years
    ArrayList<String> yearList;
    //Getting the Spinner(Combobox) Objects
    Spinner makeSpinner;
    Spinner modelSpinner;
    Spinner trimSpinner;
    Spinner yearSpinner;
    //Creating the Spinner Adapters
    ArrayAdapter makeSpinnerAdapter;
    ArrayAdapter modelSpinnerAdapter;
    ArrayAdapter trimSpinnerAdapter;
    ArrayAdapter yearSpinnerAdapter;
    //Creating the Button for searching
    Button searchVehicle;
    //TextView searchVehicle;
    //Creating my referenced to the database
    DatabaseReference dbRef;
    ValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_specific_model_list);
        //Setting the context
        context = getApplicationContext();
        //getting all of the items from the Vehicle portion of the database.
        dbRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        //List of carModel Objects so I can pass the one they choose to the users.
        makeList = new ArrayList<>();
        modelList = new ArrayList<>();
        trimList = new ArrayList<>();
        yearList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        //Creating the Spinner Objects
        makeSpinner = findViewById(R.id.spMake);
        modelSpinner = findViewById(R.id.spModel);
        trimSpinner = findViewById(R.id.spTrim);
        yearSpinner = findViewById(R.id.spYear);
        searchVehicle = findViewById(R.id.btnFindSpecificModelSearch);

        //Getting the data from the database
        vehicleList = getMakeModelYear();
        //Creating the spinner adapters and setting the lists that are used.
        makeSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, makeList);
        trimSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, modelList);
        modelSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, modelList);
        yearSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, yearList);

        //Setting the Spinner Adapters to the respected Spinner Objects
        makeSpinner.setAdapter(makeSpinnerAdapter);
        trimSpinner.setAdapter(trimSpinnerAdapter);
        modelSpinner.setAdapter(modelSpinnerAdapter);
        yearSpinner.setAdapter(yearSpinnerAdapter);

        //Setting the onTap Event listeners for Spinners
        makeSpinner.setOnTouchListener(onTouchMakeSpinner);
        modelSpinner.setOnTouchListener(onTouchModelSpinner);
        trimSpinner.setOnTouchListener(onTouchTrimSpinner);
        yearSpinner.setOnTouchListener(onTouchYearSpinner);

        //Setting the onTap Event Listener for Model
        searchVehicle.setOnTouchListener(onClickSearchVehicle);
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

//        //Creating a List to hold all of the Names of the Make
         makeList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Models
         modelList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Years
//        yearList = new ArrayList<>();

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
                                CarModel carModel = new CarModel();
                                //Getting the model
                                year = ssYear.getKey();
                                //Adding the make to the carModel Object
                                carModel.Make = make;
                                //add the models name to the carModel Object
                                carModel.Model = model;
                                //add the models name to the carModel Object
                                carModel.Trim = trim;
                                //Get the year and add it to the carModel Object
                                carModel.Year = Integer.parseInt(year);
                                //Check if the model is already in the models array list
                                if (!modelList.contains(model)){
                                    //if it is not in the list add it
                                    modelList.add(model);
                                }
                                //Adding the carModel to the carModel List
                                vehicleList.add(carModel);
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
     * This Event Handler is paired so when the user clicks the Make spinners it will check if a
     * value is chosen. If a value is chosen for the Make Spinner it will populate the model spinner
     *
     * The Function is created to populate The Model and View based on what value is returned from the Make.
     *
     * This function will return None
     */
    private View.OnTouchListener onTouchMakeSpinner= new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            //Once the user finishes choosing a value populate the next one.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Loop through the carModel Array list so we can get the list of Models based on make they chose
                //debug
                //Log.d("Car list is ", makeList.get(0));
                makeSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, makeList);
                makeSpinner.setAdapter(makeSpinnerAdapter);
            }
            return false;
        }
    };
    /*
     * This Event Handler is paired so when the user clicks the Make spinners it will check if a
     * value is chosen. If a value is chosen for the Make Spinner it will populate the model spinner
     *
     * The Function is created to populate The Model and View based on what value is returned from the Make.
     * If the user chooses a make, this function will be called to populate the year, once that file is populated,
     * the function will populate the years that are available
     *
     * This function will return None
     */
    private View.OnTouchListener onTouchModelSpinner= new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            //Once the user finishes choosing a value populate the next one.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Clear the list so it can re calculate the years we have
                modelList.clear();
                //Loop through the carModel Array list so we can get the list of Models based on make they chose
                for (CarModel carModel : vehicleList){
                    //for every model, look for the model that the user chose
                    String userMake;
                    try {
                        userMake = makeSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userMake = "Audi";

                    }
                    if(carModel.getMake().equals(userMake)){
                        modelList.add(carModel.getModel());
                    }
                }
                modelSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, modelList);
                modelSpinner.setAdapter(modelSpinnerAdapter);
            }
            return false;
        }
    };

    /*
     * This Event Handler is paired so when the user clicks the Trim spinners it will check if a
     * value is chosen. If a value is chosen for the Make Spinner it will populate the Year spinner
     *
     * The Function is created to populate The Year based on what value is returned from the Trim.
     * If the user chooses a make, this function will be called to populate the year, once that file is populated,
     * the function will populate the years that are available
     *
     * This function will return None
     */
    private View.OnTouchListener onTouchTrimSpinner = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            //Once the user finishes choosing a value populate the next one.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Clear the list so it can re calculate the years we have
                trimList.clear();
                //Loop through the carModel Array list so we can get the list of Models based on make they chose
                for (CarModel carModel : vehicleList){
                    //for every model, look for the model that the user chose
                    String userModel;
                    try {
                        userModel = modelSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userModel = "quattro 2Point0T Progressiv";
                    }
                    if(carModel.getModel().equals(userModel)){
                        trimList.add(carModel.getTrim());
                    }
                }
                trimSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, trimList);
                trimSpinner.setAdapter(trimSpinnerAdapter);
            }
            return false;
        }
    };



    /*
     * This Event Handler is paired so when the user clicks the Make spinners it will check if a
     * value is chosen. If a value is chosen for the Make Spinner it will populate the year spinner
     *
     * The Function is created to populate The Model and View based on what value is returned from the Make.
     * If the user chooses a make, this function will be called to populate the year, once that file is populated,
     * the function will populate the years that are available
     *
     * This function will return None
     */
    private View.OnTouchListener onTouchYearSpinner= new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            //Once the user finishes choosing a value populate the next one.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Clear the list so it can re calculate the years we have
                yearList.clear();
                for (CarModel carModel : vehicleList){
                    //for every model, look for the model that the user chose
                    String userTrim;
                    try {
                        userTrim = trimSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userTrim = "";
                    }
                    //String userModel = modelSpinner.getSelectedItem().toString();
                    if(carModel.getTrim().equals(userTrim)){
                        //Workaround for .toString() possible error...
                        //if the year is already in the list no need to add it.
                        yearList.add(String.valueOf(carModel.getYear()));
                    }
                }
                yearSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, yearList);
                yearSpinner.setAdapter(yearSpinnerAdapter);
            }
            return false;
        }
    };

    /*
     * When the user clicks the search button it will send the user to the next page
     *
     * The Function is created to get the vehicle data that the user requested. It will look through
     * The database Reference and get the item information that has the same make,model and year.
     * This function will Send the user to the specific cars page and will pass the Specific Vehicle
     * Information received from the Database
     *
     * This function will return None
     */
    private View.OnTouchListener onClickSearchVehicle = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Getting the users selection from the spinners
            String userMake = makeSpinner.getSelectedItem().toString();
            String userModel = modelSpinner.getSelectedItem().toString();
            String userTrim = trimSpinner.getSelectedItem().toString();
            String userYear = yearSpinner.getSelectedItem().toString();

            //Checking if the spinners are all selected.
            if (userMake.isEmpty()) {
                //Display Error Toast
                Toast.makeText(context, "Please Choose a Valid Make", Toast.LENGTH_SHORT).show();
            } else if (userModel.isEmpty()) {
                //Display Error Toast
                Toast.makeText(context, "Please Choose a Valid Model", Toast.LENGTH_SHORT).show();
            } else if (userYear.isEmpty()) {
                //Display Error Toast
                Toast.makeText(context, "Please Choose a Valid Year", Toast.LENGTH_SHORT).show();
            } else {
                CarModel carModel = new CarModel();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Setting the Make, Year, Model in the object
                    carModel.setMake(userMake);
                    carModel.setModel(userModel);
                    carModel.setTrim(userTrim);
                    //DatabaseReference getValueFromDb =  FirebaseDatabase.getInstance().getReference().child("Vehicle");
                    listener = dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /*
                             *   This method is used to get the vehicle information from the users selection.

                             *   Will Return:
                             *      CARMODEL OBJECT!
                             *      Description         <String>
                             *      Recalls             [List]
                             *      Category            <String>
                             *      Drivetrain          <String>
                             *      Cylinders           <int>
                             *      CommonProblems      [List]
                             *      Doors               <int>
                             *      Engine              <String>
                             *      Horsepower          <int>
                             *      MPG                 <int>
                             *      Price               <int>
                             *      Seats               <int>
                             */

                            //Create a Car Model for the descriptions
                            for (DataSnapshot ssCarDesc : dataSnapshot.child(userMake).child(userModel).child(userTrim).child(userYear).getChildren()) {
                                //The Key of each description (Category,CommonProblems,Description...)
                                String descName = ssCarDesc.getKey();
                                //Temporary varible to convert the data from a string, to an int.
                                int convertToInt =0;
                                //Creating the list to contain the Recalls and Common Problems
                                String[] descArray;


                                carModel.setYear(Integer.parseInt(userYear));
                                Log.d("EachDataSnapshot", "Data Snapshot " + userYear );

                                //Check what it is and put it into the correct value
                                switch (descName){
                                    case "Category":
                                        //convert to list and store in Category. (for futureproofing)

                                        carModel.setCategory(ssCarDesc.getValue().toString());
                                        break;
                                    case "CommonProblems":
                                        //Debug
                                        Log.d("FindSpecificModel", "The Car information for Common Problems is" + ssCarDesc.getValue().toString());
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        carModel.setCommonProblems(descArray);
                                        break;
                                    case "Ratings":
                                        //Debug
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        carModel.setRatings(descArray);
                                        break;
                                    case "Description":
                                        //Create a Car Model for the descriptions
                                        carModel.setDescription(ssCarDesc.getValue().toString()) ;
                                        break;
                                    case "Doors":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setDoors(convertToInt);
                                        break;
                                    case "Engine":
                                        //Create a Car Model for the descriptions
                                        carModel.setEngine(ssCarDesc.getValue().toString()); ;
                                        break;
                                    case "Horsepower":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setHorsePower(convertToInt);
                                        break;
                                    case "MPG":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setMPG(convertToInt);
                                        break;
                                    case "Price":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setPrice(convertToInt);
                                        break;
                                    case "Recalls":
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        carModel.setRecalls(descArray);
                                        break;
                                    case "Seats":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setSeats(convertToInt);
                                        break;
                                    case "Drivetrain":
                                        //Convert it to a string then parse for the int
                                        carModel.setDrivetrain(ssCarDesc.getValue().toString());
                                        break;
                                    case "Cylinders":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setCylinders(convertToInt);
                                        break;
                                    case "Torque ft-lb":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        carModel.setTorque(convertToInt);
                                        break;
                                    default:
                                        //Debug
                                        Log.d("NoModelFound", ssCarDesc.getValue().toString());

                                }//End Of Switch
                            }
                                //Send the model to the next page
                                Intent intent = new Intent(context, SpecificModel.class);
                                intent.putExtra("CarModel", carModel);
                                //Setting the carModel Object to the next page.
                                startActivity(intent);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }//end of else
            return false;
        }//End of ontouch
    };//end of class

}
