package owen.ross.carguru.Adaptors;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.Database.VehicleDatabase;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;
import owen.ross.carguru.UI.FindSpecificModel.SpecificVehicleInfoFragment;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Car> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCarItemTitle;
        private final ImageView ivCarItemImage;
        private final AppCompatActivity activity;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvCarItemTitle = (TextView) view.findViewById(R.id.tvCarItemTitle);
            ivCarItemImage = (ImageView) view.findViewById(R.id.ivCarItemImage);
            //Allowing for getResources to change the drawables
            activity = (AppCompatActivity) view.getContext();
        }

        public TextView getTextView() {
            return tvCarItemTitle;
        }
        public ImageView getImageView(){
            return ivCarItemImage;
        }
        public AppCompatActivity getActivity(){
            return activity;
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
                // We get HONDA ACCORD SE 2019 // we need Honda Accord Se 2019 // TODO find a way to figure out if there is a space in the name/trim... Land rover
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
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

            }
        });
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get the car make,model,year to pull pictures
        String make = localDataSet.get(position).getMake();
        String model = localDataSet.get(position).getModel();
        String trim = localDataSet.get(position).getTrim();
        String year = localDataSet.get(position).getYear() + "";

        // Replace the spaces in between the make,moden & trim names with a _
        make = make.replace(" ", "_");
        model = model.replace(" ", "_");
        trim = trim.replace(" ", "_");

        // Get element from your dataset at this position and replace the contents of the view with that element
        viewHolder.getTextView().setText(String.format("%s %s %s %s", make, model, trim, year));

        //The link to the file in our system
        //TODO this should be changed to database values
        //replacing - with _ for models like BMW '3-series'
        String linkToCar = String.format("@drawable/%s_%s_%s", make.toLowerCase(), model.toLowerCase().replace("-", "_"), year);

        // Get the int img resource
        int imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());
        // If there is no image for the drawable check if there is and get the closest year to it
        if(imageResource == 0){
            //Year variable that will change
            int tempYear = 2021;
            int intYear = Integer.parseInt(year);
            String tempCarLink = "";
            //DEBUG
            linkToCar = "@drawable/ford_focus_2017";
            imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());



//            // While the resource is == 0 look for an image with a year above.
//            while(imageResource == 0){
//                //There was no file found in the drawable. look for one that is above the year
//                if (intYear <= 2021 && intYear >=2008){
//                    tempYear = tempYear -1;
//                    tempCarLink = String.format("@drawable/%s_%s_%s", make.toLowerCase(), model.toLowerCase(), tempYear);
//                    imageResource = viewHolder.getActivity().getResources().getIdentifier(tempCarLink, null, viewHolder.getActivity().getPackageName());
//                }
//                else{
//                    // Number is out of range
//                    linkToCar = "@drawable/ford_focus_2017";
//                    imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());
//                    break;
//                }
//            }
        }
        Drawable carImg = viewHolder.getActivity().getDrawable(imageResource);
        viewHolder.getImageView().setImageDrawable(carImg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}