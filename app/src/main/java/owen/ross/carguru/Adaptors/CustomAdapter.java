package owen.ross.carguru.Adaptors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.FindSpecificModel.SpecificVehicleInfoFragment;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Car> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCarItemTitle;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvCarItemTitle = (TextView) view.findViewById(R.id.tvCarItemTitle);
        }

        public TextView getTextView() {
            return tvCarItemTitle;
        }
    }


    public CustomAdapter(ArrayList<Car> dataSet) {
        localDataSet = dataSet;
    }

    Car car;

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_car_item, viewGroup, false);

        //Adding an onClickListener to my view to present the TextBox's Name
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvCarItemTitle = v.findViewById(R.id.tvCarItemTitle);

                //Create an array to split the vehicle into different pieces.
                String[] carInfo = tvCarItemTitle.getText().toString().split(" ");
                //Create a car object with the string peices.
                // Push the car to the next page.
                car = new Car();
                // Ex. Honda Accord Se 2019 // TODO find a way to figure out if there is a space in the name/trim... Land rover
                String make = carInfo[0].substring(0,1)+ carInfo[0].substring(1).toLowerCase();
                String model = carInfo[1].substring(0,1)+ carInfo[1].substring(1).toLowerCase();
                String trim = carInfo[2].toUpperCase();
                String year = carInfo[3];
                car.setMake(make);
                car.setModel(model);
                car.setTrim(trim);
                car.setYear(Integer.parseInt(year));
                // Call the database method to pull the vehicle data.
                //TODO Reference the database
                car = VehicleDatabase.getSpecificCarInfo(car, new VehicleFirebaseCallback() {
                    @Override
                    public void onCallbackCarList(ArrayList<Car> vehicleList) {
                    }

                    @Override
                    public void onCallbackStringArrayList(ArrayList<String> cars) {
                    }

                    @Override
                    public void onCallbackCar(Car dbCar) {
                        // checking if the questions linkedlist is already populated with questions
//                        if (car.getCategory() == null) {
//                            // the questions returned from the database will be added to the list if the list is empty
                        car = dbCar;
                        // }
                    }
                });

                Toast.makeText(view.getContext(), tvCarItemTitle.getText() , Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Fragment fragment = new SpecificVehicleInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("car", (Serializable) car);
                //debug
                bundle.putString("bingo", "bingo");
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

            }
        });
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getMake() + " " + localDataSet.get(position).getModel()
                + " " + localDataSet.get(position).getTrim() + " " + localDataSet.get(position).getYear());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}