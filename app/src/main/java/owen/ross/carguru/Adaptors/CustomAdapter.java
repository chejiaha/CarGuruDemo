package owen.ross.carguru.Adaptors;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
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
                // Getting the Loading progress to start when you click on an element

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
                // Getting the context of FinalListOfQUestionnaire Page so we can pass the next fragment
                // As well as set the loading sign while we wait for the db to spit the car back out.
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                //ProgressBar pbLoading = activity.findViewById(R.id.pbLoadingFinalListQuestionnaire);
                //pbLoading.setVisibility(View.VISIBLE);

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

        //The link to the file in our system (localhost/acura_ilx_2017.jpg)
//        try{
//            //Localhost in the emulator is 127.0.0.1 so I need to use 10.0.2.2 to access my pc's localhostIP
//            String vehicleImageUrl =  String.format("http://10.0.2.2/%s_%s_%s.jpg", make.toLowerCase(), model.toLowerCase(), year);
//           //debug
//            //String vehicleImageUrl = "http://10.0.2.2/audi_a7_2013.jpg";
//            //String vehicleImageUrl = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABFFBMVEX///8jHyAvvhUAAADR09Sxs7T8/PzZ2twgHB0XEhMdGRojICD5+fkMAAX09PTW19ng4eK+vr8euwAbFhh0cnMRCwyPkJEIAADt7e6ko6M8wiU5Njfm5ucMuQBZV1hJR0fw++615q7V79KH2XiU2ot91m2lzKGo4aBBPj9JxjJUyT9ezUpo0FVy02BqaGm3traIx4OGh4gtKSoyLi97eXpgXl5UUVLGx8irqqqPjY6f35bE675GQ0Tl9+HY1NzE0MSfy5ppwluC2XKP3YG96LfQ78vo9uWO0oSGx33A0MB1xWqvzay12a+s1qNiwlJ1xWiSyIvL2cm/17xs0lhyxGVjW2OdtJpzenKBr3msxqh9kHna9NVPUNVsAAARXUlEQVR4nO1d6WLayJaWkNCGQAsIJBDGxBuLDc0qcByy2E7sxDftOH3vzJ1+//eYc0o7YMfp3GkDU9+PWKpN9XG2OkWhMAwFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQXFX0Wh/9Iz+D+G0ON2m6LQsyRrlykKkzzLSjssRSCYy0msVNlVimqvkmPlkZLbVSmCDbIsV7ZrMrubtij08myOKzOM3QKK+d2jSJxMpYyX9oW8g+4mkiDCbe0eRRVs0CeouvAPscWdUtTAycCVqvERxR1yN7ENAkHep7hTtihMKrEE+YCiu0MUVXAyKYKBFHfG3aRskE9S3BFbXFHRJSlu/Rp1ycksUdwBW1Qhm1gjwd2hqEYrmRWCkS3mtjn0r3Uyayhy2Zee6V+E+pgNLimq1dBfN5vNa+Ya/9kikIz+cQnGHnWqHtZNs37ypm7Wmy89658AZPSP22CSolt4Y2YyZkc4q9c7wktP+/lQV2xQEwHrKDJnKMLrV5cnJ69eeNY/AWGyQrA/HQ4nxpqgcX12cnJ+wuzvv/SkfwargV5zrKIkyexsmeKr5sHhwfX+QdU8e+lZ/wSIF01L0LAkNseyytBL8fvWMREfD8yMuUUquiabEHsyS1BxkkJcfAInkzEP9sEQu0zBeOmZPxOpQL9Y+AxHRIQsa5WTDLUMwnx1eNS8/qhOt2QBlw70mZtb5CjWpBxSzCUYLhaLf4BymmbXxiDh1qztWN0I8WJbRCGZ5ue3C14bW2kt1Rb8l5vjbqZ7dPn73QKDhr4lKXHCBvmSp6EamtV3PO8Ni6ilciPgd3vTNs1/fHq4e/d2sSg725P1pwJ9ZWgEhnYL4bBmKUqlQVzp4vZ9BvTz67uvVbN+vJhwc21bsn41FegVeeozzKCe8oNWixjh4u3vpukzx+XascOhcUZS3OhkSsUtizjQK0otYPj+mF/gso0QPM74/HyYx3PLmmvBMrwhb/Q3U0AwldErSitgaJrt+7d+3Hh7Y2YiAYYMy4l91A3OF8EG0+lSzBCZtO9BU/nFu4AYyPWrmWIY78Bt6PbU6mI7xRC4dCE0+gxN8wZc6PuI4TzeKt5cd6PugYqmM/olhsDrPmDYQZXV3i/JMLk9tXmKqu5ZbG5pyyJmCFHB5/ieR4bmZ7TJNQwT7mbTKKoTeTjML2X0bK61qBJmt2/v24SjeX+cYEhK7ubFYryU86WoDHMbFjTUXm40UhyGLNW0APxo1BDbVcQtLNJuMnCR+e0Y/mRuFtjgD1JyNx/mymLUyZficDRiN4mi2hu1WiOfIMzSn66o1WpTsU1wC/eL26/VdvX+rtpuVwlD8Xe4bFfvyqOREzHU/NXNtNaqjTaIYq/WaFygigpiAlrjYqJ3O4hvfsl9p33/pd3ptG8WePsZLjvtO6fVcvREN5tBio1GY+S9NLEI3qg2nU5EuLKTFKfTnn7aRQQMFw+n91863W7nE2H0CS67nQen0Ugy1FUYx5lOG8NS4aWJxeAlaXSxRyjqEcTJXkk/OwV8/xaWfXu4656eds/JzSes6z7M9vZmcS8dc0Wn0Rrle+pL00qClxUgiRQL8Vx7vbF7RvAtLrz7fnb2/QSv3HOs+v6QnUyyborgXIF1TW/D9k41WWEVS2OQouvihF29VBq4H44Ql3d6wMF9OIP7E2jxcEiqzh76pVLfxfZQ6CKtASex+d5LM1oBzwLFHA9XtibqyNIdjOfu5QHBh8sH18fDB7hrut8OPwQ1r4zx2HDJpyL6EsTFUWnDJIjgc0BxSCiSNF4Ue+O5fQi4JGj6DF/h9dW1X3YJlcCwlNVJYiUSCWIGVtooGwzBSwpbHKKi2v76RFYuxGqne3b04QDZnLs24BVyPiesDz4cfe9W7+ZysPLWfBUFCW6aDYbwbZF41HBdKv7Wxh1fWJ3B8qX9AGHwDuMhBPoqWYqbmT/ehutSX4IcSHDzbDAESDGnSJEUFanWX2hf3ptRzvtlEeaH/n33Hb/wSr4Mt4Hgsi2Cz5drpdni4X3E6kuCodk91oxBQ5LJJmpggxvqZGIgxcgWMUZKstxwtON2wKv69l0k0Hve6LEWtsnPtcjJ5DfTycTg0RYlYoserMbliqwUranBB2I0fzsOLtq3i4EkK3LFytVq5YSKbrQEEcTdyESKkF542UFDkWW5vLg3kwwhFTZaVtEalRwD0i0x9qIvPf9nIBH6MQJoWr8nyZWSTzFgaP6hzSTZapT5RBwEgvnNlyDCt8Uo9APJ7NSyeoSiz9D8qjmKPCzzfmq/PTYYAgIhW2QToV/j51a+t/jdDGXIz5TK1Au/Pd0iGwyhFdOhH8Q0k7iBVvUZml+MYqWkBQS3Iw4uQ1Mg9CuxouJBhSHnfDGRofl5McoPwmMZEcENzCaeAj9MLcN9iqz3nsjwtgTyTBGEQJ/f7EC/CvSoxTTFWaX0gAw/Z7nSEsEtcjIxSOhXUrZY5vrdm2Pz39OLkGDoZHJb5GRiiFHWH1Hs/fP+5rjjSF5KgttngyHSoZ+g9a/f3n3+p6OtqOgWShABoT+Xcjea81/v3v13b2cI+lm/kksq6uDfD/9jLBHcShsMAcvwXCr0e7zmraxkXnqWvwQNpZgK/TviZAS1YPtb8ks7cGmCcUavFgqFbQmIQsEVRd0uqIFxraW4upIRVJv023CaqssbBu+mZ7ka+h/NJoSC7hmeuEFfyCSB7GZZfs3sROJuEh41kfCuthZ0GGbzWKq6lwXw63VsKfTHTqa0fjTXgLEM8j3ihqDA95Ff1n2sQSrTeEY2IZDPK+s9OuDfDC3rQ3u8CQn9ftb/rK17NRjS2ARlFYxgNln9iVaQEgfLcMQPA73QDwfdADF64VyyxlOePg79zwn0YjRo9sWtUYjnkjWemg0/LAa2CGvR3JOLbYFPDPqE7v9N6Cdmk/We4BiG/h9lE6poJId8Svf/HrjZFIzHIxkJ/aNx5SkbFFwvPd4mHDhx++k5ZfueXlgrIsz6JXl9oAeotmYsDbURBPHI+vK8gKXBi260Ng3Bk+PsleVALxRsXfNW2IFCbIAn9VEQ10yP8PQ8XtRdGxIHVRUEhh8VFbmkMoKgQjZh266u8Z6xrASh/F7cjaawRsOW6fb7hpF1HMcwDLj+QWPP3cAsA9feP2D5LPQ90d7cfQ3V1vl1FvVMboanuZuwUPsRwHO4ItrXD1QxyYzndXdrsvwIuJeBJ5143vM8I+VQwCTBCfEacUPog3YEQoiXnggFxf8b9C4ajYu1NQOoGf3K0Fd4OO7lfxk9tZSitbamZCkK9ytDN+uZTP1nGLqwlnLEJ5vY2IT/qWlMLVnOr60pySz7iwxN8zGG1ycnJ+f7S4U8l8//4Gc4OlfJh+/MeyYGPcDaml9m+Prr0dHXN+vrzpH9x6VCDXeGnv79hmtBZu78wqQKiOD6lxk+hXMTNHg/VTSbj+GJxV55jhgIAv6ZBZXaAIp0Yz4uQpNJ0ATTGr7cm7ZGgNa05+BWg4qVHmP0oHTPgQWYg21xDG8wrY1yAHbUmAsJhoXZeK+BY4wae2N83cA+HiC+ZppnnWq72jk9DMT06vyo28Hf2nS6RydYdo3tPjIf/T/Ns3a12j49x5rLw1M8dHV5SHDu959w5H0HRYuAUwUrb3F7AcM+B0XeIN3EZdQ9zpKLeHJSUopynhvAdIfQzYGWUFrkagWmgV3J+FZRQWDjykiNGHpcBcZQikX8x+KmwDADGnberYc/E65fQn/htG7Gh3BJWWCHf5KXorT95viKG4apm8ENQccn0bMUnL5UJACGkgTiihjCZLxBJdXEZcYc3uOpQ5nUcH2mUJNYqQUZrixhgcNMi9CBYcrQVKqwICg571cEDIUadFU4rLEsYA9j7Ff935eaIaX6FcMchHzDsibTNIkv/RNcaqZNmvs1r5l6/FHEDAfTBjxKutgjaKxj6EynMG2pFTSxGSQoWY3SeNxrVVC6U8KQlbiLcWkIGi33QoY1juMqHh5id8t5lozsM3RhEGUq2gVSoyj5Qciwc3RwcEQO3ZpHIBb/QHFc1k0xzJhtqOqQmjOmc9rGsu4pwVFoiXzK06xhCL4UPI0VeRqcHBtsYQtZqJIUn2EFm9ggnOIFs0cYCiSLCPoBQ6URMsSnFnvgf/yFuKCqPkMzeFnUFU66y7xBGuafflmTEEkxNF/7tkpEJwSeZtmXPodhPulLRY6NmzCshBNGhkpL9S3bqrQChuis9i4UuSjnptpQSjAkHxOatoyWOJzABJAhiChAB3VQQBrmaVhGlDHJ0AzldIQe5j/K0BqEdy0lZBh0A72zC7EdgqMposVaSClmyOxVJPKuDImgyPlaah6E43ZjhpdhWXuF4UlQc4llP2QIearBM0mG2coSQx6bIEN5HHavRTKUkxHeZ6hzUGu1JhD9pyjsBEN7nON8VAhXzv4rDMPXgy0zFF5fXV2FTAlDjIATeFoPZSKNgh2FSTFmaOGaZgxNJjpxEkHvAr625FGGWRT33Dc1XFkkGMIsbEz/Pa+fHaER88QOAwcIoYMwJHYYau6++WyG+9XE29GIze8VGDcnofJNiS/kQdV48tKVkKECQc6+UFi5JBBPM+Zd29Y9/BCU0WMMHXxbTbAi1FMMDXZUG4WqXrYihubB9cf9/TfXZyYxQKHul70JyzLVHzI0D2FlZ0ZeCNSF2DwIB9Utyzh4KxP1kYsBQzWPGgYlCnnzxVCJmnBFwvYpGbLWpOw4gwl2TnoaRVLyEyc7yzolDDmcG0SLOoLEOFTPzGrZUwxf1/3mpGm0dp0G78eBuYCc1EbgAiC84YIOGTK9SthEzrmMwSlsArKlP8bQzSvEZebzllWbKkktneAnWsxXKpW8jM+a+NGiG/78BOM2TPGqnkkCy55iuB+/WCTysxDfphykF/kKx01xiWmPK5BKwK01d1FKuGQsgI1CWZ7jGhjd+i2OdCGduCkUFSS00ATDC+wKy94i6QcdazraeQ0+Lb+mMLb8MbGSG479eGhevmnX/SVJvUtC41V4j0UmljVRoMgQEPrSA7xBe7+uhs0/JLe8RAPyv1n0VUgB999n+M0BLkZ8r6MbM2jT14MA7Rp9zBmhU/AFg01iRAyMGVijetjP6fNqEEaiGqbAkzEdx/B38QNfKvzZhCSv+Tr0hMLH11cniObV9T4p2AcI4R8feL3vN7++It33mc1DOlrsIijD7QfJD49+3G57ITSbW/YeWgqK/xBUpwwINoT5YJOtUMbgLTrhHSLI1I3oEI9dxjV5OQ7zAg41j17S2Y8SygJ5hvtIr8SZCwPbRd20cnhaITUDDzM9PdwODNuUcV6z4AyVV06QsjEp0oNpeyGnMf6nN4NgD9gewMrBCDKEcnzIxYX0YpA4EiKUkzvnruOETXUcSHQf6xVvps9S27ZlI6RRGMCaxwvuvIGjRp8+Mw4YDgbwtHBfnvwNSdmDPsBeYliezYBLOdGkHDzcSRzjcQfz5JkXoez049esOi6Zl9/O6Pejs5QrveLDa9lyvx992W14vmAIw6xhOMFnbGSzA1tfYciXZ0mGfN8Ix8UXi2T7ywyFctlzI4airuvqKkOmn/oyQIAu0dzF8Ww2CEWCz4g7LvdKMJy5kREUBs4s1NjCQDTG4Rlk0CZv0F9haBfi71bgr+3Ogsdoji7qsxWGjD3zlYswTBwicLLBq47WzdXQxVAZBzqsrwNt5GfwjMcZGjCiL+AZed2QXw4rf7sQ2ERhXAAbtSOGjDhYYQiPyo5Dhn0RHhiQUnF3JtQgnQ8LCbNgTgUjkYfgTk3kbcT06UgylD+ES/ryfDAAIvqY1vXyPwqNNEw8VPdvVOzseUGWoyXmxoRTJ3/50NbJOJt1qoqCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoJiN/C/Y/g2EOtPuCgAAAAASUVORK5CYII=";
//            Glide.with(viewHolder.getActivity()).load(vehicleImageUrl).into(viewHolder.getImageView());
//        }catch (Exception err){
//            //If the urlImage is not found set the hourglass image as the default
//            String linkToCar = "@drawable/searchicon.png";
//            int imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());
//            Drawable carImg = viewHolder.getActivity().getDrawable(imageResource);
//            viewHolder.getImageView().setImageDrawable(carImg);
//            err.printStackTrace();
//        }
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
        }

        Drawable carImg = viewHolder.getActivity().getDrawable(imageResource);
        viewHolder.getImageView().setImageDrawable(carImg);



        //TODO this should be changed to database values
        //replacing - with _ for models like BMW '3-series'
//        String linkToCar = String.format("@drawable/%s_%s_%s", make.toLowerCase(), model.toLowerCase().replace("-", "_"), year);

        // Get the int img resource
//        int imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());
//        // If there is no image for the drawable check if there is and get the closest year to it
//        if(imageResource == 0){
//            //Year variable that will change
//            int tempYear = 2021;
//            int intYear = Integer.parseInt(year);
//            String tempCarLink = "";
//            //DEBUG
//            linkToCar = "@drawable/ford_focus_2017";
//            imageResource = viewHolder.getActivity().getResources().getIdentifier(linkToCar, null, viewHolder.getActivity().getPackageName());

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
//        }
//        Drawable carImg = viewHolder.getActivity().getDrawable(imageResource);
//        viewHolder.getImageView().setImageDrawable(carImg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}