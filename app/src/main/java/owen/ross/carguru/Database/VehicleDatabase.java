package owen.ross.carguru.Database;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import owen.ross.carguru.Callbacks.VehicleFirebaseCallback;
import owen.ross.carguru.Models.Car;
import owen.ross.carguru.Models.HelperFunctions;

public class VehicleDatabase extends Fragment implements VehicleFirebaseCallback {
    //This References the Vehicle
    private static DatabaseReference vehicleReference = FirebaseDatabase.getInstance().getReference().child("Vehicle");
    private static ValueEventListener listener;

    @Override
    public void onCallbackCarList(ArrayList<Car> vehicleList) {
    }

    @Override
    public void onCallbackStringArrayList(ArrayList<String> vehicleList) {
    }

    @Override
    public void onCallbackCar(Car car) {

    }


    /*
     * This Function is created to get all of the Cars Makes Models and years, and put them in the
     * respective ComboBox's (Spinners). This will populate the public arrays from the class
     * (1. makeList, 2.  modelList, 3. YearList)
     * The selected vehicle information will be passed to the next class.
     */
    public static ArrayList<Car> getMakeModelYear(VehicleFirebaseCallback vehicleFirebaseCallback) {
        //getting all of the items from the Vehicle portion of the database.
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        //List of car Objects so I can pass the one they choose to the .

//        //Creating a List to hold all of the Names of the Make
        ArrayList<String> makeList = new ArrayList<>();
//        //Creating a List to hold all of the Names of the Models
        ArrayList<String> modelList = new ArrayList<>();

        //List of car Objects so I can pass the one they choose to the users.
        ArrayList<Car> vehicleList = new ArrayList<>();

        ValueEventListener listener = vehicleReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 *   This method is used to get the vehicle information from the database.
                 *   The Information is stored into the VehicleList. This list will contain the make,
                 *   model, year and category. This will be used in the onCreate method to look through
                 *   the information and populate the Spinners(ComboBoxes)
                 *
                 *   Will Populate:
                 *       ArrayList<String> makeList ()
                 *   Will Return:
                 *       ArrayList <car> vehicleList (**only make, model, year and category are filled out**)
                 */
                String make = "";
                String model = "";
                String trim = "";
                String year = "";


                for (DataSnapshot ssMake : dataSnapshot.getChildren()) {
                    make = ssMake.getKey();
                    //Adding the Make to the Make List
                    makeList.add(make);

                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot ssModel : dataSnapshot.child(make).getChildren()) {
                        //setting the make so we can iterate through them
                        model = ssModel.getKey();
                        //Go through each year and add the models for that year
                        for (DataSnapshot ssTrim : dataSnapshot.child(make).child(model).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            trim = ssTrim.getKey();
                            //Go through each model of each make
                            for (DataSnapshot ssYear : dataSnapshot.child(make).child(model).child(trim).getChildren()) {
                                Car car = new Car();
                                //Getting the model
                                year = ssYear.getKey();
                                //Adding the make to the car Object
                                car.setMake(make);
                                //add the models name to the car Object
                                car.setModel(model);
                                //add the models name to the car Object
                                car.setTrim(trim);
                                //Get the year and add it to the car Object
                                car.setYear(Integer.parseInt(year));
                                //Check if the model is already in the models array list
                                if (!modelList.contains(model)){
                                    //if it is not in the list add it
                                    modelList.add(model);
                                }
                                //Adding the car to the car List
                                vehicleList.add(car);
                            }
                        }
                    }
                }
                // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
                vehicleFirebaseCallback.onCallbackCarList(vehicleList);
                //Clearing the modelList
                modelList.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return vehicleList;
    }


    /*
     *
     * The Function is created to get the SINGLE vehicle data that the user requested. It will look through
     * The database Reference and get the item information that has the same make,model and year.
     * This function will Send the user to the specific cars page and will pass the Specific Vehicle
     * Information received from the Database
     *
     * This function will return a Car Object
     */
    public static Car getSpecificCarInfo(Car car, VehicleFirebaseCallback stringCallback ) {
        String carMake = car.getMake();
        String carModel = car.getModel() ;
        String carTrim = car.getTrim();
        String carYear = car.getYear() + "";

        listener = vehicleReference.child(carMake).child(carModel).child(carTrim).child(carYear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 *   This method is used to get ONE vehicles information from the users selection.

                 *   Will Return:
                 *      car OBJECT!
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
                 */

                Car tempCar = new Car();
                //Create a Car Model for the descriptions
                for (DataSnapshot ssCarDesc : dataSnapshot.getChildren()) {
                    //The Key of each description (Category,CommonProblems,Description...)
                    String descName = ssCarDesc.getKey();
                    car.setYear(Integer.parseInt(carYear));
                    String descValue =  ssCarDesc.getValue().toString();
                    tempCar = HelperFunctions.getSpecs(descName,descValue, car);
                } // End Of Data Snapshot
                stringCallback.onCallbackCar(tempCar);

            }// End of OnDataChanged

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }); //End of Listener
        // After the car is recived. sleep for 4 seconds so the call to get the data will have enough
        // time to get the data before the program continues
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("getSpecificCarInfo","VEHICLE DATABASE SLEEP DID NOT WORK!");
        }
        return car;
    }

    /*
     *   This method is used to get the vehicle information of all vehicles based on the category
     *   chosen by the user.
     *   The Information is stored into the a Car object and then inside an ArrayList of cars.
     *   as the category that was selected in the previous screen. This will be used in the
     *   onCreate method to look through the information and populate the Spinners(ComboBoxes)
     *
     *   Will Return:
     *      ArrayList<Carl> cars () (**all fields of car should have values**) : All cars in a specified category.
     */




    public static ArrayList<Car> GetAllCarsInCategory(String category, VehicleFirebaseCallback stringCallback){
        ArrayList<Car> categoryVehicles = new ArrayList<>();

        listener = vehicleReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String make = "";
                String model = "";
                String trim = "";
                String year = "";
                //ArrayList<Car> carModels = new ArrayList<>();
                Car car = new Car();

                //Go through each make -> model -> trim -> year -> and Description of each vehicle to create a list of all vehicles in a specific category.
                for (DataSnapshot carMakes : snapshot.getChildren()) {
                    //setting the make so we can iterate through them
                    make = carMakes.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot carModels : snapshot.child(make).getChildren()) {
                        //setting the model so we can iterate through them
                        model = carModels.getKey();
                        for (DataSnapshot carTrims : snapshot.child(make).child(model).getChildren()) {
                            //setting the trim so we can iterate through each year and get the models
                            trim = carTrims.getKey();
                            car.setTrim(trim);
                            for (DataSnapshot carYears : snapshot.child(make).child(model).child(trim).getChildren()) {

                                // only store the cars of the chosen category in vehicle list
                                if (carYears.child("Category").getValue().toString().contains(category)) {
                                    //Check if the car is a match to the query we are looking for
                                    //setting the year so we can get the data of its children
                                    year = carYears.getKey();
                                    // adding the car values to the car object
                                    car = new Car();
                                    car.setMake(make);
                                    car.setModel(model);
                                    car.setTrim(trim);
                                    car.setYear(Integer.parseInt(year));
                                    categoryVehicles.add(car);
                                }
//                                //Going through each of the descriptions of the vehicle, and then adding them to the object.
//                                for (DataSnapshot carDesc : snapshot.child(make).child(model).child(trim).child(year).getChildren()) {
//                                    //TODO IS it more efficient to Pull all vehicles and then go through each field? or should we keep it and go through each item once it comes out of the db?
//                                    //The Key of each description (Category,CommonProblems,Description...)
//                                    String descName = carDesc.getKey();
//                                    String descValue =  carDesc.getValue().toString();
//                                    car = HelperFunctions.getSpecs(descName, descValue, car);
//                                    Log.d("car", "Car Details: Make" + make + " \nModel" + model +  " \n Year" +year +  " \n " +  ":DescName " + descName + "\n Desc value" + descValue );
//                                    categoryVehicles.add(car);
//                                } // End Of Data Snapshot
                            }
                        }
                    }
                }//End of Makes For loop
                // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
//                vehicleFirebaseCallback.onCallbackCarList(categoryVehicles);

                // update callback to store vehicle list
                stringCallback.onCallbackCarList(categoryVehicles);
            }//End of OnData changed

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log something if there was an item not found in the description.
                Log.d("QueryListResults", "Problem getting information from the DB");
            }
        });//End of DB reference


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("getSpecificCarInfo","VEHICLE DATABASE SLEEP DID NOT WORK!");
        }

        return categoryVehicles;
    }

    public static ArrayList<Car> GetAllCarsFromBodyType(String bodyType, VehicleFirebaseCallback stringCallback){
        ArrayList<Car> categoryVehicles = new ArrayList<>();

        listener = vehicleReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String make = "";
                String model = "";
                String trim = "";
                String year = "";
                //ArrayList<Car> carModels = new ArrayList<>();
                Car car = new Car();

                //Go through each make -> model -> trim -> year -> and Description of each vehicle to create a list of all vehicles in a specific category.
                for (DataSnapshot carMakes : snapshot.getChildren()) {
                    //setting the make so we can iterate through them
                    make = carMakes.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot carModels : snapshot.child(make).getChildren()) {
                        //setting the model so we can iterate through them
                        model = carModels.getKey();
                        for (DataSnapshot carTrims : snapshot.child(make).child(model).getChildren()) {
                            //setting the trim so we can iterate through each year and get the models
                            trim = carTrims.getKey();
                            car.setTrim(trim);
                            for (DataSnapshot carYears : snapshot.child(make).child(model).child(trim).getChildren()) {

                                // only store the cars of the chosen category in vehicle list
                                if (carYears.child("Description").getValue().toString().contains(bodyType)) {
                                    //Check if the car is a match to the query we are looking for
                                    //setting the year so we can get the data of its children
                                    year = carYears.getKey();
                                    // adding the car values to the car object
                                    car = new Car();
                                    car.setMake(make);
                                    car.setModel(model);
                                    car.setTrim(trim);
                                    car.setYear(Integer.parseInt(year));
                                    categoryVehicles.add(car);
                                }
//                                //Going through each of the descriptions of the vehicle, and then adding them to the object.
//                                for (DataSnapshot carDesc : snapshot.child(make).child(model).child(trim).child(year).getChildren()) {
//                                    //TODO IS it more efficient to Pull all vehicles and then go through each field? or should we keep it and go through each item once it comes out of the db?
//                                    //The Key of each description (Category,CommonProblems,Description...)
//                                    String descName = carDesc.getKey();
//                                    String descValue =  carDesc.getValue().toString();
//                                    car = HelperFunctions.getSpecs(descName, descValue, car);
//                                    Log.d("car", "Car Details: Make" + make + " \nModel" + model +  " \n Year" +year +  " \n " +  ":DescName " + descName + "\n Desc value" + descValue );
//                                    categoryVehicles.add(car);
//                                } // End Of Data Snapshot
                            }
                        }
                    }
                }//End of Makes For loop
                // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
//                vehicleFirebaseCallback.onCallbackCarList(categoryVehicles);

                // update callback to store vehicle list
                stringCallback.onCallbackCarList(categoryVehicles);
            }//End of OnData changed

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log something if there was an item not found in the description.
                Log.d("QueryListResults", "Problem getting information from the DB");
            }
        });//End of DB reference


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("getSpecificCarInfo","VEHICLE DATABASE SLEEP DID NOT WORK!");
        }

        return categoryVehicles;
    }

    /*
     * This method is used to get and return the string that we will search our database with.
     * To do this, it will check what the vehicle category that the user was placed into
     *
     *   questionCategory <String>: check what the vehicle category that the user was placed into
     *   Hashtable<String,String> questionAnswers: Will hold the { DBField, questionAnswer }
     *      DBField is what we are targeting for when the user answers the question.
     *              So if the question is do you care about gas? it DBField will be MPG
     *      questionAnswer being the answer that the user chose to the question
     *
     * returns A List of Vehicles that the program finds.
     */
    public static ArrayList<Car> CategoryAnswerParser(Hashtable<String,String> questionAnswers, String questionCategory, VehicleFirebaseCallback vehicleFirebaseCallback) {

        ArrayList<Car> returnCarList = new ArrayList<Car>();
        listener = vehicleReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String make = "";
                String model = "";
                String trim = "";
                String year = "";

                Set<String> smallerList = new HashSet<String>();
                Set<String> greaterList = new HashSet<String>();
                Set<String> smallerThanList = new HashSet<String>();
                Set<String> greaterThanList = new HashSet<String>();
                Set<String> allList = new HashSet<String>();
                Set<String> normalList = new HashSet<String>();
                Set<String> containList = new HashSet<String>();
                Set<String> resultList = new HashSet<String>();

                // This variable is the compent we are comparing from the current car in the database and the users queried statement (MPG, horsepower..)
                String comparedValue = "";

                for (DataSnapshot ssMake : dataSnapshot.getChildren()) {
                    make = ssMake.getKey();
                    //Get the makes list Once they get this list then populate the others
                    //for each make in the Given Category (Category is passed)
                    for (DataSnapshot ssModel : dataSnapshot.child(make).getChildren()) {
                        //setting the make so we can iterate through them
                        model = ssModel.getKey();
                        //Go through each year and add the models for that year
                        for (DataSnapshot ssTrim : dataSnapshot.child(make).child(model).getChildren()) {
                            //setting the year so we can iterate through each year and get the models
                            trim = ssTrim.getKey();
                            //Go through each model of each make
                            for (DataSnapshot ssYear : dataSnapshot.child(make).child(model).child(trim).getChildren()) {
                                year = ssYear.getKey();
//                                Log.d("AllCar",make+model+trim+year);
                                if (questionCategory.contains(ssYear.child("Category").getValue().toString())) {
                                    Set<String> setOfKeys = questionAnswers.keySet();

                                    // Iterating questionCategorythrough the Hahstable
                                    for (String DBField : setOfKeys) {

                                        if (DBField.equals("Make")) {
                                            comparedValue = make;
                                        } else if (DBField.equals("Model")) {
                                            comparedValue = model;
                                        } else if (DBField.equals("Trim")) {
                                            comparedValue = trim;
                                        } else if (DBField.equals("Year")) {
                                            comparedValue = year;
                                        } else {
                                            comparedValue = ssYear.child(DBField).getValue().toString();
                                        }
                                        // If the
                                        if (questionAnswers.get(DBField).contains("<=")) {
                                            if (Integer.parseInt(comparedValue) <= Integer.parseInt(questionAnswers.get(DBField).substring(2))) {
                                                smallerThanList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("smallerThan", smallerThanList.toString());
                                            }
                                        } else if (questionAnswers.get(DBField).contains(">=")) {
                                            if (Integer.parseInt(comparedValue) >= Integer.parseInt(questionAnswers.get(DBField).substring(2))) {
                                                greaterThanList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("greaterThan", greaterThanList.toString());
                                            }
                                        } else if (questionAnswers.get(DBField).contains("<")) {
                                            if (Integer.parseInt(comparedValue) < Integer.parseInt(questionAnswers.get(DBField).substring(1))) {
                                                smallerList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("smaller", smallerList.toString());
                                            }
                                        } else if (questionAnswers.get(DBField).contains(">")) {
                                            if (Integer.parseInt(comparedValue) > Integer.parseInt(questionAnswers.get(DBField).substring(1))) {
                                                greaterList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("greater", greaterList.toString());
                                            }
                                        } else if (questionAnswers.get(DBField).contains("-")) {

                                            if (Integer.parseInt(comparedValue) >= Integer.parseInt(questionAnswers.get(DBField).split("-")[0])
                                                    && Integer.parseInt(comparedValue) <= Integer.parseInt(questionAnswers.get(DBField).split("-")[1])) {
                                                allList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("dash", allList.toString());
                                            }
                                        } else if (questionAnswers.get(DBField).equals("All")) {
                                            allList.add(make + "," + model + "," + trim + "," + year);
                                            Log.d("all", allList.toString());
                                        }
//                                        else if (comparedValue.contains(questionAnswers.get(DBField))){
//                                            containList.add(make + " " + model + " " + trim + " " + year);
//                                            Log.d("contain", allList.toString());
//                                        }
                                        else {
                                            if (questionAnswers.get(DBField).equals(comparedValue) || comparedValue.equals("Both")) {
                                                normalList.add(make + "," + model + "," + trim + "," + year);
                                                Log.d("both", normalList.toString());
//                                                if (comparedValue.contains(questionAnswers.get(DBField))){
//                                                    containList.add(make + " " + model + " " + trim + " " + year);
//                                                    Log.d("contain", allList.toString());
//                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                boolean isFirstNonEmptyList = false;
                //TODO Find a way to pass this to the method above..
                Set<String> resultSet = new HashSet<String>();
                if (smallerList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = smallerList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(smallerList);
                    }
                }
                if (greaterList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = greaterList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(greaterList);
                    }
                }
                if (smallerThanList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = smallerThanList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(smallerThanList);
                    }
                }
                if (greaterThanList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = greaterThanList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(greaterThanList);
                    }
                }
                if (allList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = allList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(allList);
                    }
                }
                if (normalList.size() > 0) {
                    if (isFirstNonEmptyList == false) {
                        resultSet = normalList;
                        isFirstNonEmptyList = true;
                    } else {
                        resultSet.retainAll(normalList);
                    }
                }
//                if (containList.size() > 0){
//                    if (isFirstNonEmptyList == false) {
//                        resultSet = containList;
//                    } else {
//                        resultSet.retainAll(containList);
//                    }
//                }

                Log.d("RES", resultSet.toString());
                for (String car : resultSet) {
                    Log.d("Car: ", car);
                    Car carObj = new Car();
                    carObj.setMake(car.split(",")[0]);
                    carObj.setModel(car.split(",")[1]);
                    carObj.setTrim(car.split(",")[2]);
                    carObj.setYear(Integer.parseInt(car.split(",")[3]));
                    returnCarList.add(carObj);
                }

                vehicleFirebaseCallback.onCallbackCarList(returnCarList);
            }// End of OnDataChanged

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }); //End of Listener

        return returnCarList;

    }



}
