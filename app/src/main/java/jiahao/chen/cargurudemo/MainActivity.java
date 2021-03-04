package jiahao.chen.cargurudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView tvModel;
    TextView tvEngine;
    TextView tvYear;
    TextView tvMPG;
    TextView tvSeats;
    Button btnSubmit;
    DatabaseReference dbRef;
    DatabaseReference dbRefSpinner;
    Spinner spModels;

    ValueEventListener listener;
    ArrayList<String>list;
    ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button moveToPage2 = (Button)findViewById(R.id.pageTwoBtn);
        moveToPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SortingTop3.class);
                startActivity(intent);
            }
        });

        // retrieving objects from xml
        tvModel = findViewById(R.id.tvModel);
        tvEngine = findViewById(R.id.tvEngine);
        tvYear = findViewById(R.id.tvYear);
        tvMPG = findViewById(R.id.tvMPG);
        tvSeats = findViewById(R.id.tvSeats);
        btnSubmit = findViewById(R.id.btnSubmit);
        spModels = findViewById(R.id.spModels);
        listView = findViewById(R.id.listView);

        // creating a list to store Car makes
        list = new ArrayList<>();
        // appending ArrayAdapter to spinner object. Allows for use of lists in spinner object
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        // set the list object into spinner object
        spModels.setAdapter(spinnerAdapter);
        //reference = FirebaseDatabase.Instance.Reference;

        //retrieve the instance from Firebase
        dbRefSpinner = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        //models = FirebaseDatabase.getInstance().getReference().child("Vehicle").toString();

        // method to retrieve values and place into list
        fetchData();

//
//        // instantiate arraylist
        ArrayList<String> usersList = new ArrayList<>();
//
////        // instantiate adapter with list_item.xml
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, usersList);
//        // append xml and arraylist to listview
        listView.setAdapter(adapter);

                // returns a reference of the root branch
//       DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        // returns reference of the child branch "Mazda"
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

// to get all the values inside the "Mazda branch"
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    UsersModel user = snapshot.getValue(UsersModel.class);
                    String txt = user.getFirstname() + " : " + user.getLastname() ;
//                    //list.add(snapshot.getValue().toString());
                    usersList.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // attach the key value from spinner to .child of the parent (vehicle)
                dbRef= FirebaseDatabase.getInstance().getReference().child("Vehicle")
                        .child(spModels.getSelectedItem().toString());

                dbRef.addValueEventListener(new ValueEventListener() {

                    // overrides default onChange method, passing in DataSnapshot object (Firebase)
                    // Datasnapshot is an instance of the Firebase database at that moment in time (like taking a picture)
                    // you can extract contents by calling .value() or traverse into snapshot by calling .child()
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // log errors to console
                        Log.d("TAG",(spModels.getSelectedItem().toString()));

                        // getting the child fields from Firebase table
                        // names have to match child(keys) in table (they are case-sensitive!)
                        String model = snapshot.child("Model").getValue().toString();
                        String engine = snapshot.child("Engine").getValue().toString();
                        String year = snapshot.child("Year").getValue().toString();
                        String mpg = snapshot.child("MPG").getValue().toString();
                        String seats = snapshot.child("Seats").getValue().toString();

                        // set fields to Textview
                        tvModel.setText(model);
                        tvEngine.setText(engine);
                        tvYear.setText(year);
                        tvMPG.setText(mpg);
                        tvSeats.setText(seats);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("TAG", error.toString());
                    }
                });
            }
        });

    }

    // method to retrieve data from Firebase and add to spinner list
    public void fetchData(){
        listener = dbRefSpinner.addValueEventListener(new ValueEventListener() {
            // overrrides default onChange method to database snapshot
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // loop through each child(key) in parent(root) table
                for(DataSnapshot ds : snapshot.getChildren()){
                    // add keys to list
                    list.add(ds.getKey());
                    // update adapter
                    spinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.toString());
            }
        });
    }
}

//        listView = findViewById(R.id.lvCarInfo);
//
//        // instantiate arraylist
//        ArrayList<String> list = new ArrayList<>();
//
////        // instantiate adapter with list_item.xml
////        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
//        // append xml and arraylist to listview
////        listView.setAdapter(adapter);
//
//        // returns a reference of the root branch
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        // returns reference of the child branch "Mazda"
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");
//
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        CollectionReference applicationRef = rootRef.collection("car-guru-demo-default-rtdb");
//        DocumentReference applicationIDRef = applicationRef.document(applicationRef.getId());
//        applicationIDRef.get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists()) {
//                    List<CarModel> carModels = document.toObject(CarList.class).list;
//                    // instantiate adapter with list_item.xml
//                    ArrayAdapter adapter = new ArrayAdapter<CarModel>(this, R.layout.list_item, carModels);
//                    listView.setAdapter(adapter);
//                }
//
//            }
//
//        });
// to get all the values inside the "Mazda branch"
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    CarModel car = snapshot.getValue(CarModel.class);
//                    String txt = car.getEngine() + " : " + car.getModel() + " : " + Integer.toString(car.getYear());
////                    String txt = info.getModel() + " : " + info.getEngine() + " : " + info.getMpg()
////                            + " : " + info.getYear() + " : " + info.getSeats();
////                    String txt = Integer.toString(info.getMpg());
////                    //list.add(snapshot.getValue().toString());
//                    list.add(txt);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });