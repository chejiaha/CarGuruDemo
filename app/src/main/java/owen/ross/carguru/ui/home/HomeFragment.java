package owen.ross.carguru.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import owen.ross.carguru.R;
import owen.ross.carguru.ui.Catagories.ViewByCategoryFragment;
import owen.ross.carguru.ui.FindSpecificModel.FindSpecificModelFragment;
import owen.ross.carguru.ui.UserAuthentication.UserLoginFragment;
import owen.ross.carguru.ui.UserAuthentication.UserRegistrationFragment;
import owen.ross.carguru.ui.questionnaire.QuestionnaireFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ImageView ivFindACarForYou;
    ImageView ivFindSpecificModel;
    ImageView ivBrowseByCategory;
    ImageView ivCarNews;
    TextView tvFindMeACar;
    TextView tvSearchForCar;
    TextView tvBrowseByCategory;
    TextView tvCarNews;
    Button btnLogin;
    Button btnRegister;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        //Pairing the Text and Image to the Fragment
        ivFindACarForYou = (ImageView) view.findViewById(R.id.ivFindACarForYou);
        ivFindSpecificModel = (ImageView) view.findViewById(R.id.ivFindSpecificModel);
        ivBrowseByCategory = (ImageView) view.findViewById(R.id.ivBrowseByCategory);
        ivCarNews = (ImageView) view.findViewById(R.id.ivCarNews);
        tvFindMeACar = (TextView) view.findViewById(R.id.tvFindACarForYou);
        tvSearchForCar = (TextView) view.findViewById(R.id.tvFindSpecificModel);
        tvBrowseByCategory = (TextView) view.findViewById(R.id.tvBrowseByCategory);
        tvCarNews = (TextView) view.findViewById(R.id.tvCarNews);

        btnLogin = (Button) view.findViewById(R.id.btnHompageLogin);
        btnRegister = (Button) view.findViewById(R.id.btnHompageRegister);

        //Setting the onclick Listeners
        tvFindMeACar.setOnClickListener(onClickFindMeACar);
        tvSearchForCar.setOnClickListener(onClickToSpecificModel);
        tvBrowseByCategory.setOnClickListener(onClickToCategories);
        tvCarNews.setOnClickListener(onClickToNews);
        //Image Listeneners
        ivFindACarForYou.setOnClickListener(onClickFindMeACar);
        ivFindSpecificModel.setOnClickListener(onClickToSpecificModel);
        ivBrowseByCategory.setOnClickListener(onClickToCategories);
        ivCarNews.setOnClickListener(onClickToNews);
        //Button Listeners
        btnLogin.setOnClickListener(onClickLogin);
        btnRegister.setOnClickListener(onClickRegistration);


        return view;
    }

    // To Find me a car
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Questionnaire fragment
            //Going from SearchCarFragment to Specific model fragment
            Fragment findMeACar = new QuestionnaireFragment();
            switchFragments(findMeACar, R.id.nav_host_fragment, new Bundle());

        }
    };
    // Find a specific Model
    public View.OnClickListener  onClickToSpecificModel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment findSpecificModelFragment = new FindSpecificModelFragment();
            switchFragments(findSpecificModelFragment, R.id.nav_host_fragment, new Bundle());

        }
    };

    // Browse By Category Link
    public View.OnClickListener  onClickToCategories = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Transfer Categories Fragment
            Fragment viewByCategoryFragment = new ViewByCategoryFragment();
            switchFragments(viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };

    // Car News Link
    public View.OnClickListener  onClickToNews = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Create News Fragment

            /*Intent intent = new Intent(getActivity(), NewsPage.class);
            startActivity(intent);*/
        }
    };

    //Settings Link
    public View.OnClickListener onClickToSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Create Settings Fragment
           /* Intent intent = new Intent(getActivity(), Settings.class);
            startActivity(intent);*/
        }
    };

    // Home Page Link
//    public View.OnClickListener  onClickToHomePage = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(getActivity(), FindACarForYou.class);
//            startActivity(intent);
//        }
//    };
    // To Garage Page
    public View.OnClickListener  onClickToMyGarage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Create Garage Fragment
            /*Intent intent = new Intent(getActivity(), MyGaragePage.class);
            startActivity(intent);*/
        }
    };

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