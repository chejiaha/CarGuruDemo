package owen.ross.carguru.ui.Catagories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import owen.ross.carguru.R;
import owen.ross.carguru.models.Car;
import owen.ross.carguru.models.Database;


public class SearchForSpecificModel extends Fragment {

    public SearchForSpecificModel() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //Creating the context
    Context context;
    //List of car Objects so I can pass the one they choose to the users.
    ArrayList<Car> vehicleList;
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
    //Creating a Navigation View
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_search_for_specific_model, container, false);

        //Setting the context
        context = getActivity();
        //getting all of the items from the Vehicle portion of the database.
        dbRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        //List of car Objects so I can pass the one they choose to the users.
        makeList = new ArrayList<>();
        modelList = new ArrayList<>();
        trimList = new ArrayList<>();
        yearList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        //Creating the Spinner Objects
        makeSpinner = view.findViewById(R.id.spMake);
        modelSpinner = view.findViewById(R.id.spModel);
        trimSpinner = view.findViewById(R.id.spTrim);
        yearSpinner = view.findViewById(R.id.spYear);
        searchVehicle = view.findViewById(R.id.btnFindSpecificModelSearch);

        //Getting the data from the database
        vehicleList = Database.getMakeModelYear();
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

        // Inflate the layout for this fragment
        return view;
    } //End of Create view

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
                //If the user clicks make, clear all of the other selection fields.
                modelSpinner.setAdapter(null);
                trimSpinner.setAdapter(null);
                yearSpinner.setAdapter(null);

                //Log.d("Car list is ", makeList.get(0));
                //makeSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, makeList);
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
                //Loop through the car Array list so we can get the list of Models based on make they chose
                for (Car car : vehicleList){
                    //for every model, look for the model that the user chose
                    String userMake;
                    try {
                        userMake = makeSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userMake = "Audi";

                    }
                    if(car.getMake().equals(userMake)){
                        if (!modelList.contains(car.getModel())){
                            //if it is not in the list already add it
                            modelList.add(car.getModel());
                        }
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
                //Loop through the car Array list so we can get the list of Models based on make they chose
                for (Car car : vehicleList){
                    //for every model, look for the model that the user chose
                    String userModel;
                    try {
                        userModel = modelSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userModel = "quattro 2Point0T Progressiv";
                    }
                    if(car.getModel().equals(userModel)){
                        trimList.add(car.getTrim());
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
                for (Car car : vehicleList){
                    //for every model, look for the model that the user chose
                    String userTrim;
                    try {
                        userTrim = trimSpinner.getSelectedItem().toString();
                    }catch (Exception e){
                        userTrim = "";
                    }
                    //String userModel = modelSpinner.getSelectedItem().toString();
                    if(car.getTrim().equals(userTrim)){
                        //if the year is already in the list no need to add it.

                        if (!yearList.contains(car.getYear())){
                            //if it is not in the list already add it
                            yearList.add(String.valueOf(car.getYear()));
                        }
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
    private View.OnTouchListener onClickSearchVehicle = new View.OnTouchListener() {
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
                Car car = new Car();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Setting the Make, Year, Model in the object
                    car.setMake(userMake);
                    car.setModel(userModel);
                    car.setTrim(userTrim);
                    //DatabaseReference getValueFromDb =  FirebaseDatabase.getInstance().getReference().child("Vehicle");
                    listener = dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /*
                             *   This method is used to get the vehicle information from the users selection.

                             *   Will Return:
                             *      car OBJECT!
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
                                int convertToInt = 0;
                                //Creating the list to contain the Recalls and Common Problems
                                String[] descArray;


                                car.setYear(Integer.parseInt(userYear));
                                Log.d("EachDataSnapshot", "Data Snapshot " + userYear);

                                //Check what it is and put it into the correct value
                                switch (descName) {
                                    case "Category":
                                        //convert to list and store in Category. (for futureproofing)

                                        car.setCategory(ssCarDesc.getValue().toString());
                                        break;
                                    case "CommonProblems":
                                        //Debug
                                        Log.d("FindSpecificModel", "The Car information for Common Problems is" + ssCarDesc.getValue().toString());
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        car.setCommonProblems(descArray);
                                        break;
                                    case "Ratings":
                                        //Debug
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        car.setRatings(descArray);
                                        break;
                                    case "Description":
                                        //Create a Car Model for the descriptions
                                        car.setDescription(ssCarDesc.getValue().toString());
                                        break;
                                    case "Doors":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setDoors(convertToInt);
                                        break;
                                    case "Engine":
                                        //Create a Car Model for the descriptions
                                        car.setEngine(ssCarDesc.getValue().toString());
                                        ;
                                        break;
                                    case "Horsepower":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setHorsePower(convertToInt);
                                        break;
                                    case "MPG":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setMPG(convertToInt);
                                        break;
                                    case "Price":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setPrice(convertToInt);
                                        break;
                                    case "Recalls":
                                        //Convert the string into a list
                                        descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                        car.setRecalls(descArray);
                                        break;
                                    case "Seats":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setSeats(convertToInt);
                                        break;
                                    case "Drivetrain":
                                        //Convert it to a string then parse for the int
                                        car.setDrivetrain(ssCarDesc.getValue().toString());
                                        break;
                                    case "Cylinders":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setCylinders(convertToInt);
                                        break;
                                    case "Torque ft-lb":
                                        //Convert it to a string then parse for the int
                                        convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
                                        car.setTorque(convertToInt);
                                        break;
                                    default:
                                        //Debug
                                        Log.d("NoModelFound", ssCarDesc.getValue().toString());

                                }//End Of Switch
                            }
                            //NextSend the model to the next page
                            //Adding the arguments into the class
                            Bundle bundle = new Bundle();
                            //bundle.putSerializable("car", car);
                            //Going from SearchCarFragment to Specific model fragment
                            //Navigation.findNavController(view).navigate(R.id.action_navigation_SearchCar_to_fragment_specificModel2, bundle);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }//end of else
            return false;
        }//End of ontouch
    };// End of onClickSearchVehicle


}