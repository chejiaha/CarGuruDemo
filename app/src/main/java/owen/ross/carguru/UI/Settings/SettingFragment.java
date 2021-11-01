package owen.ross.carguru.UI.Settings;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import owen.ross.carguru.R;
import owen.ross.carguru.UI.Articles.ArticleFragment;

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

    Button btnDark;
    Button btnLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);


        //Setting the light and dark themes
        btnDark = view.findViewById(R.id.btnDark);
        btnLight = view.findViewById(R.id.btnLight);

        btnDark.setOnClickListener(onClickChangeThemeDark);
        btnLight.setOnClickListener(onClickChangeThemeLight);

        // Inflate the layout for this fragment
        return view;
    }

    // Car News Link
    public View.OnClickListener  onClickChangeThemeDark = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getActivity();
//
            try{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }catch (Exception err){
                context.setTheme(R.style.Theme_CarGuru);
            }

        }
    };

    // Car News Link
    public View.OnClickListener  onClickChangeThemeLight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getActivity();
            try{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }catch (Exception err){
                context.setTheme(R.style.Theme_CarGuruLight);
            }

        }
    };






}