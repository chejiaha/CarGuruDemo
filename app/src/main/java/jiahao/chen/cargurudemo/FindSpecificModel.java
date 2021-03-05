package jiahao.chen.cargurudemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FindSpecificModel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_specific_model_list);

    }

    public void onClickFindModel (View view){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, SpecificModel.class);
        startActivity(intent);
    }
}
