package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/*
 * This Class is used to create
 */
public class BottomNavigationFragment extends Fragment {
    //Creating Context Object
    Context context;
    //Creating View Objects
    ImageView ivHomeNavigation;
    ImageView ivGuruFindACar;
    ImageView ivSearchByModel;
    ImageView ivMyGarage;
    ImageView ivSettings;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
        //Setting the objects based on the view
        ivHomeNavigation = view.findViewById(R.id.ivFooterHome);
        ivGuruFindACar = view.findViewById(R.id.ivFooterFindMeACar);
        ivSearchByModel = view.findViewById(R.id.ivFooterSearchForCar);
        ivMyGarage= view.findViewById(R.id.ivFooterMyGarage);
        ivSettings = view.findViewById(R.id.ivFooterSettings);

        return view;
    }



}