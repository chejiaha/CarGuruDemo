package owen.ross.carguru.UI.Garage.MyGarage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import owen.ross.carguru.R;
import owen.ross.carguru.UI.Garage.UserAuthentication.UserLoginFragment;
import owen.ross.carguru.UI.Garage.UserAuthentication.UserRegistrationFragment;


public class GarageSpashFragment extends Fragment {


    public GarageSpashFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    Button btnSignIn;
    Button btnSignUp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_garage_spash, container, false);

        // Get the buttons
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(onClickLogin);
        btnSignUp.setOnClickListener(onClickRegistration);


        // Inflate the layout for this fragment
        return view;
    }

    // To login
    public View.OnClickListener  onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Going from SearchCarFragment to Specific model fragment
            Fragment login = new UserLoginFragment();
            switchFragments(login, R.id.nav_host_fragment, new Bundle());

        }
    };

    // To register
    public View.OnClickListener  onClickRegistration = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Going from SearchCarFragment to Specific model fragment
            Fragment register = new UserRegistrationFragment();
            switchFragments(register, R.id.nav_host_fragment, new Bundle());

        }
    };

    //TODO Move this method out of this class.
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


}