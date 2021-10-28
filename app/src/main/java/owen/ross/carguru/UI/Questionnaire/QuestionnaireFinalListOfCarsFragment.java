package owen.ross.carguru.UI.Questionnaire;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.MainActivity;
import owen.ross.carguru.R;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.Adaptors.CustomAdapter;
import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.UI.FindSpecificModel.SpecificVehicleInfoFragment;

public class QuestionnaireFinalListOfCarsFragment extends Fragment{

    View view;
    // Recycler View containing a list of items.
    RecyclerView rvVehicleList;

    public QuestionnaireFinalListOfCarsFragment() {
        // Required empty public constructor
    }
    public static QuestionnaireFinalListOfCarsFragment newInstance(String param1, String param2) {
        QuestionnaireFinalListOfCarsFragment fragment = new QuestionnaireFinalListOfCarsFragment();
        return fragment;
    }
    ArrayList<Car> vehicleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_final_list_of_cars, container, false);
        rvVehicleList = view.findViewById(R.id.rvVehicleList);
        Bundle bundle = new Bundle();
        vehicleList = (ArrayList<Car>) bundle.getSerializable("listOfCars");
        vehicleList = ((ArrayList<Car>) getArguments().getSerializable("listOfCars"));
        CustomAdapter customAdapter = new CustomAdapter(vehicleList);
        rvVehicleList.setAdapter(customAdapter);
        rvVehicleList.setLayoutManager(new LinearLayoutManager(getActivity()));

        // If the item is clicked get the title of the car and split it into make, model, trim, and year
//        rvVehicleList.setOnClickListener(onClickSearchVehicle);


        return view;
    }


    /*
     * This Method is activated when the user clicks the next question button.
     * This question will get the checked checkboxes value and depending on if its the Main Question
     * Category or a Specific Question Category will act accordingly
     *
     * Main Question
     *  Will calculate the highest category and send it to the next page
     *
     * Specific Category Question
     *  Will use the Category Answer Parser in the AnswerParser Class to parse the answers.
     */
//    public View.OnClickListener onClickSearchVehicle = new CustomAdapter(vehicleList) {
//        Car car = new Car();
//        @Override
//        public void onClick(View v, int position) {
//            String item = vehicleList.get(position);
//            String[] carInfo = item.split(" ");
//            Car car = new Car();
//            //Setting the Make, Year, Model in the object
//            car.setMake(carInfo[0]);
//            car.setModel(carInfo[1]);
//            car.setTrim(carInfo[2]);
//            //car.setYear(Integer.parseInt(carInfo[3]));
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("car", (Serializable) car);
//            Fragment frag = new SpecificVehicleInfoFragment();
//            // Create a FragmentManager
//            FragmentManager fm = getFragmentManager();
//            // Create a FragmentTransaction to begin the transaction and replace the Fragment
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
//            fragmentTransaction.replace(R.id.nav_host_fragment, frag);
//            fragmentTransaction.commit(); // save the changes
//
//        }
//        @Override
//        public void onClick(View v) {
//            Log.d("onClickSearchVehicle", "I WAS CLICKED");
//
//            TextView item = v.findViewById(R.id.tvCarItemTitle);
//
//
//            Toast.makeText(v.getContext(),item.getText() , Toast.LENGTH_SHORT).show();
//            // Find what was vehicle was clicked.
//            int itemPosition = rvVehicleList.getChildLayoutPosition(view);
//            // Get the vehicle information from the vehicle list
//            String item = vehicleList.get(itemPosition);
//            // break the information into each part
//            String[] carInfo = item.split(" ");
////            Car car = new Car();
//            car = new Car();
//            //Setting the Make, Year, Model in the object
//            car.setMake(carInfo[0]);
//            car.setModel(carInfo[2]);
//            car.setTrim(carInfo[3]);
//            car.setYear(Integer.parseInt(carInfo[4]));
//            //TODO Reference the database
//            car = VehicleDatabase.getSpecificCarInfo(car, new VehicleFirebaseCallback() {
//                @Override
//                public void onCallbackCarList(ArrayList<Car> vehicleList) {
//                }
//
//                @Override
//                public void onCallbackStringArrayList(ArrayList<String> cars) {
//                }
//
//                @Override
//                public void onCallbackCar(Car dbCar) {
//                    // checking if the questions linkedlist is already populated with questions
//                    if (car.getCategory() == null) {
//                        // the questions returned from the database will be added to the list if the list is empty
//                        car = dbCar;
//                    }
//                }
//            });
//            // Create a FragmentManager
//            FragmentManager fm = getFragmentManager();
//            // Create a FragmentTransaction to begin the transaction and replace the Fragment
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
//            fragmentTransaction.replace(idOfNavHostUI, fragmentName);
//            fragmentTransaction.commit(); // save the changes
//            //NextSend the model to the next page
//            //Adding the arguments into the class
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("car", (Serializable) car);
//            //Going from SearchCarFragment to Specific model fragment
//            Fragment SpecificCarInformation = new SpecificVehicleInfoFragment();
//            switchFragments(SpecificCarInformation, R.id.nav_host_fragment, bundle);
//        }
//    };

    //TODO Move this method into Helper Methods.
    public void switchFragments (Fragment fragmentName,  int idOfNavHostUI, Bundle bundle){
        //If the bundle is not empty add the argument
        if (bundle.isEmpty() == false){
            fragmentName.setArguments(bundle);
        }
        // If idOfNavHostUI is null, then set it to the navigation_host_fragment
        idOfNavHostUI = idOfNavHostUI != 0 ? idOfNavHostUI : R.id.nav_host_fragment;

        // Create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
        fragmentTransaction.replace(idOfNavHostUI, fragmentName);
        fragmentTransaction.commit(); // save the changes
    }


//    @Override
//    public void onClick(View v, int position) {
//        String item = vehicleList.get(position);
//        String[] carInfo = item.split(" ");
//        Car car = new Car();
//        //Setting the Make, Year, Model in the object
//        car.setMake(carInfo[0]);
//        car.setModel(carInfo[1]);
//        car.setTrim(carInfo[2]);
//        //car.setYear(Integer.parseInt(carInfo[3]));
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("car", (Serializable) car);
//        Fragment frag = new SpecificVehicleInfoFragment();
//        // Create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//        // Create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
//        fragmentTransaction.replace(R.id.nav_host_fragment, frag);
//        fragmentTransaction.commit(); // save the changes
//
//    }
}