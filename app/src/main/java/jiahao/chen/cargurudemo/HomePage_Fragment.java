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
        View theView = inflater.inflate(R.layout.activity_homepage, container, false);
        //
        ivFindMeACar = (ImageView) theView.findViewById(R.id.ivFindACarForYou);
        ivSearchForCar = (ImageView) theView.findViewById(R.id.ivFindSpecificModel);
        ivBrowseByCategory = (ImageView) theView.findViewById(R.id.ivBrowseByCategory);
        ivNews = (ImageView) theView.findViewById(R.id.ivCarNews);
        tvFindMeACar = (TextView) theView.findViewById(R.id.tvFindACarForYou);
        tvSearchForCar = (TextView) theView.findViewById(R.id.tvFindSpecificModel);
        tvBrowseByCategory = (TextView) theView.findViewById(R.id.tvBrowseByCategory);
        tvNews = (TextView) theView.findViewById(R.id.tvCarNews);

        //Setting the onclick Listeners
        tvFindMeACar.setOnClickListener(onClickFindMeACar);
        tvSearchForCar.setOnClickListener(onClickToSpecificModel);
        tvBrowseByCategory.setOnClickListener(onClickToCategories);
        tvNews.setOnClickListener(onClickToNews);
        tvFindMeACar.setOnClickListener(onClickFindMeACar);
        tvSearchForCar.setOnClickListener(onClickToSpecificModel);
        tvBrowseByCategory.setOnClickListener(onClickToCategories);
        tvNews.setOnClickListener(onClickToNews);

        return theView;
    }

    // To Find me a car
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

/*            Intent intent = new Intent(getActivity(), FindSpecificModel.class);
            startActivity(intent);*/
        }
    };
    // Find a specific Model
    public View.OnClickListener  onClickToSpecificModel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

/*            Intent intent = new Intent(getActivity(), FindSpecificModel.class);
            startActivity(intent);*/
        }
    };

    // Browse By Category Link
    public View.OnClickListener  onClickToCategories = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           /* Intent intent = new Intent(getActivity(), BrowseByCategory.class);
            startActivity(intent);*/
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
            /*Intent intent = new Intent(getActivity(), MyGaragePage.class);
            startActivity(intent);*/
        }
    };
}
