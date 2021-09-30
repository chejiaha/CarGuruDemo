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

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Your Name is required");
            etName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Your Email is required");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Your Name is required");
            etPassword.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please Enter a Valid Email Address");
            etEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Length of Password must be 6 or greater");
            etPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "User has registered successfully",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Registration failed please try again",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Registration failed please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    public View.OnClickListener  onClickRegistration = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), UserRegistrationFragment.class));
        }
    };
}