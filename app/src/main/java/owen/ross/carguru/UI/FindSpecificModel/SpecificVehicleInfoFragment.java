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
        String bingo = bundle.getString("bingo");
        Log.d("Test", "Debug" + bingo);

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

        //Adding all of the items from an object
        //BETTER WAY TO DO THIS?
        String[] descriptionList = new String[10];
        descriptionList[0] = (String.format("Price: %s",String.valueOf(car.getPrice())));
        descriptionList[1] = (String.format("Seats: %s",String.valueOf(car.getSeats())));
        descriptionList[2] = (String.format("Torque ft-lb: %s",String.valueOf(car.getTorque())));
        descriptionList[3] = (String.format("Doors: %s",String.valueOf(car.getDoors())));
        descriptionList[4] =  (String.format("Engine: %s",String.valueOf(car.getEngine())));
        descriptionList[5] = (String.format("Horsepower: %s",String.valueOf(car.getHorsePower())));
        descriptionList[6] = (String.format("Categories: %s",String.valueOf(car.getCategory())));
        descriptionList[7] = (String.format("MPG: %s",String.valueOf(car.getMPG())));
        descriptionList[8] = (String.format("Drivetrain: %s",String.valueOf(car.getDrivetrain())));
        descriptionList[9] = (String.format("Cylinders: %s",String.valueOf(car.getCylinders())));

        //Setting the List Adapters to display the vehicle information.
        Log.d("EndALL", "CarModel commonProblems" + car.getCommonProblems());
        //commonProblemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getCommonProblems());
        // recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, car.getRecalls());
        //descriptionListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, descriptionList);
        // Creating a commonListAdapter and setting all of the recycler views.
        CommonListAdapter listAdapterCommonProblems = new CommonListAdapter(car.getCommonProblems());
        rvCommonProblems.setLayoutManager(new LinearLayoutManager(getActivity()));

        CommonListAdapter listAdapterRecalls = new CommonListAdapter(car.getRecalls());
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