package owen.ross.carguru.ui.FindSpecificModel;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import owen.ross.carguru.R;
import owen.ross.carguru.models.Car;


public class SpecificVehicleInfoFragment extends Fragment {


    public SpecificVehicleInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;
    Context context;
    //Setting all of the variables
    TextView tvCompareCars;
    TextView tvCarName;
    TextView tvDescriptionText;
    TextView tvAproximateCost;
    ImageView ivCompareCars;
    ImageView ivCarImage;
    ImageView ivOtherCarImages;
    ImageView ivResult1;
    ImageView ivResult2;
    ImageView ivResult3;
    ImageView ivResult4;
    TextView tvResult1;
    TextView tvResult2;
    TextView tvResult3;
    TextView tvResult4;
    ListView lvCommonProblems;
    ListView lvRecalls;
    ListView lvDescription;
    Slider slCost;
    ArrayAdapter commonProblemsAdapter;
    ArrayAdapter recallsAdapter;
    ArrayAdapter descriptionListAdapter;
    Car car;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_specific_vehicle_info, container, false);

        //Setting the context
        context = getActivity();
        //Pairing the View Objects with the respected View
        tvCompareCars = view.findViewById(R.id.tvChooseCarsToCompare);
        tvCarName = view.findViewById(R.id.tvSpecificCarTitle);
        tvDescriptionText = view.findViewById(R.id.tvSpecificCarDescriptionText);
        tvAproximateCost = view.findViewById(R.id.tvspecificCarApproxCost);
        ivCompareCars = view.findViewById(R.id.ivChooseCarsToCompare);
        ivCarImage = view.findViewById(R.id.ivSpecificCarPic);
        //ivOtherCarImages = findViewById(R.id.tvChooseCarsToCompare);
        ivResult1 = view.findViewById(R.id.ivSpecificCarGasRating);
        ivResult2 = view.findViewById(R.id.ivSpecificCarCosts);
        ivResult3 = view.findViewById(R.id.ivSpecificCarSpeed);
        ivResult4 = view.findViewById(R.id.ivSpecificCarWildcard);
        tvResult1 = view.findViewById(R.id.tvSpecificCarGasRating);
        tvResult2 = view.findViewById(R.id.tvSpecificCarCosts);
        tvResult3 = view.findViewById(R.id.tvSpecificCarSpeed);
        tvResult4 = view.findViewById(R.id.tvSpecificCarWildcard);
        lvCommonProblems = view.findViewById(R.id.lvSpecificCarCommonProblemList);
        lvRecalls = view.findViewById(R.id.lvSpecificCarRecallsList);
        lvDescription = view.findViewById(R.id.lvSpecificCarDescriptionList);
        //Get the vehicle details and populate arrays
        populateCarDetails();

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * This class is used to populate the fields with the information from the previous slide,
     * and add it into the next slides.
     * This method will get the
     * NameOfTheCar, The Description, Make, Model, Year, AvrgCostOnMarket, carImage
     *
     * Returns None
     */
    private void populateCarDetails(){

        //Get CarModel Object from the Specific Car Page
        Bundle bundle = new Bundle();
        car = (Car) bundle.getSerializable("car");
        car = ((Car) getArguments().getSerializable("car"));
        //debug
        Log.d("SpecificModel", "CarModel Make is" + car.getMake());
        Log.d("SpecificModel", "CarModel Model is" + car.getModel());
        Log.d("SpecificModel", "CarModel Year is" + car.getYear());
        Log.d("SpecificModel", "CarModel Seats is" + car.getSeats());
        Log.d("SpecificModel", "CarModel Engine is" + car.getEngine());
        Log.d("SpecificModel", "CarModel Cylinders is" + car.getCylinders());
        Log.d("SpecificModel", "CarModel Category is" + car.getCategory());
        Log.d("SpecificModel", "CarModel Drivetrain is" + car.getDrivetrain());
        Log.d("SpecificModel", "CarModel Doors is" + car.getDoors());

        //Setting the Model information into the View
        tvCarName.setText(String.format("%s %s %s",car.getMake(),car.getModel(), car.getYear() ));
        tvDescriptionText.setText(car.getDescription());

        //A method to check if the fields are null
        CheckFields();
        //Check if ratings are supplied
        if (car.getRatings() != null){
            tvResult1.setText(car.getRatings()[0]);
            tvResult2.setText(car.getRatings()[1]);
            tvResult3.setText(car.getRatings()[2]);
            tvResult4.setText(car.getRatings()[3]);
            //LATER MOVE THIS TO THE COMMON PROBLEMS ADAPTER
            recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getRatings());
        }

        //Adding all of the items from an object
        //BETTER WAY TO DO THIS?
        ArrayList<String> descriptionList = new ArrayList<>();
        descriptionList.add(String.format("Price: %s",String.valueOf(car.getPrice())));
        descriptionList.add(String.format("Seats: %s",String.valueOf(car.getSeats())));
        descriptionList.add(String.format("Torque ft-lb: %s",String.valueOf(car.getTorque())));
        descriptionList.add(String.format("Doors: %s",String.valueOf(car.getDoors())));
        descriptionList.add(String.format("Engine: %s",String.valueOf(car.getEngine())));
        descriptionList.add(String.format("Horsepower: %s",String.valueOf(car.getHorsePower())));
        descriptionList.add(String.format("Categories: %s",String.valueOf(car.getCategory())));
        descriptionList.add(String.format("MPG: %s",String.valueOf(car.getMPG())));
        descriptionList.add(String.format("Drivetrain: %s",String.valueOf(car.getDrivetrain())));
        descriptionList.add(String.format("Cylinders: %s",String.valueOf(car.getCylinders())));

        //Setting the List Adapters to display the vehicle information.
        commonProblemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getCommonProblems());
        recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getRecalls());
        descriptionListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, descriptionList);
        // Displaying the Lists on the respective ListView
        lvCommonProblems.setAdapter(commonProblemsAdapter);
        lvRecalls.setAdapter(recallsAdapter);
        lvDescription.setAdapter(descriptionListAdapter);
    }


    /*
     * A method that will Check if the fields are empty or null
     *
     * This will prevent and handle the event of a null pointer or empty fields occuring
     *
     * Checks for
     *  <String>
     *      If its Null or "" Empty
     *  <int>
     *      If its 0 throw an error TODO(Since its in beta do not throw yet.)
     *  [List]
     *      If its Empty, or if it is null Supply a string to display.
     *
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
     *
     * Returns None.
     */
    public void CheckFields() throws NullPointerException{
        //Check if the arrays are null, if they are, just populate the empty array
        //Checking the Common problems Array, if it is null or less then one.
        if (car.getCommonProblems() == null){
            String[] emptyList = {"No Common Problems Allocated"};
            car.setRecalls(emptyList);
        }
        //Checking the Recalls Array, if it is null or less then one.
        if (car.getRecalls() == null){
            String[] emptyList = {"No Recalls"};
            car.setRecalls(emptyList);
        }
        //Checking the Category  Array, if it is null or less then one.
        //TODO When the categories are arrays implement the code!

        //Checking if the Number of Engine is set null or is empty
        if (car.getEngine() == null || car.getEngine().trim().equals("")){
            car.setEngine("The Engine Type has not been Set!");
            //throw new NullPointerException("The Engine String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (car.getCategory() == null || car.getCategory().trim().equals("")){
            car.setCategory("The Category Type has not been Set!");
            //throw new NullPointerException("The Category String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (car.getDrivetrain() == null || car.getDrivetrain().trim().equals("")){
            car.setDrivetrain("The Drivetrain Type has not been Set!");
            //throw new NullPointerException("The Drivetrain String is not set in SpecificModel");
        }
        //Checking if the variable for Price is null or a string
        if (car.getPrice() == 0){
            car.setPrice(-1);
            //throw new NullPointerException("The Price is not set in SpecificModel") ;
        }
        //Checking if the variable for Seats is set is 0
        if (car.getSeats() == 0){
            car.setSeats(-1);
            //throw new NullPointerException("The Seats Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Torque is set is 0
        if (car.getTorque() == 0){
            car.setTorque(-1);
            //throw new NullPointerException("The Torque Variable is not set in SpecificModel") ;
        }
        //Checking if the Number of Doors is set is 0
        if (car.getDoors() == 0){
            car.setDoors(-1);
            //throw new NullPointerException("The Doors Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Horsepower is set is 0
        if (car.getHorsePower() == 0){
            car.setHorsePower(-1);
            // throw new NullPointerException("The HorsePower Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for MPG is set is 0
        if (car.getMPG() == 0){
            car.setMPG(-1);
            //   throw new NullPointerException("The MPG Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Cylinders is set is 0
        if (car.getCylinders() == 0){
            car.setCylinders(-1);
            //   throw new NullPointerException("The Cylinders Variable is not set in SpecificModel") ;
        }
    }


}