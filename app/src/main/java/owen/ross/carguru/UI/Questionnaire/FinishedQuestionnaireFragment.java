package owen.ross.carguru.UI.Questionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;


public class FinishedQuestionnaireFragment extends Fragment {


    public FinishedQuestionnaireFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    TextView tvQuestionnaireCategoryTitle;
    TextView tvQuestionnaireCategoryExplanation;
    TextView tvQuestionnaireTryAgain;
    TextView tvQuestionnaireToCars;
    ArrayList<Car> vehicleList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_finished_questionnaire, container, false);
        // The title for the category the user was put into
        tvQuestionnaireCategoryTitle = view.findViewById(R.id.tvQuestionnaireCategoryTitle);
        // The explination that the user is given as to why they were put in that category
        tvQuestionnaireCategoryExplanation = view.findViewById(R.id.tvQuestionnaireCategoryExplanation);
        // Try Questionnaire again button
        tvQuestionnaireTryAgain = view.findViewById(R.id.tvQuestionnaireTryAgain);
        // See results button
        tvQuestionnaireToCars = view.findViewById(R.id.tvQuestionnaireToCars);

        //Setting the onClick Listeners
        tvQuestionnaireToCars.setOnClickListener(onClickSuggestedCars);
        tvQuestionnaireTryAgain.setOnClickListener(onClickFindMeACar);

        //Function called to change the features based on Questionnaire Fragments response
        /*
         * Questionnaire fragment response includes
         * ArrayList <Cars> carsPassed : a list of cars for the user
         * String category : The category that the user was placed into
         *
         */
//        String questionCategory = (getArguments().getString("category"));
//        vehicleList = (getArguments().getStringArrayList("listOfCars"));
        String questionCategory = (getArguments().getString("category"));
        Bundle bundle = new Bundle();
        vehicleList = (ArrayList<Car>) bundle.getSerializable("listOfCars");
        vehicleList = ((ArrayList<Car>) getArguments().getSerializable("listOfCars"));

        // Setting the title
        tvQuestionnaireCategoryTitle.setText(questionCategory);

        // Setting up the sentence describing the decision of the guru [Hard coded right now]
        if (questionCategory.equals("Sports")){
            tvQuestionnaireCategoryExplanation.setText("The Guru decided that your answers all pointed to a nice quick and gas guzzling vehicle. You can enjoy a nice interior and a quick and bumpy ride!");
        }else if (questionCategory.contains("Commuter")) {
            tvQuestionnaireCategoryExplanation.setText("The Guru decided You are an average guy/gal that enjoys a nice ride in a car that wont cost alot to maintain and repair. You enjoy nice calm drives that mainly involve common commutes");
        } else if (questionCategory.contains("Utility")) {
            tvQuestionnaireCategoryExplanation.setText("The Guru Decided that You are a hands on person who enjoys working with tools and enjoy keeping yourself busy. You are a hard working individual who enjoys a large space to fit all different types of materials !");
        }else if (questionCategory.contains("Luxury")) {
            tvQuestionnaireCategoryExplanation.setText("The Guru Decided that You enjoy taking a nice expensive car on long cruises along the coast. You enjoy spending time alone and don't have time to spend, but have lots of money to spend on a vehicle that turns heads whenever someone sees it.. ");
        }else if (questionCategory.contains("Beater")) {
            tvQuestionnaireCategoryExplanation.setText("The Guru Decided that you are on a tighter budget and pay more attention to where you spend your money. You have tramendous value to your vehicle and will most likely drive the car until it dies.");
        }else if (questionCategory.contains("Family")) {
            tvQuestionnaireCategoryExplanation.setText("The Guru Decided that You are a family man, You need a car that will fit your whole family into it. You enjoy going on nice long drives to campsites and try to keep family first in your list.");
        }
        else {
            tvQuestionnaireCategoryExplanation.setText("There was no Category found Category: " + questionCategory);
        }


        // Inflate the layout for this fragment
        return view;
    }

    public View.OnClickListener onClickSuggestedCars = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //Send the user to the Finished Questionnaire Fragment to sift through the results
            Bundle bundle = new Bundle();
            // Adding the category with the highest Tally based on the questions they answered
            bundle.putSerializable("listOfCars", vehicleList);
            bundle.putString("title", "Suggested Vehicle List");
            Fragment fragment = new QuestionnaireFinalListOfCarsFragment();
            switchFragments(fragment, R.id.nav_host_fragment, bundle);
        }
    };

    // To Find me a car
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment findMeACar = new QuestionnaireFragment();
            switchFragments(findMeACar, R.id.nav_host_fragment, new Bundle());
        }
    };


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