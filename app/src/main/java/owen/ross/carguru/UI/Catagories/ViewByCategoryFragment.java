package owen.ross.carguru.UI.Catagories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireFragment;


public class ViewByCategoryFragment extends Fragment {


    public ViewByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ImageView ivChooseCommuter;
    ImageView ivChooseSport ;
    ImageView ivChooseLuxury;
    ImageView ivChooseFamily ;
    ImageView ivChooseUtility;
    ImageView ivChooseBeater;
    ArrayList<Car> vehicleList;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout. fragment_view_by_category, container, false);
        // initialize the arraylist
        vehicleList = new ArrayList<>();

        // setting the values of the UI controls for this Activity
        ivChooseCommuter = view.findViewById(R.id.ivChooseCommuter);
        ivChooseCommuter.setOnClickListener(onClickFindMeACar);

        ivChooseSport = view.findViewById(R.id.ivChooseSports);
        ivChooseSport.setOnClickListener(onClickFindMeACar);

        ivChooseLuxury = view.findViewById(R.id.ivChooseLuxury);
        ivChooseLuxury.setOnClickListener(onClickFindMeACar);

        ivChooseFamily = view.findViewById(R.id.ivChooseFamily);
        ivChooseFamily.setOnClickListener(onClickFindMeACar);

        ivChooseUtility = view.findViewById(R.id.ivChooseUtility);
        ivChooseUtility.setOnClickListener(onClickFindMeACar);

        ivChooseBeater = view.findViewById(R.id.ivChooseBeater);
        ivChooseBeater.setOnClickListener(onClickFindMeACar);

        //Setting up the listeners

        // Inflate the layout for this fragment
        return view;
    }



    // Listener to handle onclick function when user selects a category
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // initialize
            String category = "";
            int id = v.getId();
            // if user clicks on imageview or textview of the corresponding category
            if (id == R.id.ivChooseCommuter || id == R.id.tvCommuter) {
                category = "Commuter";
            } else if (id == R.id.ivChooseLuxury || id == R.id.tvLuxury) {
                category = "Luxury";
            } else if (id == R.id.ivChooseFamily || id == R.id.tvHatchFamily) {
                category = "Family";
            } else if (id == R.id.ivChooseBeater || id == R.id.tvBeater) {
                category = "Beater";
            } else if (id == R.id.ivChooseUtility || id == R.id.tvUtility) {
                category = "Utility";
            } else if (id == R.id.ivChooseSports || id == R.id.tvSports) {
                category = "Sport";
            }
            // Adding the arguments into the bundle
            Bundle bundle = new Bundle();
            // Adding the question number that will specify the question from the list
            bundle.putString("Category", category);
            // Telling it what Fragment you want to replace it with.
            Fragment fragment = new ListOfCarsBasedOnCategory();
//                fragment.setArguments(bundle);
            //TODO This can also Be a function.
            //TODO functionName(Fragment FragmentName, Bundle bundle, int IdOfNavHostUI**Optional)

            vehicleList = VehicleDatabase.GetAllCarsInCategory(category, new VehicleFirebaseCallback() {
                @Override
                public void onCallbackCarList(ArrayList<Car> list) {
                    // checking if the questions linkedlist is already populated with questions
                    if (vehicleList.isEmpty()) {
                        // the questions returned from the database will be added to the list if the list is empty
                        vehicleList = list;
                    }
                }

                @Override
                public void onCallbackStringArrayList(ArrayList<String> cars) {

                }

                @Override
                public void onCallbackCar(Car car) {

                }
            });


//                bundle.putStringArrayList("listOfCars", vehicleList);
            bundle.putSerializable("carsList", vehicleList);

            fragment.setArguments(bundle);
            // create a FragmentManager
            FragmentManager fm = getFragmentManager();
            // create a FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            // replace the FrameLayout with new Fragment
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.commit(); // save the changes
        }
    };


}