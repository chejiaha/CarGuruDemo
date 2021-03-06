package jiahao.chen.cargurudemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity2 extends AppCompatActivity {

    DatabaseReference dbRefSpinner;
    DatabaseReference dbRef;
    ValueEventListener listener;
    ArrayList<String> listParent;
    ArrayList<String> listChild;
    ArrayList<String> listModel;
    ArrayList<String> listYear;
    ArrayList<String> listSpecs;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> spinnerAdapterChild;
    ArrayAdapter<String> spinnerAdapterModel;
    ArrayAdapter<String> spinnerAdapterYear;
    ArrayAdapter<String> spinnerAdapterSpecs;
    Spinner spinner;
    Spinner spinnerChild;
    Spinner spinnerModel;
    Spinner spinnerYear;
    Spinner spinnerSpecs;
    EditText etInput;
    Button btnAddToDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        spinner = findViewById(R.id.spKeys);
        etInput = findViewById(R.id.etValue);
        spinnerChild = findViewById(R.id.spChildMake);
        spinnerModel = findViewById(R.id.spChildModel);
        spinnerYear = findViewById(R.id.spChildYear);
        spinnerSpecs =findViewById(R.id.spChildSpecs);
        btnAddToDb = findViewById(R.id.btnAddValue);

        // creating a list to store Car makes
        listParent = new ArrayList<>();
        listChild = new ArrayList<>();
        listModel = new ArrayList<>();
        listYear = new ArrayList<>();
        listSpecs = new ArrayList<>();

        // appending ArrayAdapter to spinner object. Allows for use of lists in spinner object
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listParent);
        spinnerAdapterChild = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listChild);
        spinnerAdapterModel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listModel);
        spinnerAdapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listYear);
        spinnerAdapterSpecs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listSpecs);

        // set the list object into spinner object
        spinner.setAdapter(spinnerAdapter);
        spinnerChild.setAdapter(spinnerAdapterChild);
        spinnerModel.setAdapter(spinnerAdapterModel);
        spinnerYear.setAdapter(spinnerAdapterYear);
        spinnerSpecs.setAdapter(spinnerAdapterSpecs);


        // retrieve root
        dbRefSpinner = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        // populate spinner objects on touch
        fetchDataParent();
        spinnerChild.setOnTouchListener(Spinner_OnTouch_Child);
        spinnerModel.setOnTouchListener(Spinner_OnTouch_Model);
        spinnerYear.setOnTouchListener(Spinner_OnTouch_Year);
        spinnerSpecs.setOnTouchListener(Spinner_OnTouch_Specs);

        btnAddToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = etInput.getText().toString();
                HashMap map = new HashMap();
                map.put(spinnerSpecs.getSelectedItem().toString(), value);
                // set child path
                dbRef = dbRef
                        .child(spinner.getSelectedItem().toString())
                        .child(spinnerChild.getSelectedItem().toString())
                        .child(spinnerModel.getSelectedItem().toString())
                        .child(spinnerYear.getSelectedItem().toString())
                        .child(spinnerSpecs.getSelectedItem().toString());
                //consoel debug
                Log.d("TAG",dbRef.toString());
                // set value to path determined by spinner
                dbRef.setValue(value);
            }
        });
    }

    // method to retrieve data from Firebase and add to spinner list
    public void fetchDataParent() {
        listener = dbRefSpinner.addValueEventListener(new ValueEventListener() {
            // overrrides default onChange method to database snapshot
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // loop through each child(key) in parent(root) table
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // add keys to list
                    listParent.add(ds.getKey());
                    // update adapter
                    spinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // populates spinner depending on which option user picked previously
    private View.OnTouchListener Spinner_OnTouch_Child = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            listChild.clear();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                listener = dbRefSpinner.child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    // overrrides default onChange method to database snapshot
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // loop through each child(key) in parent(root) table
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            // add keys to list
                            listChild.add(ds.getKey());
                            // update adapter and adds selected item to the spinner object
                            spinnerAdapterChild.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            return false;
        }
    };

    private View.OnTouchListener Spinner_OnTouch_Model = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            listModel.clear();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                listener = dbRefSpinner.child(spinner.getSelectedItem().toString())
                        .child(spinnerChild.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    // overrrides default onChange method to database snapshot
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // loop through each child(key) in parent(root) table
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            // add keys to list
                            listModel.add(ds.getKey());
                            // update adapter
                            spinnerAdapterModel.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            return false;
        }
    };

    private View.OnTouchListener Spinner_OnTouch_Year = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            listYear.clear();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                listener = dbRefSpinner.child(spinner.getSelectedItem().toString())
                        .child(spinnerChild.getSelectedItem().toString())
                        .child(spinnerModel.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    // overrrides default onChange method to database snapshot
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // loop through each child(key) in parent(root) table
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            // add keys to list
                            listYear.add(ds.getKey());
                            // update adapter
                            spinnerAdapterYear.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            return false;
        }
    };

    private View.OnTouchListener Spinner_OnTouch_Specs = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            listSpecs.clear();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                listener = dbRefSpinner.child(spinner.getSelectedItem().toString())
                        .child(spinnerChild.getSelectedItem().toString())
                        .child(spinnerModel.getSelectedItem().toString())
                        .child(spinnerYear.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    // overrrides default onChange method to database snapshot
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // loop through each child(key) in parent(root) table
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            // add keys to list
                            listSpecs.add(ds.getKey());
                            // update adapter
                            spinnerAdapterSpecs.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            return false;
        }
    };
}