package owen.ross.carguru.UI.Garage.UserAuthentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import owen.ross.carguru.MainActivity;
import owen.ross.carguru.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserLoginFragment extends Fragment {

    View view;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserLoginFragment newInstance(String param1, String param2) {
        UserLoginFragment fragment = new UserLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_login, container, false);

        TextView register = (TextView) view.findViewById(R.id.tvRegister);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        etEmail = (EditText) view.findViewById(R.id.etEmailAddress);
        etPassword = (EditText) view.findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(onClickRegisterPage);
        btnLogin.setOnClickListener(onClickLogin);

        // Inflate the layout for this fragment
        return view;
    }

    //TODO DELETE THIS
    public View.OnClickListener  onClickRegisterPage = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), owen.ross.carguru.UI.Garage.UserAuthentication.UserRegistrationFragment.class));
        }
    };

    public View.OnClickListener  onClickLogin = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            userLogin();
        }
    };

    /* this method will get the user input and check to see if the user entered valid input */
    private void userLogin() {
        // getting the email and password the user entered
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // checking to see if the user entered an email, if they didn't an error message will be displayed
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        // checking to see if the user entered a valid email or not, if they didn't then an error message will be displayed
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        // checking to see if the user entered a password, if they didn't an error message will be displayed
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // checking to see if the user entered a password with a valid length, if they didn't then an error message will be displayed
        if (password.length() < 6) {
            etPassword.setError("Minimum password length is 6 characters");
            etPassword.requestFocus();
            return;
        }

        /* this listener will be executed when the user clicks the login button */
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // checking to see if the task was successful
                if (task.isSuccessful()) {
                    // getting the current user that has logged in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    /* Optional code that we can use if we want to add email verification */
//                    // checking if the user has verified their email or not
//                   if (user.isEmailVerified()) {
//                       // redirect to login homepage
//                   } else {
//                       user.sendEmailVerification();
//                      Toast.makeText(getActivity(), "Please check your email to verify your account", Toast.LENGTH_LONG).show();
//                  }

                    // displaying a message to the user that they have logged in successfully
                    Toast.makeText(getActivity(), "User " + user.getEmail() + " Has logged in", Toast.LENGTH_LONG).show();

                    // redirecting the user to the home page
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    // if the user did not login successfully then a failure message will be displayed
                } else {
                    Toast.makeText(getActivity(), "Login Failed, Please Check Your Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}