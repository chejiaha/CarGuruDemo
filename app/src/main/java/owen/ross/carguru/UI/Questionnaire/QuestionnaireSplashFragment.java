package owen.ross.carguru.UI.Questionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import owen.ross.carguru.Models.HelperFunctions;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.Catagories.ViewByCategoryFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionnaireSplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        ivStartQuestionnaire.setOnClickListener(onClickToCategories);
        btnSearchByCategory.setOnClickListener(onClickFindMeACar);




        // Inflate the layout for this fragment
        return view;
    }

    // To Find me a car
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Questionnaire fragment
            //Going from SearchCarFragment to Specific model fragment
            Fragment findMeACar = new QuestionnaireFragment();
            HelperFunctions.switchFragments(getActivity(), findMeACar, R.id.nav_host_fragment, new Bundle());

        }
    };

    // Browse By Category Link
    public View.OnClickListener  onClickToCategories = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment viewByCategoryFragment = new ViewByCategoryFragment();
            HelperFunctions.switchFragments(getActivity(),viewByCategoryFragment, R.id.nav_host_fragment, new Bundle());
        }
    };


    // Start Questionnaire
    public void onClickStartQuestionnaire (View v){

    }

    // View Results Page.
    public void onClickViewResults (View view){

    }


}