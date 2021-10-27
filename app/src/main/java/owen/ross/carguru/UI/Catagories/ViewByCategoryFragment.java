package owen.ross.carguru.UI.Catagories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import owen.ross.carguru.R;
import owen.ross.carguru.UI.Questionnaire.QuestionnaireFragment;


public class ViewByCategoryFragment extends Fragment {


    public ViewByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ImageView ivChooseCommuter;
    ImageView ivChooseSport ;
    ImageView ivChooseLuxury;
    ImageView ivChooseFamily ;
    ImageView ivChooseUtility;
    ImageView ivChooseBeater;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout. fragment_view_by_category, container, false);
        // setting the values of the UI controls for this Activity
        ivChooseCommuter = view.findViewById(R.id.ivChooseCommuter);
        ivChooseSport = view.findViewById(R.id.ivChooseSports);
        ivChooseLuxury = view.findViewById(R.id.ivChooseLuxury);
        ivChooseFamily = view.findViewById(R.id.ivChooseFamily);
        ivChooseUtility = view.findViewById(R.id.ivChooseUtility);
        ivChooseBeater = view.findViewById(R.id.ivChooseBeater);
        //Setting up the listeners

        // Inflate the layout for this fragment
        return view;
    }


    // Listener to find all cars in the Commuter Category and list them
    public View.OnClickListener  onClickFindMeACar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String category = "Commuter";
            // Adding the arguments into the bundle
            Bundle bundle = new Bundle();
            // Adding the question number that will specify the question from the list
            bundle.putString("Category", category);
            // Telling it what Fragment you want to replace it with.
            Fragment fragment = new ListOfCarsBasedOnCategory();
            fragment.setArguments(bundle);
            //TODO This can also Be a function.
            //TODO functionName(Fragment FragmentName, Bundle bundle, int IdOfNavHostUI**Optional)

            // create a FragmentManager
            FragmentManager fm = getFragmentManager();
            // create a FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            // replace the FrameLayout with new Fragment
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.commit(); // save the changes

        }
    };

}