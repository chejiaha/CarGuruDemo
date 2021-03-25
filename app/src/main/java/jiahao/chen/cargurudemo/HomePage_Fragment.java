package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomePage_Fragment extends Fragment {


    public HomePage_Fragment() {
        // Required empty public constructor
    }

    ImageView ivFindMeACar;
    ImageView ivSearchForCar;
    ImageView ivBrowseByCategory;
    ImageView ivNews;
    TextView tvFindMeACar;
    TextView tvSearchForCar;
    TextView tvBrowseByCategory;
    TextView tvNews;
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creating context
        Context context = getActivity();
        //Getting the view
        view = inflater.inflate(R.layout.activity_homepage, container, false);
        //
        ivFindMeACar = (ImageView) view.findViewById(R.id.ivFindACarForYou);
        ivSearchForCar = (ImageView) view.findViewById(R.id.ivFindSpecificModel);
        ivBrowseByCategory = (ImageView) view.findViewById(R.id.ivBrowseByCategory);
        ivNews = (ImageView) view.findViewById(R.id.ivCarNews);
        tvFindMeACar = (TextView) view.findViewById(R.id.tvFindACarForYou);
        tvSearchForCar = (TextView) view.findViewById(R.id.tvFindSpecificModel);
        tvBrowseByCategory = (TextView) view.findViewById(R.id.tvBrowseByCategory);
        tvNews = (TextView) view.findViewById(R.id.tvCarNews);

        //Setting the onclick Listeners
        tvFindMeACar.setOnClickListener(onClickFindMeACar);
        tvSearchForCar.setOnClickListener(onClickToSpecificModel);
        tvBrowseByCategory.setOnClickListener(onClickToCategories);
        tvNews.setOnClickListener(onClickToNews);
        ivFindMeACar.setOnClickListener(onClickFindMeACar);
        ivSearchForCar.setOnClickListener(onClickToSpecificModel);
        ivBrowseByCategory.setOnClickListener(onClickToCategories);
        ivNews.setOnClickListener(onClickToNews);

        return view;
    }

    // To Find me a car
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Going from SearchCarFragment to Specific model fragment
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_questionnaire_splashPage2);
/*            Intent intent = new Intent(getActivity(), FindSpecificModel.class);
            startActivity(intent);*/
        }
    };
    // Find a specific Model
    public View.OnClickListener  onClickToSpecificModel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_SearchCar);

/*            Intent intent = new Intent(getActivity(), FindSpecificModel.class);
            startActivity(intent);*/
        }
    };

    // Browse By Category Link
    public View.OnClickListener  onClickToCategories = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_searchByCategory);
        }
    };

    // Car News Link
    public View.OnClickListener  onClickToNews = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /*Intent intent = new Intent(getActivity(), NewsPage.class);
            startActivity(intent);*/
        }
    };

    //Settings Link
    public View.OnClickListener onClickToSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_SearchCar);
            /*Intent intent = new Intent(getActivity(), MyGaragePage.class);
            startActivity(intent);*/
        }
    };
}
