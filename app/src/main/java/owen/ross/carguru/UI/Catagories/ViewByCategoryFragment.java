package owen.ross.carguru.UI.Catagories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;


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
    ImageView ivChooseSedan;
    ImageView ivChooseCoupe ;
    ImageView ivChooseSUV;
    ImageView ivChooseMinivan;
    ImageView ivChooseTruck;

    TextView tvChooseBeater;
    TextView tvChooseCommuter;
    TextView tvChooseFamily;
    TextView tvChooseSport;
    TextView tvChooseUtility;
    TextView tvChooseLuxury;
    TextView tvChooseSedan;
    TextView tvChooseMinivan;
    TextView tvChooseCoupe;
    TextView tvChooseTruck;
    TextView tvChooseSUV;


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
        ivChooseCommuter.setOnClickListener(onClickFindMeACarCategory);

        ivChooseSport = view.findViewById(R.id.ivChooseSports);
        ivChooseSport.setOnClickListener(onClickFindMeACarCategory);

        ivChooseLuxury = view.findViewById(R.id.ivChooseLuxury);
        ivChooseLuxury.setOnClickListener(onClickFindMeACarCategory);

        ivChooseFamily = view.findViewById(R.id.ivChooseFamily);
        ivChooseFamily.setOnClickListener(onClickFindMeACarCategory);

        ivChooseUtility = view.findViewById(R.id.ivChooseUtility);
        ivChooseUtility.setOnClickListener(onClickFindMeACarCategory);

        ivChooseBeater = view.findViewById(R.id.ivChooseBeater);
        ivChooseBeater.setOnClickListener(onClickFindMeACarCategory);

        ivChooseSedan = view.findViewById(R.id.ivChooseSedan);
        ivChooseSedan.setOnClickListener(onClickFindMeACarBodyType);

        ivChooseCoupe = view.findViewById(R.id.ivChooseCoupe);
        ivChooseCoupe.setOnClickListener(onClickFindMeACarBodyType);

        ivChooseSUV = view.findViewById(R.id.ivChooseSUV);
        ivChooseSUV.setOnClickListener(onClickFindMeACarBodyType);

        ivChooseMinivan = view.findViewById(R.id.ivChooseMinivan);
        ivChooseMinivan.setOnClickListener(onClickFindMeACarBodyType);

        ivChooseTruck = view.findViewById(R.id.ivChooseTruck);
        ivChooseTruck.setOnClickListener(onClickFindMeACarBodyType);


        tvChooseCommuter = view.findViewById(R.id.tvChooseCommuter);
        tvChooseCommuter.setOnClickListener(onClickFindMeACarCategory);

        tvChooseSport = view.findViewById(R.id.tvChooseSports);
        tvChooseSport.setOnClickListener(onClickFindMeACarCategory);

        tvChooseLuxury = view.findViewById(R.id.tvChooseLuxury);
        tvChooseLuxury.setOnClickListener(onClickFindMeACarCategory);

        tvChooseFamily = view.findViewById(R.id.tvChooseFamily);
        tvChooseFamily.setOnClickListener(onClickFindMeACarCategory);

        tvChooseUtility = view.findViewById(R.id.tvChooseUtility);
        tvChooseUtility.setOnClickListener(onClickFindMeACarCategory);

        tvChooseBeater = view.findViewById(R.id.tvChooseBeater);
        tvChooseBeater.setOnClickListener(onClickFindMeACarCategory);

        tvChooseSedan = view.findViewById(R.id.tvChooseSedan);
        tvChooseSedan.setOnClickListener(onClickFindMeACarBodyType);

        tvChooseCoupe = view.findViewById(R.id.tvChooseCoupe);
        tvChooseCoupe.setOnClickListener(onClickFindMeACarBodyType);

        tvChooseSUV = view.findViewById(R.id.tvChooseSUV);
        tvChooseSUV.setOnClickListener(onClickFindMeACarBodyType);

        tvChooseMinivan = view.findViewById(R.id.tvChooseMinivan);
        tvChooseMinivan.setOnClickListener(onClickFindMeACarBodyType);

        tvChooseTruck = view.findViewById(R.id.tvChooseTruck);
        tvChooseTruck.setOnClickListener(onClickFindMeACarBodyType);


        //Setting up the listeners

        // Inflate the layout for this fragment
        return view;
    }



    // Listener to handle onclick function when user selects a category
    public View.OnClickListener onClickFindMeACarCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // initialize
            String category = "";
            String bodyType = "";
            int id = v.getId();
            // if user clicks on imageview or textview of the corresponding category
            if (id == R.id.ivChooseCommuter || id == R.id.tvChooseCommuter) {
                category = "Commuter";
            } else if (id == R.id.ivChooseLuxury || id == R.id.tvChooseLuxury) {
                category = "Luxury";
            } else if (id == R.id.ivChooseFamily || id == R.id.tvChooseFamily) {
                category = "Family";
            } else if (id == R.id.ivChooseBeater || id == R.id.tvChooseBeater) {
                category = "Beater";
            } else if (id == R.id.ivChooseUtility || id == R.id.tvChooseUtility) {
                category = "Utility";
            } else if (id == R.id.ivChooseSports || id == R.id.tvChooseSports) {
                category = "Sport";
            }

            // Adding the arguments into the bundle
            Bundle bundle = new Bundle();
//            if (!category.isEmpty()){
//                // Adding the question number that will specify the question from the list
//                bundle.putString("Category", category);
//            }else if (!bodyType.isEmpty()){
//                // Adding the question number that will specify the question from the list
//                bundle.putString("BodyType", bodyType);
//            }

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

    // Listener to handle onclick function when user selects a category
    public View.OnClickListener onClickFindMeACarBodyType = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // initialize
            String bodyType = "";
            int id = v.getId();
            // if user clicks on imageview or textview of the corresponding category
            if (id == R.id.ivChooseSUV || id == R.id.tvChooseSUV) {
                bodyType = "SUV";
            } else if (id == R.id.ivChooseCoupe || id == R.id.tvChooseCoupe) {
                bodyType = "Coupe";
            } else if (id == R.id.ivChooseTruck || id == R.id.tvChooseTruck) {
                bodyType = "Truck";
            } else if (id == R.id.ivChooseSedan || id == R.id.tvChooseSedan) {
                bodyType = "Sedan";
            }else if (id == R.id.ivChooseMinivan || id == R.id.tvChooseMinivan) {
                bodyType = "Minivan";
            }
            // Adding the arguments into the bundle
            Bundle bundle = new Bundle();
            // Adding the question number that will specify the question from the list
            bundle.putString("BodyType", bodyType);
            // Telling it what Fragment you want to replace it with.
            Fragment fragment = new ListOfCarsBasedOnCategory();
//                fragment.setArguments(bundle);
            //TODO This can also Be a function.
            //TODO functionName(Fragment FragmentName, Bundle bundle, int IdOfNavHostUI**Optional)

            vehicleList = VehicleDatabase.GetAllCarsFromBodyType(bodyType, new VehicleFirebaseCallback() {
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