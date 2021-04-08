package jiahao.chen.cargurudemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.DescriptorProtos;
import java.util.ArrayList;

public class DisplayCategoryCarsFragment extends Fragment {


    public DisplayCategoryCarsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View view;
    TextView tvCategory;
    ArrayList<CarModel> cars;
    ListView listView;
    ArrayAdapter carArr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_category_cars, container, false);
        // setting the values for the UI controls
        tvCategory = view.findViewById(R.id.tvCategory);
        listView = view.findViewById(R.id.category_cars_list);
        // setting the string that was passed form the previous activity
        String category = getArguments().getString("category");
        cars = new ArrayList<>();

        //getting all of the items from the Vehicle portion of the database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");


        ValueEventListener listener = reference.addValueEventListener(new ValueEventListener() {

            /*
             *   This method is used to get the vehicle information from the database.
             *   The Information is stored into the cars ArrayList. This list will contain the make,
             *   model, year and trim. The car will only be added to the list if it has the same category
             *   as the category that was selected in the previous screen. This will be used in the
             *   onCreate method to look through the information and populate the Spinners(ComboBoxes)
             *
             *   Will Populate:
             *       ArrayList<CarModel> cars ()
             *   Will Return:
             *       ArrayList <CarModel> cars (**only make, model, year and trim are filled out**)
             */

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String make = "";
                String model = "";
                String trim = "";
                String year = "";
                String databaseCategory = "";
                ArrayList<String> carModels = new ArrayList<>();
                CarModel carModel;

                for (DataSnapshot ssMake : snapshot.getChildren()) {
                    //setting the make so we can iterate through them
                    make = ssMake.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot ssModel : snapshot.child(make).getChildren()) {
                        //setting the model so we can iterate through them
                        model = ssModel.getKey();
                        for (DataSnapshot ssTrim : snapshot.child(ssMake.getKey()).child(model).getChildren()) {
                            //setting the trim so we can iterate through each year and get the models
                            trim = ssTrim.getKey();
                            for (DataSnapshot ssYear : snapshot.child(make).child(model).child(trim).getChildren()) {
                                //setting the year so we can get the data of its children
                                year = ssYear.getKey();
                                // setting the value for the category of car, so it can be compared to the category that was passed in
                                databaseCategory = ssYear.child("Category").getValue().toString();

                                Log.d("Category", "The category of the car is: " + databaseCategory);

                                // checking to see if the category that was passed in matches the category of the current car
                                if (databaseCategory.equalsIgnoreCase(category)) {
                                    // adding the car values to the CarModel object
                                    carModel = new CarModel();
                                    carModel.setMake(make);
                                    carModel.setModel(model);
                                    carModel.setTrim(trim);
                                    carModel.setYear(Integer.parseInt(year));
                                    // adding the CarModel object to the cars array
                                    cars.add(carModel);
                                }

                            }
                        }
                    }
                }
                /*
                 *  iterating through all of the items in the cars array list, to save the data into
                 *  the carModels array list to be displayed
                 */
                for (CarModel car: cars) {
                    // adding the make, model, trim, and year as a concatenated string to the carModels array list
                    carModels.add(car.getMake() + " " + car.getModel() + " " + car.getTrim() + " " + car.getYear());
                }

                // setting the array adapter that will be set to the listView that will display all of the car information
                carArr = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, carModels);

                /*
                 *  setting the text of the category of label at the top of the page, it will be the
                 *  category that the user chose on the last screen
                 */
                tvCategory.setText(category);

                // setting the array adapter to the listview
                listView.setAdapter(carArr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        // Inflate the layout for this fragment
        return view;
    }
}