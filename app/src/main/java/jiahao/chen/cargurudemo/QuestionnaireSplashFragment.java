package jiahao.chen.cargurudemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class QuestionnaireSplashFragment extends Fragment {

    public QuestionnaireSplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    //Creating View Objects
    ImageView ivStartQuestionnaire;
    Button btnViewPreviousResults;
    Button btnSearchByCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_splash, container, false);

        //Creating context
        btnSearchByCategory = view.findViewById(R.id.btnViewByCategory);
        btnViewPreviousResults = view.findViewById(R.id.btnViewPreviousResults);
        ivStartQuestionnaire = view.findViewById(R.id.ivStartQuestionnaire);

        //btnSearchByCategory.setOnClickListener(this::onClickToCategories);
        ivStartQuestionnaire.setOnClickListener(this::onClickStartQuestionnaire);
       // btnViewPreviousResults.setOnClickListener(this::onClickViewResults);
        // Inflate the layout for this fragment
        return view;
    }

    // Navigate to ChooseCarByCategory Page
    public void onClickToCategories (View view){
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_searchByCategory);
    }

    // Start Questionnaire
    public void onClickStartQuestionnaire (View v){
        Navigation.findNavController(view).navigate(R.id.action_fragment_questionnaire_splashPage_to_fragment_questionnaire_questionPage3);
    }

    // View Results Page.
    public void onClickViewResults (View view){
       // Navigation.findNavController(view).navigate(R.id.action_fragment_questionnaire_splashPage_to_fragment_questionnaire_questionPage3);
    }
}