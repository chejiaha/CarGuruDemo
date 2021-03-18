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

public class CompareCarsFragmentA extends Fragment {
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

    public CompareCarsFragmentA() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting the context
        context = getActivity();
        View theView = getView();
        //Pairing the View Objects with the respected View
        tvCompareCars = theView.findViewById(R.id.tvChooseCarsToCompare);
        tvCarName =theView. findViewById(R.id.tvSpecificCarTitle);
        tvDescriptionText = theView.findViewById(R.id.tvSpecificCarDescriptionText);
        tvAproximateCost = theView.findViewById(R.id.tvspecificCarApproxCost);
        ivCompareCars = theView.findViewById(R.id.ivChooseCarsToCompare);
        ivCarImage = theView.findViewById(R.id.ivSpecificCarPic);
        //ivOtherCarImages = findViewById(R.id.tvChooseCarsToCompare);
        ivResult1 = theView.findViewById(R.id.ivSpecificCarGasRating);
        ivResult2 = theView.findViewById(R.id.ivSpecificCarCosts);
        ivResult3 = theView.findViewById(R.id.ivSpecificCarSpeed);
        ivResult4 = theView.findViewById(R.id.ivSpecificCarWildcard);
        tvResult1 = theView.findViewById(R.id.tvSpecificCarGasRating);
        tvResult2 = theView.findViewById(R.id.tvSpecificCarCosts);
        tvResult3 = theView.findViewById(R.id.tvSpecificCarSpeed);
        tvResult4 = theView.findViewById(R.id.tvSpecificCarWildcard);
        lvCommonProblems = theView.findViewById(R.id.lvSpecificCarCommonProblemList);
        lvRecalls = theView.findViewById(R.id.lvSpecificCarRecallsList);
        lvDescription = theView.findViewById(R.id.lvSpecificCarDescriptionList);
        //Get the vehicle details and populate arrays
        populateCarDetails();
    }

    private void populateCarDetails() {
        //Get CarModel Object from the Specific Car Page
       //CarModel carModel = (CarModel) this.getIntent().getSerializableExtra("CarModel");
        CarModel carModel = (CarModel) getArguments().get("CarModel");
        //debug
        Log.d("SpecificModel", "CarModel is" + carModel.getMake());
        Log.d("SpecificModel", "CarModel is" + carModel.getModel());
        //Setting the Model information into the View
        tvCarName.setText(String.format("%s %s %s", carModel.getMake(), carModel.getModel(), carModel.getYear()));
        tvDescriptionText.setText(carModel.getDescription());

        //Check if ratings are supplied
        if (carModel.getRatings().length < 1 || carModel.getRatings() != null) {
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
        descriptionList.add(String.format("Price: %s", String.valueOf(carModel.getPrice())));
        descriptionList.add(String.format("Seats: %s", String.valueOf(carModel.getSeats())));
        descriptionList.add(String.format("Torque ft-lb: %s", String.valueOf(carModel.getTorque())));
        descriptionList.add(String.format("Doors: %s", String.valueOf(carModel.getDoors())));
        descriptionList.add(String.format("Engine: %s", String.valueOf(carModel.getEngine())));
        descriptionList.add(String.format("Horsepower: %s", String.valueOf(carModel.getHorsePower())));
        descriptionList.add(String.format("Categories: %s", String.valueOf(carModel.getCategory())));
        descriptionList.add(String.format("MPG: %s", String.valueOf(carModel.getMPG())));
        descriptionList.add(String.format("Drivetrain: %s", String.valueOf(carModel.getDrivetrain())));
        descriptionList.add(String.format("Cylinders: %s", String.valueOf(carModel.getCylinders())));

        //Setting the List Adapters to display the vehicle information.
        commonProblemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getCommonProblems());
        recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getRecalls());
        descriptionListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, descriptionList);

        // Displaying the Lists on the respective ListView
        lvCommonProblems.setAdapter(commonProblemsAdapter);
        lvRecalls.setAdapter(recallsAdapter);
        lvDescription.setAdapter(descriptionListAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compare_cars_a, container, false);
    }
}