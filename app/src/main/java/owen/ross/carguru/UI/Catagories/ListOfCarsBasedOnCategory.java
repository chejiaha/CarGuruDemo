package owen.ross.carguru.UI.Catagories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import owen.ross.carguru.R;

public class ListOfCarsBasedOnCategory extends Fragment {



    public ListOfCarsBasedOnCategory() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_list_of_cars_based_on_category, container, false);
    }
}