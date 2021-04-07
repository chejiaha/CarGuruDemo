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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_by_category, container, false);

        // setting the values of the UI controls for this Activity
        ImageView commuter = view.findViewById(R.id.ivChooseCommuter);
        ImageView sport = view.findViewById(R.id.ivChooseSports);
        Intent intent = new Intent(getActivity(), DisplayCategoryCarsActivity.class);

        // setting the onClickListener for the Commuter category image
        commuter.setOnClickListener(new View.OnClickListener() {
            // this will set the string for the category the user chose and pass it to the next screen
            @Override
            public void onClick(View v) {
                String category = "Commuter";
                intent.putExtra("category", category);
                startActivity(intent);

            }
        });

        // setting teh onClickListener for the Sports category image
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            // this will set the string for the category the user chose and pass it to the next screen
            public void onClick(View v) {
                String category = "Sport";
                intent.putExtra("category", category);
                startActivity(intent);

            }
        });

        return view;
    }

}