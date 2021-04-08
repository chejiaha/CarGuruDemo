package jiahao.chen.cargurudemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class SearchByCategoryFragment extends Fragment {


    public SearchByCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_by_category, container, false);

        // setting the values of the UI controls for this Activity
        ImageView commuter = view.findViewById(R.id.ivChooseCommuter);
        ImageView sport = view.findViewById(R.id.ivChooseSports);

        // setting the onClickListener for the Commuter category image
        commuter.setOnClickListener(new View.OnClickListener() {
            // this will set the string for the category the user chose and pass it to the next screen
            @Override
            public void onClick(View v) {
                //TODO Create a methdo to put items in and send it to the next fragment
                // functionName(String categoryName, Fragment fragmentName, )
                String category = "Commuter";
                // Adding the arguments into the bundle
                Bundle bundle = new Bundle();
                // Adding the question number that will specify the question from the list
                bundle.putString("Category", category);
                // Telling it what Fragment you want to replace it with.
                Fragment fragment = new DisplayCategoryCarsFragment();
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
                //TODO pass all the values and go to the next category pages.

            }
        });

        // setting teh onClickListener for the Sports category image
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            // this will set the string for the category the user chose and pass it to the next screen
            public void onClick(View v) {
                String category = "Sport";

                //TODO Create a methdo to put items in and send it to the next fragment
                // functionName(String categoryName, Fragment fragmentName, )

                // Adding the arguments into the bundle
                Bundle bundle = new Bundle();
                // Adding the question number that will specify the question from the list
                bundle.putString("Category", category);
                // Telling it what Fragment you want to replace it with.
                Fragment fragment = new DisplayCategoryCarsFragment();
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
                //TODO pass all the values and go to the next category pages.
            }
        });

        return view;
    }

}