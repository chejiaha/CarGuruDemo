package jiahao.chen.cargurudemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MyGarageSplashFragment extends Fragment {

    //creating Page context
    Context context;
    //Creating View Objects
    Button btnSignIn;
    Button btnSignUp;
    boolean loggedIn = false;

    //This list will hold the Question IDs so we can go forwards and back to get the questions.
    int[] questionIDs;
    String[] questionAnswer;


    public MyGarageSplashFragment() {
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
        return inflater.inflate(R.layout.fragment_my_garage_splash_page, container, false);
    }
}