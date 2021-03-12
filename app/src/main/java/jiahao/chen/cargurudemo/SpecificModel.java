package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import java.text.Format;

public class SpecificModel extends AppCompatActivity {
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
    ListView lvCommonProblems;
    ListView lvRecalls;
    Slider slCost;
    ArrayAdapter commonProblemsAdapter;
    ArrayAdapter recallsAdapter;

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
        lvCommonProblems = findViewById(R.id.lvSpecificCarCommonProblemList);
        lvRecalls = findViewById(R.id.lvSpecificCarRecallsList);

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
    private void populateCarDetails(){
        //Get CarModel Object from the Specific Car Page
        CarModel carModel = (CarModel) this.getIntent().getSerializableExtra("CarModel");
        //debug
        Log.d("SpecificModel", "CarModel is" + carModel.getMake());
        Log.d("SpecificModel", "CarModel is" + carModel.getModel());
        //Setting the Model information into the View
        tvCarName.setText(String.format("%s %s %s",carModel.getMake(),carModel.getModel(), carModel.getYear() ));
        tvDescriptionText.setText(carModel.getDescription());
        //ivCarImage.getCarImage();
//        ivResult1.getResult1();
//        ivResult2.getResult2();
//        ivResult3.getResult3();
//        ivResult4.getResult4();
        //ivCompareCars.setImageResource();
        commonProblemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getCommonProblems());
        recallsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, carModel.getRecalls());
        lvCommonProblems.setAdapter(commonProblemsAdapter);
        lvRecalls.setAdapter(recallsAdapter);

    }

    private View.OnClickListener onClickCompareCars= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener onClickCalculateAproxCost= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener onPopulateRatingFields= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
