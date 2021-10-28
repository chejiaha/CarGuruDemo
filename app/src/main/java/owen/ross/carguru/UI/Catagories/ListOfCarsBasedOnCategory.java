package owen.ross.carguru.UI.Catagories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import owen.ross.carguru.Adaptors.CustomAdapter;
import owen.ross.carguru.MainActivity;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireFinalListOfCarsFragment;

public class ListOfCarsBasedOnCategory extends Fragment {


    View view;
    RecyclerView rvVehicleList;
    ArrayList<Car> vehicleList;

    public ListOfCarsBasedOnCategory() {
        // Required empty public constructor
    }

    public static ListOfCarsBasedOnCategory newInstance(String param1, String param2) {
        ListOfCarsBasedOnCategory fragment = new ListOfCarsBasedOnCategory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the category and display it on the screen.
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_cars_based_on_category, container, false);
        rvVehicleList = view.findViewById(R.id.rvVehicleListCategory);
        // set vehiclelist as array of car objects
        vehicleList = (ArrayList<Car>) getArguments().getSerializable("carsList");

//        MainActivity mainActivity = (MainActivity) getActivity();
        //CustomAdapter customAdapter = new CustomAdapter(vehicleList, mainActivity );
        CustomAdapter customAdapter = new CustomAdapter(vehicleList);
        rvVehicleList.setAdapter(customAdapter);
        //rvVehicleList.setOnClickListener(onClickSearchVehicle);
        rvVehicleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //return inflater.inflate(R.layout.fragment_list_of_cars_based_on_category, container, false);
        return view;
    }
}