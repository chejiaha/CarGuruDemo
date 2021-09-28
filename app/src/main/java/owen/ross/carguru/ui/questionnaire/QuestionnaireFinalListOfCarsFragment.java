package owen.ross.carguru.ui.questionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import owen.ross.carguru.R;
import owen.ross.carguru.models.AnswerParser;
import owen.ross.carguru.models.Car;
import owen.ross.carguru.models.CustomAdapter;
import owen.ross.carguru.models.VehicleDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionnaireFinalListOfCarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireFinalListOfCarsFragment extends Fragment {

    View view;
    ListView listView;
    RecyclerView recyclerView;

    public QuestionnaireFinalListOfCarsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static QuestionnaireFinalListOfCarsFragment newInstance(String param1, String param2) {
        QuestionnaireFinalListOfCarsFragment fragment = new QuestionnaireFinalListOfCarsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_final_list_of_cars, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<String> vehicleList = (getArguments().getStringArrayList("listOfCars"));
        CustomAdapter customAdapter = new CustomAdapter(vehicleList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


}