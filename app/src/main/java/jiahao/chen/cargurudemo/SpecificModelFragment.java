package jiahao.chen.cargurudemo;

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


public class SpecificModelFragment extends Fragment {

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
    CarModel carModel;

    public SpecificModelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specific_model, container, false);

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
        carModel = (CarModel) bundle.getSerializable("CarModel");

        carModel = ((CarModel) getArguments().getSerializable("CarModel"));
        //debug
        Log.d("SpecificModel", "CarModel Make is" + carModel.getMake());
        Log.d("SpecificModel", "CarModel Model is" + carModel.getModel());
        Log.d("SpecificModel", "CarModel Year is" + carModel.getYear());
        Log.d("SpecificModel", "CarModel Seats is" + carModel.getSeats());
        Log.d("SpecificModel", "CarModel Engine is" + carModel.getEngine());
        Log.d("SpecificModel", "CarModel Cylinders is" + carModel.getCylinders());
        Log.d("SpecificModel", "CarModel Category is" + carModel.getCategory());
        Log.d("SpecificModel", "CarModel Drivetrain is" + carModel.getDrivetrain());
        Log.d("SpecificModel", "CarModel Doors is" + carModel.getDoors());

        //Setting the Model information into the View
        tvCarName.setText(String.format("%s %s %s",carModel.getMake(),carModel.getModel(), carModel.getYear() ));
        tvDescriptionText.setText(carModel.getDescription());

        //A method to check if the fields are null
        CheckFields();
        //Check if ratings are supplied
        if (carModel.getRatings() != null){
            tvResult1.setText(carModel.getRatings()[0]);
            tvResult2.setText(carModel.getRatings()[1]);
            tvResult3.setText(carModel.getRatings()[2]);
            tvResult4.setText(carModel.getRatings()[3]);
            //LATER MOVE THIS TO THE COMMON PROBLEMS ADAPTER
            recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getRatings());
        }

        //Adding all of the items from an object
        //BETTER WAY TO DO THIS?
        ArrayList<String> descriptionList = new ArrayList<>();
        descriptionList.add(String.format("Price: %s",String.valueOf(carModel.getPrice())));
        descriptionList.add(String.format("Seats: %s",String.valueOf(carModel.getSeats())));
        descriptionList.add(String.format("Torque ft-lb: %s",String.valueOf(carModel.getTorque())));
        descriptionList.add(String.format("Doors: %s",String.valueOf(carModel.getDoors())));
        descriptionList.add(String.format("Engine: %s",String.valueOf(carModel.getEngine())));
        descriptionList.add(String.format("Horsepower: %s",String.valueOf(carModel.getHorsePower())));
        descriptionList.add(String.format("Categories: %s",String.valueOf(carModel.getCategory())));
        descriptionList.add(String.format("MPG: %s",String.valueOf(carModel.getMPG())));
        descriptionList.add(String.format("Drivetrain: %s",String.valueOf(carModel.getDrivetrain())));
        descriptionList.add(String.format("Cylinders: %s",String.valueOf(carModel.getCylinders())));

        //Setting the List Adapters to display the vehicle information.
        commonProblemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getCommonProblems());
        recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getRecalls());
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
        if (carModel.getCommonProblems() == null){
            String[] emptyList = {"No Common Problems Allocated"};
            carModel.setRecalls(emptyList);
        }
        //Checking the Recalls Array, if it is null or less then one.
        if (carModel.getRecalls() == null){
            String[] emptyList = {"No Recalls"};
            carModel.setRecalls(emptyList);
        }
        //Checking the Category  Array, if it is null or less then one.
        //TODO When the categories are arrays implement the code!

        //Checking if the Number of Engine is set null or is empty
        if (carModel.getEngine() == null || carModel.getEngine().trim().equals("")){
            carModel.setEngine("The Engine Type has not been Set!");
            //throw new NullPointerException("The Engine String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (carModel.getCategory() == null || carModel.getCategory().trim().equals("")){
            carModel.setCategory("The Category Type has not been Set!");
            //throw new NullPointerException("The Category String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (carModel.getDrivetrain() == null || carModel.getDrivetrain().trim().equals("")){
            carModel.setDrivetrain("The Drivetrain Type has not been Set!");
            //throw new NullPointerException("The Drivetrain String is not set in SpecificModel");
        }
        //Checking if the variable for Price is null or a string
        if (carModel.getPrice() == 0){
            carModel.setPrice(-1);
            //throw new NullPointerException("The Price is not set in SpecificModel") ;
        }
        //Checking if the variable for Seats is set is 0
        if (carModel.getSeats() == 0){
            carModel.setSeats(-1);
            //throw new NullPointerException("The Seats Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Torque is set is 0
        if (carModel.getTorque() == 0){
            carModel.setTorque(-1);
            //throw new NullPointerException("The Torque Variable is not set in SpecificModel") ;
        }
        //Checking if the Number of Doors is set is 0
        if (carModel.getDoors() == 0){
            carModel.setDoors(-1);
            //throw new NullPointerException("The Doors Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Horsepower is set is 0
        if (carModel.getHorsePower() == 0){
            carModel.setHorsePower(-1);
            // throw new NullPointerException("The HorsePower Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for MPG is set is 0
        if (carModel.getMPG() == 0){
            carModel.setMPG(-1);
            //   throw new NullPointerException("The MPG Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Cylinders is set is 0
        if (carModel.getCylinders() == 0){
            carModel.setCylinders(-1);
            //   throw new NullPointerException("The Cylinders Variable is not set in SpecificModel") ;
        }
    }


}