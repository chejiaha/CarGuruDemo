package owen.ross.carguru.ui.UserAuthentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import owen.ross.carguru.MainActivity;
import owen.ross.carguru.R;
import owen.ross.carguru.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRegistrationFragment extends Fragment {

    View view;
    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserRegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRegistrationFragment newInstance(String param1, String param2) {
        UserRegistrationFragment fragment = new UserRegistrationFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_registration, container, false);

        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        etEmail = (EditText) view.findViewById(R.id.etRegistrationEmail);
        etName = (EditText) view.findViewById(R.id.etName);
        etPassword = (EditText) view.findViewById(R.id.etRegistrationPassword);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(onClickRegistration);

        return view;
    }

    /* this method will get and check the user input to make sure it is valid */
    private void registerUser() {
        // getting all of the user input
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // checking to see if the user entered a name, if they didn't then an error message will be displayed
        if (name.isEmpty()) {
            etName.setError("Your Name is required");
            etName.requestFocus();
            return;
        }

        // checking to see if the user entered an email, if they didn't then an error message will be displayed
        if (email.isEmpty()) {
            etEmail.setError("Your Email is required");
            etEmail.requestFocus();
            return;
        }

        // checking to see if the user entered a password, if they did not then an error message will be displayed
        if (password.isEmpty()) {
            etPassword.setError("Your Name is required");
            etPassword.requestFocus();
            return;
        }

        // checking to see if the user entered a valid email. if they didn't an error message will be displayed
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please Enter a Valid Email Address");
            etEmail.requestFocus();
            return;
        }

        // checking to see if the user entered a password with a proper length, if not than an error will be displayed
        if (password.length() < 6) {
            etPassword.setError("Length of Password must be 6 or greater");
            etPassword.requestFocus();
            return;
        }

        /* this will add the user to the database */
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // will check to see if the registration task was successful
                        if (task.isSuccessful()) {
                            // creating a new user object passing in the nae, email, and password the user entered
                            User user = new User(name, email, password);

                            // adding the user object to the database under the Users branch
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // checking to see if the user was added to the database successfully
                                    if (task.isSuccessful()) {
                                        // if it is successful, then a success message will be displayed
                                        Toast.makeText(getActivity(), "User has registered successfully",
                                                Toast.LENGTH_LONG).show();


                                        // redirecting the user to the home screen
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);

                                    // if the user wasn't added to the database then an error message will be displayed
                                    } else {
                                        Toast.makeText(getActivity(), "Registration failed please try again",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            // if the registration fails, then an error message will be displayed
                        } else {
                            Toast.makeText(getActivity(), "Registration failed please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    // this listener is set to execute when the user clicks on the registration button
    public View.OnClickListener  onClickRegistration = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            // calling the registerUser method
            registerUser();
        }
    };
}