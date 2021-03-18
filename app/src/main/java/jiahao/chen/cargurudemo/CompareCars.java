package jiahao.chen.cargurudemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class CompareCars extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_car_page);
        //Setting the context
        context = getApplicationContext();
        //Pairing the View Objects with the respected View
        tvCompareCars = findViewById(R.id.tvChooseCarsToCompare);
        tvCarName = findViewById(R.id.tvSpecificCarTitle);
        tvDescriptionText = findViewById(R.id.tvSpecificCarDescriptionText);
        tvAproximateCost = findViewById(R.id.tvspecificCarApproxCost);
        ivCompareCars = findViewById(R.id.ivChooseCarsToCompare);
        ivCarImage = findViewById(R.id.ivSpecificCarPic);
        //ivOtherCarImages = findViewById(R.id.tvChooseCarsToCompare);
        ivResult1 = findViewById(R.id.ivSpecificCarGasRating);
        ivResult2 = findViewById(R.id.ivSpecificCarCosts);
        ivResult3 = findViewById(R.id.ivSpecificCarSpeed);
        ivResult4 = findViewById(R.id.ivSpecificCarWildcard);
        tvResult1 = findViewById(R.id.tvSpecificCarGasRating);
        tvResult2 = findViewById(R.id.tvSpecificCarCosts);
        tvResult3 = findViewById(R.id.tvSpecificCarSpeed);
        tvResult4 = findViewById(R.id.tvSpecificCarWildcard);
        lvCommonProblems = findViewById(R.id.lvSpecificCarCommonProblemList);
        lvRecalls = findViewById(R.id.lvSpecificCarRecallsList);
        lvDescription = findViewById(R.id.lvSpecificCarDescriptionList);
        //Get the vehicle details and populate arrays
        populateCarDetails();

    }

    /*
     * This class is used to populate the fields with the information from the previous slide,
     * and add it into the next slides.
     * This method will get the
     * NameOfTheCar, The Description, Make, Model, Year, AvrgCostOnMarket, carImage
     *
     * Returns None
     */
    private void populateCarDetails() {
        //Get CarModel Object from the Specific Car Page
        CarModel carModel = (CarModel) this.getIntent().getSerializableExtra("CarModel");
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
}
