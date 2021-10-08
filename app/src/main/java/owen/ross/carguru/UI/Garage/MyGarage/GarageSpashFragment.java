package owen.ross.carguru.UI.Garage.MyGarage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import owen.ross.carguru.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GarageSpashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GarageSpashFragment extends Fragment {


    public GarageSpashFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garage_spash, container, false);
    }
}