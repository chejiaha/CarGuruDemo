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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    public View.OnClickListener  onClickRegisterPage = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), UserRegistrationFragment.class));
        }
    };

    public View.OnClickListener  onClickLogin = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            userLogin();
        }
    };

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Minimum password length is 6 characters");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to login homepage
                } else {
                    Toast.makeText(getActivity(), "Login Failed, Please Check Your Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}