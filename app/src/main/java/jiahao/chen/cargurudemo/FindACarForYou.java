package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

/*
 * This is connected to the guru activity. This page is used to help the user find a car that they
 * want to buy.
 */
public class FindACarForYou extends AppCompatActivity {
    //creating Page context
    Context context;
    //Creating View Objects
    ImageView ivStartQuestionnaire;
    Button btnViewPreviousResults;
    Button btnSearchByCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_splash_page);
        //Creating context
        context = getApplicationContext();
        btnSearchByCategory = findViewById(R.id.btnViewByCategory);
        btnViewPreviousResults = findViewById(R.id.btnViewPreviousResults);
        ivStartQuestionnaire = findViewById(R.id.ivStartQuestionnaire);

        btnSearchByCategory.setOnClickListener(this::onClickToCategories);
        ivStartQuestionnaire.setOnClickListener(this::onClickStartQuestionnaire);
        btnViewPreviousResults.setOnClickListener(this::onClickViewResults);
    }

    // Navigate to ChooseCarByCategory Page
    public void onClickToCategories (View view){
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_MyGarage);
    }
    // Start Questionnaire
    public void onClickStartQuestionnaire (View view){
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_questionnaire_splashPage2);
    }
    // View Results Page.
    public void onClickViewResults (View view){
        Navigation.findNavController(view).navigate(R.id.action_fragment_questionnaire_splashPage_to_fragment_questionnaire_questionPage3);
    }






}
