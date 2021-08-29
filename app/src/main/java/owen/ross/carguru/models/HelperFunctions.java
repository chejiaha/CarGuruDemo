package owen.ross.carguru.models;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import owen.ross.carguru.R;

public class HelperFunctions {

    /*
     * A method that will Check if the fields are empty or null
     * This method is used in the SpecificVehicleInfoFragment to stop the program from crashing
     *
     * This will prevent and handle the event of a null pointer or empty fields occuring
     *
     * Checks for
     *  <String>
     *      If its Null or "" Empty
     *  <int>
     *      If its 0 throw an error TODO(Since its in beta do not throw yet.)
     *  [List]
     *      If its Empty, or if it is null Supply a string to display.
     *
     *      CARMODEL OBJECT!
     *      Description         <String>
     *      Recalls             [List]
     *      Category            <String>
     *      Drivetrain          <String>
     *      Cylinders           <int>
     *      CommonProblems      [List]
     *      Doors               <int>
     *      Engine              <String>
     *      Horsepower          <int>
     *      MPG                 <int>
     *      Price               <int>
     *      Seats               <int>
     *
     * Returns None.
     */
    public static Car CheckFields(Car car) throws NullPointerException{
        //Check if the arrays are null, if they are, just populate the empty array
        //Checking the Common problems Array, if it is null or less then one.
        if (car.getCommonProblems() == null){
            String[] emptyList = {"No Common Problems Allocated"};
            car.setRecalls(emptyList);
        }
        //Checking the Recalls Array, if it is null or less then one.
        if (car.getRecalls() == null){
            String[] emptyList = {"No Recalls"};
            car.setRecalls(emptyList);
        }
        //Checking the Category  Array, if it is null or less then one.
        //TODO When the categories are arrays implement the code!

        //Checking if the Number of Engine is set null or is empty
        if (car.getEngine() == null || car.getEngine().trim().equals("")){
            car.setEngine("The Engine Type has not been Set!");
            //throw new NullPointerException("The Engine String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (car.getCategory() == null || car.getCategory().trim().equals("")){
            car.setCategory("The Category Type has not been Set!");
            //throw new NullPointerException("The Category String is not set in SpecificModel");
        }
        //Checking if the Number of Engine is set null or is empty
        if (car.getDrivetrain() == null || car.getDrivetrain().trim().equals("")){
            car.setDrivetrain("The Drivetrain Type has not been Set!");
            //throw new NullPointerException("The Drivetrain String is not set in SpecificModel");
        }
        //Checking if the variable for Price is null or a string
        if (car.getPrice() == 0){
            car.setPrice(-1);
            //throw new NullPointerException("The Price is not set in SpecificModel") ;
        }
        //Checking if the variable for Seats is set is 0
        if (car.getSeats() == 0){
            car.setSeats(-1);
            //throw new NullPointerException("The Seats Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Torque is set is 0
        if (car.getTorque() == 0){
            car.setTorque(-1);
            //throw new NullPointerException("The Torque Variable is not set in SpecificModel") ;
        }
        //Checking if the Number of Doors is set is 0
        if (car.getDoors() == 0){
            car.setDoors(-1);
            //throw new NullPointerException("The Doors Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Horsepower is set is 0
        if (car.getHorsePower() == 0){
            car.setHorsePower(-1);
            // throw new NullPointerException("The HorsePower Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for MPG is set is 0
        if (car.getMPG() == 0){
            car.setMPG(-1);
            //   throw new NullPointerException("The MPG Variable is not set in SpecificModel") ;
        }
        //Checking if the variable for Cylinders is set is 0
        if (car.getCylinders() == 0){
            car.setCylinders(-1);
            //   throw new NullPointerException("The Cylinders Variable is not set in SpecificModel") ;
        }
        return car;
    }

    //POSSIBLE NEW CAR WEBSITE TO SCRAPE?
    // https://cars.usnews.com/cars-trucks/acura/ilx


//    /*
//     * This method is created to send a fragment from one fragment to the other.
//     *
//     * Takes in:
//     *  Fragment FragmentName : The Fragment That you want to instantiate.
//     *  Bundle bundle         : The bundle with all of the objects already populated.
//     *  int IdOfNavHostUI     : This is used to specify which view you want to replace.
//     *          BY default in our application set to the id of the "navigation_host_fragment" ID
//     *  NOTE:
//     */
//    public void switchFragments (Fragment fragmentName, int idOfNavHostUI, Bundle bundle){
//        //If the bundle is not empty add the argument
//        if (bundle.isEmpty() == false){
//            fragmentName.setArguments(bundle);
//        }
//        // If idOfNavHostUI is null, then set it to the navigation_host_fragment
//        idOfNavHostUI = idOfNavHostUI != 0 ? idOfNavHostUI : R.id.nav_host_fragment;
//
//        // Create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//        // Create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
//        fragmentTransaction.replace(idOfNavHostUI, fragmentName);
//        fragmentTransaction.commit(); // save the changes
//    }





    /*
     *      *
     * The Function is created to get the SINGLE vehicle data that the user requested. It will look through
     * The database Reference and get the item information that has the same make,model and year.
     * This function will Send the user to the specific cars page and will pass the Specific Vehicle
     * Information received from the Database
     *
     * This function will return a Car Object
     */
//    public static Car getSpecificCarInfo(Car car) {
//        //DatabaseReference getValueFromDb =  FirebaseDatabase.getInstance().getReference().child("Vehicle");
//        listener = vehicleReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                /*
//                 *   This method is used to get the vehicle information from the users selection.
//
//                 *   Will Return:
//                 *      car OBJECT!
//                 *      Description         <String>
//                 *      Recalls             [List]
//                 *      Category            <String>
//                 *      Drivetrain          <String>
//                 *      Cylinders           <int>
//                 *      CommonProblems      [List]
//                 *      Doors               <int>
//                 *      Engine              <String>
//                 *      Horsepower          <int>
//                 *      MPG                 <int>
//                 *      Price               <int>
//                 *      Seats               <int>
//                 */
//                String carMake = car.getMake();
//                String carModel = car.getModel() ;
//                String carTrim = car.getTrim();
//                String carYear = car.getYear() + "";
//
//                //Create a Car Model for the descriptions
//                for (DataSnapshot ssCarDesc : dataSnapshot.child(carMake).child(carModel).child(carTrim).child(carYear).getChildren()) {
//                    //The Key of each description (Category,CommonProblems,Description...)
//                    String descName = ssCarDesc.getKey();
//                    //Temporary varible to convert the data from a string, to an int.
//                    int convertToInt = 0;
//                    //Creating the list to contain the Recalls and Common Problems
//                    String[] descArray;
//
//
//                    car.setYear(Integer.parseInt(carYear));
//                    Log.d("getSpecificCarInfo", "Data Snapshot " + carYear);
//
//                    //Check what it is and put it into the correct value
//                    switch (descName) {
//                        case "Category":
//                            //convert to list and store in Category. (for futureproofing)
//
//                            car.setCategory(ssCarDesc.getValue().toString());
//                            break;
//                        case "CommonProblems":
//                            //Debug
//                            Log.d("FindSpecificModel", "The Car information for Common Problems is" + ssCarDesc.getValue().toString());
//                            //Convert the string into a list
//                            descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
//                            car.setCommonProblems(descArray);
//                            break;
//                        case "Ratings":
//                            //Debug
//                            //Convert the string into a list
//                            descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
//                            car.setRatings(descArray);
//                            break;
//                        case "Description":
//                            //Create a Car Model for the descriptions
//                            car.setDescription(ssCarDesc.getValue().toString());
//                            break;
//                        case "Doors":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setDoors(convertToInt);
//                            break;
//                        case "Engine":
//                            //Create a Car Model for the descriptions
//                            car.setEngine(ssCarDesc.getValue().toString());
//                            ;
//                            break;
//                        case "Horsepower":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setHorsePower(convertToInt);
//                            break;
//                        case "MPG":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setMPG(convertToInt);
//                            break;
//                        case "Price":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setPrice(convertToInt);
//                            break;
//                        case "Recalls":
//                            //Convert the string into a list
//                            descArray = ssCarDesc.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
//                            car.setRecalls(descArray);
//                            break;
//                        case "Seats":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setSeats(convertToInt);
//                            break;
//                        case "Drivetrain":
//                            //Convert it to a string then parse for the int
//                            car.setDrivetrain(ssCarDesc.getValue().toString());
//                            break;
//                        case "Cylinders":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setCylinders(convertToInt);
//                            break;
//                        case "Torque ft-lb":
//                            //Convert it to a string then parse for the int
//                            convertToInt = Integer.parseInt(ssCarDesc.getValue().toString());
//                            car.setTorque(convertToInt);
//                            break;
//                        default:
//                            //Debug
//                            Log.d("NoModelFound", ssCarDesc.getValue().toString());
//
//                    }//End Of Switch
//                } // End Of Data Snapshot
//            }// End of OnDataChanged
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        }); //End of Listener
//        return car;
//    }

}
