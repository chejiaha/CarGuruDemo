package owen.ross.carguru.UI.home;

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

import owen.ross.carguru.Models.HelperFunctions;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.Articles.ArticleFragment;
import owen.ross.carguru.UI.Catagories.ViewByCategoryFragment;
import owen.ross.carguru.UI.FindSpecificModel.FindSpecificModelFragment;
import owen.ross.carguru.UI.Garage.MyGarage.GarageSpashFragment;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireFragment;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireSplashFragment;
import owen.ross.carguru.UI.Settings.SettingFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ImageView ivFindACarForYou;
    ImageView ivFindSpecificModel;
    ImageView ivBrowseByCategory;
    ImageView ivCarNews;
    Button tvFindMeACar;
    Button tvSearchForCar;
    Button tvBrowseByCategory;
    //Button tvCarNews;
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
//        ivFindACarForYou = (ImageView) view.findViewById(R.id.ivFindACarForYou);
//        ivFindSpecificModel = (ImageView) view.findViewById(R.id.ivFindSpecificModel);
//        ivBrowseByCategory = (ImageView) view.findViewById(R.id.ivBrowseByCategory);
//        ivCarNews = (ImageView) view.findViewById(R.id.ivCarNews);
        tvFindMeACar = (Button) view.findViewById(R.id.tvFindACarForYou);
        tvSearchForCar = (Button) view.findViewById(R.id.tvFindSpecificModel);
        tvBrowseByCategory = (Button) view.findViewById(R.id.tvBrowseByCategory);
        //tvCarNews = (Button) view.findViewById(R.id.tvCarNews);

        //Setting the onclick Listeners
        tvFindMeACar.setOnClickListener(onClickFindMeACar);
        tvSearchForCar.setOnClickListener(onClickToSpecificModel);
        tvBrowseByCategory.setOnClickListener(onClickToCategories);
        //tvCarNews.setOnClickListener(onClickToNews);
        //Image Listeneners
//        ivFindACarForYou.setOnClickListener(onClickFindMeACar);
//        ivFindSpecificModel.setOnClickListener(onClickToSpecificModel);
//        ivBrowseByCategory.setOnClickListener(onClickToCategories);
//        ivCarNews.setOnClickListener(onClickToNews);

        return view;
    }

    // To Find me a car
    public View.OnClickListener onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Questionnaire fragment
            //Going from SearchCarFragment to Specific model fragment
            Fragment findMeACar = new QuestionnaireSplashFragment();
            HelperFunctions.switchFragments(getActivity(), findMeACar, R.id.nav_host_fragment, new Bundle());
        }
    };
    // Find a specific Model
    public View.OnClickListener onClickToSpecificModel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment findSpecificModelFragment = new FindSpecificModelFragment();
            HelperFunctions.switchFragments(getActivity(), findSpecificModelFragment, R.id.nav_host_fragment, new Bundle());

        }
    };

    // Browse By Category Link
    public View.OnClickListener onClickToCategories = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment viewByCategoryFragment = new ViewByCategoryFragment();
            HelperFunctions.switchFragments(getActivity(), viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };

    // Car News Link
    public View.OnClickListener onClickToNews = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment articleFragment = new ArticleFragment();
            HelperFunctions.switchFragments(getActivity(), articleFragment, R.id.nav_host_fragment, new Bundle());
        }
    };

    //Settings Link
    public View.OnClickListener onClickToSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Send them to Settings Fragment
            Fragment viewByCategoryFragment = new SettingFragment();
            HelperFunctions.switchFragments(getActivity(), viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };

    // To Garage Page
    public View.OnClickListener onClickToMyGarage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Send them to Garage Fragment
            Fragment viewByCategoryFragment = new GarageSpashFragment();
            HelperFunctions.switchFragments(getActivity(), viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };



}