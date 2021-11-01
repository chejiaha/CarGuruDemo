package owen.ross.carguru.UI.FindSpecificModel;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import owen.ross.carguru.Adaptors.CommonListAdapter;
import owen.ross.carguru.Adaptors.CustomAdapter;
import owen.ross.carguru.R;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.Models.HelperFunctions;


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
    RecyclerView rvCommonProblems;
    RecyclerView rvRecalls;
    RecyclerView rvDescription;
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
        //tvAproximateCost = view.findViewById(R.id.tvspecificCarApproxCost);
        ivCompareCars = view.findViewById(R.id.ivChooseCarsToCompare);
        ivCarImage = view.findViewById(R.id.ivSpecificCarPic);
        //ivOtherCarImages = findViewById(R.id.tvChooseCarsToCompare);
        //Adding the ratings
//        ivResult1 = view.findViewById(R.id.ivSpecificCarGasRating);
//        ivResult2 = view.findViewById(R.id.ivSpecificCarCosts);
//        ivResult3 = view.findViewById(R.id.ivSpecificCarSpeed);
//        ivResult4 = view.findViewById(R.id.ivSpecificCarWildcard);
//        tvResult1 = view.findViewById(R.id.tvSpecificCarGasRating);
//        tvResult2 = view.findViewById(R.id.tvSpecificCarCosts);
//        tvResult3 = view.findViewById(R.id.tvSpecificCarSpeed);
//        tvResult4 = view.findViewById(R.id.tvSpecificCarWildcard);
        rvCommonProblems = view.findViewById(R.id.rvSpecificCarCommonProblemList);
        rvRecalls = view.findViewById(R.id.rvSpecificCarRecallsList);
        rvDescription = view.findViewById(R.id.rvSpecificCarDescriptionList);


        //Get CarModel Object from the Specific Car Page
        Bundle bundle = new Bundle();
        car = (Car) bundle.getSerializable("car");
        car = ((Car) getArguments().getSerializable("car"));

        //Get the vehicle details and populate arrays and View Data
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

        String make = car.getMake();
        String model = car.getModel();
        String trim = car.getTrim();
        String year = car.getYear() + "";

        //debug
//        Log.d("SpecificModel", "CarModel Make is" + year);
//        Log.d("SpecificModel", "CarModel Model is" + model);
//        Log.d("SpecificModel", "CarModel Year is" + year);
//        Log.d("SpecificModel", "CarModel Seats is" + car.getSeats());
//        Log.d("SpecificModel", "CarModel Engine is" + car.getEngine());
//        Log.d("SpecificModel", "CarModel Cylinders is" + car.getCylinders());
//        Log.d("SpecificModel", "CarModel Category is" + car.getCategory());
//        Log.d("SpecificModel", "CarModel Drivetrain is" + car.getDrivetrain());
//        Log.d("SpecificModel", "CarModel Doors is" + car.getDoors());

        //Setting the car Image
        String linkToCar = String.format("@drawable/%s_%s_%s", make.toLowerCase(), model.toLowerCase().replace("-", "_"), year);
        //Setting the Model information into the View
        tvCarName.setText(String.format("%s %s %s %s",make, model, trim, year ));
        tvDescriptionText.setText(car.getDescription());

        //A method to check if the fields are null
        HelperFunctions.CheckFields(car);
        //Check if ratings are supplied
        if (car.getRatings() != null){
            tvResult1.setText(car.getRatings()[0]);
            tvResult2.setText(car.getRatings()[1]);
            tvResult3.setText(car.getRatings()[2]);
            tvResult4.setText(car.getRatings()[3]);
            //LATER MOVE THIS TO THE COMMON PROBLEMS ADAPTER
            recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getRatings());
        }
        //TODO move this code to helper function 'CheckFields'
        //Adding all of the items from an object
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
        ArrayList<String> commonProblems = new ArrayList<>();
        ArrayList<String> recalls = new ArrayList<>();
        // Check if the common problems array is null
        try{
            for(String problem : car.getCommonProblems()){
                commonProblems.add(problem);
            }
        }catch (NullPointerException err){
            commonProblems.add("No Common Problems for this vehicle");
        }
        // Check if the recalls array is null
        try{
            for(String recall : car.getRecalls()){
                recalls.add(recall);
            }
        }catch (NullPointerException err) {
            recalls.add("No Recalls for this vehicle");
        }


        // Creating a commonListAdapter and setting all of the recycler views.
        CommonListAdapter listAdapterCommonProblems = new CommonListAdapter(commonProblems);
        rvCommonProblems.setLayoutManager(new LinearLayoutManager(getActivity()));

        CommonListAdapter listAdapterRecalls = new CommonListAdapter(recalls);
        rvRecalls.setAdapter(listAdapterRecalls);
        rvRecalls.setLayoutManager(new LinearLayoutManager(getActivity()));

        CommonListAdapter listAdapterDescription = new CommonListAdapter(descriptionList);
        rvDescription.setAdapter(listAdapterDescription);
        rvDescription.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvCommonProblems.setAdapter(listAdapterCommonProblems);
        rvRecalls.setAdapter(listAdapterRecalls);
        rvDescription.setAdapter(listAdapterDescription);
    }





}