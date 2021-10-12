package owen.ross.carguru.UI.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import owen.ross.carguru.R;
import owen.ross.carguru.UI.Catagories.ViewByCategoryFragment;
import owen.ross.carguru.UI.FindSpecificModel.FindSpecificModelFragment;
import owen.ross.carguru.UI.Garage.MyGarage.GarageSpashFragment;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireFragment;
import owen.ross.carguru.UI.Settings.SettingFragment;

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
            // Send them to Settings Fragment
            Fragment viewByCategoryFragment = new SettingFragment();
            switchFragments(viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };

    // To Garage Page
    public View.OnClickListener  onClickToMyGarage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Send them to Garage Fragment
            Fragment viewByCategoryFragment = new GarageSpashFragment();
            switchFragments(viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
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