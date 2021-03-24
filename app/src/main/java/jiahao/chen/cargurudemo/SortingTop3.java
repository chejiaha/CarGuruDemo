package jiahao.chen.cargurudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingTop3 extends AppCompatActivity {
    ArrayList<CarModel> commuterCarModels = new ArrayList<>();
    ArrayList<CarModel> sportCarModels = new ArrayList<>();
    ArrayList<String> carCommuterString = new ArrayList<>();
    ArrayList<String> carSportString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_top3);

        Button commuterBtn = findViewById(R.id.commuterBtn);
        Button sportBtn = findViewById(R.id.sportBtn);
        commuterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carCommuterString.clear();
                sortCar(v, "Commuter"
                );
            }
        });
        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carSportString.clear();
                sortCar(v, "Sport");
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(ValueEventListener context, int textViewResourceId,
                                  List<String> objects) {
            super((Context) context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void sortCar(View view, String carType) {

        TextView testingTV = findViewById(R.id.testingResultTV);
        DatabaseReference listOfVehicles = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        listOfVehicles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String make = "";
                String model = "";
                String year = "";
                CarModel carModel;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    carModel = new CarModel();
                    carModel.Make = snapshot.getKey();
                    make = snapshot.getKey();
                        for (DataSnapshot modelSS: dataSnapshot.child(make).getChildren()) {
                            carModel.Model = modelSS.getKey();
                            model = modelSS.getKey();
                            for (DataSnapshot yearSS: dataSnapshot.child(make).child(model).getChildren()) {
                               // carModel.Year = yearSS.getKey();
                                year = yearSS.getKey();

                                if ("Commuter".equals((String)dataSnapshot.child(make).child(model).child(year).child("Category").getValue())){
                                    carCommuterString.add(carModel.Make + " " + carModel.Model + " " + carModel.Year);
                                    commuterCarModels.add(carModel);
                                } else {
                                    carModel.Category = carType;
                                    sportCarModels.add(carModel);
                                    carSportString.add(carModel.Make + " " + carModel.Model + " " + carModel.Year);
                                }
                            }
                        }
                }

                ArrayAdapter spinnerAdapter;

                //listview
                ListView lv = findViewById(R.id.resultListView);
                if (carType.equals("Commuter")){
                    spinnerAdapter = new ArrayAdapter<>(SortingTop3.this, android.R.layout.simple_list_item_1, carCommuterString);
                } else {
                    spinnerAdapter = new ArrayAdapter<>(SortingTop3.this, android.R.layout.simple_list_item_1, carSportString);
                }
                // set the list object into spinner object
                lv.setAdapter(spinnerAdapter);
                /*final StableArrayAdapter adapter = new StableArrayAdapter(this,
                        android.R.layout.simple_list_item_1, carString);
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        view.animate().setDuration(2000).alpha(0)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        carString.remove(item);
                                        adapter.notifyDataSetChanged();
                                        view.setAlpha(1);
                                    }
                                });
                    }*/

                //});
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int getCarYear(DataSnapshot modelSS, String model) {
        int year = 0;
        if (modelSS.child(model).getChildrenCount() > 1) {
            for (DataSnapshot yearSS: modelSS.child(model).getChildren()) {
                year = Integer.valueOf(modelSS.child(model).getKey());
            }
        } else {
          //  carModel.Year = Integer.valueOf(modelSS.child(model).getKey());
        }
        return year;
    }
}