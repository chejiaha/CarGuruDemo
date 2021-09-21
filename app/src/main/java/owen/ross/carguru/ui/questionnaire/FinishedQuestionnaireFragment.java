package owen.ross.carguru.ui.questionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        //Function called to change the features based on Questionnaire Fragments response
        /*
         * Questionnaire fragment response includes
         * ArrayList <Cars> carsPassed : a list of cars for the user
         * String category : The category that the user was placed into
         *
         */
        String questionCategory = (getArguments().getString("category"));
        //ArrayList<String> vehicleList = (getArguments().getStringArrayList("allCars"));

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
}