package owen.ross.carguru.UI.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import owen.ross.carguru.R;

/**
 *
 */
public class SettingFragment extends Fragment {



    public SettingFragment() {
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
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Inflate the layout for this fragment
        return view;
    }
}