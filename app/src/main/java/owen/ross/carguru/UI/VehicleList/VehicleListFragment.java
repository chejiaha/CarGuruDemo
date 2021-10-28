package owen.ross.carguru.UI.VehicleList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.R;


public class VehicleListFragment extends Fragment {


    public VehicleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    TextView tvFragmentListTitle;
    ListView lvList1;
    //RecyclerView rvFragmentListCars;
    // Creating a new array list

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        //Setting the view up
        tvFragmentListTitle = view.findViewById(R.id.tvFragmentListTitle);
       // rvFragmentListCars= view.findViewById(R.id.rvFragmentListCars);
        lvList1 = view.findViewById(R.id.lvList1);

        //Get the category and List of cars from the previous page.
        String title = (getArguments().getString("title"));
        ArrayList<String> stringList = (getArguments().getStringArrayList("carList"));

        // Setting the title
        tvFragmentListTitle.setText(title);

        //TODO
        //Get each cars details and store it as a vehicle arrayList
        //ArrayList<Car> vehicleList = getAllCars(stringList)


        //TODO Create a recycleviewCard
        //Populate the recycleview with the list of cars.

        // setting the array adapter to the listview
        ArrayAdapter stringArrayAdaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
        lvList1.setAdapter(stringArrayAdaptor);

        // Inflate the layout for this fragment
        return view;
    }
}