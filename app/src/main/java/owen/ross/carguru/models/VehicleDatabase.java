package owen.ross.carguru.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class VehicleDatabase implements VehicleFirebaseCallback {
    //This References the Vehicle
    private static DatabaseReference vehicleReference = FirebaseDatabase.getInstance().getReference().child("Vehicle");
    private static ValueEventListener listener;


    @Override
    public void onCallback(List<Car> vehicleList) {
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
                vehicleFirebaseCallback.onCallback(vehicleList);
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
     *      *
     * The Function is created to get the SINGLE vehicle data that the user requested. It will look through
     * The database Reference and get the item information that has the same make,model and year.
     * This function will Send the user to the specific cars page and will pass the Specific Vehicle
     * Information received from the Database
     *
     * This function will return a Car Object
     */
    public static Car getSpecificCarInfo(Car car ) {

        String carMake = car.getMake();
        String carModel = car.getModel() ;
        String carTrim = car.getTrim();
        String carYear = car.getYear() + "";
        //Debug
        //Maybe we will have to do a listener for each key we are taking from the database

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
                String carMake = car.getMake();
                String carModel = car.getModel() ;
                String carTrim = car.getTrim();
                String carYear = car.getYear() + "";
                Car tempCar = new Car();
                //Create a Car Model for the descriptions
                for (DataSnapshot ssCarDesc : dataSnapshot.getChildren()) {
                    //The Key of each description (Category,CommonProblems,Description...)
                    String descName = ssCarDesc.getKey();
                    car.setYear(Integer.parseInt(carYear));
                    String descValue =  ssCarDesc.getValue().toString();
                    tempCar = HelperFunctions.getSpecs(descName,descValue, car);


                } // End Of Data Snapshot
            }// End of OnDataChanged

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }); //End of Listener
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

    public static ArrayList<Car> GetAllCarsInCategory(String questionCategory, VehicleFirebaseCallback vehicleFirebaseCallback){
        ArrayList<Car> categoryVehicles = new ArrayList<>();

        listener = vehicleReference.child(questionCategory).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String make = "";
                String model = "";
                String trim = "";
                String year = "";
                String databaseCategory = "";
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
                                //Check if the car is a match to the query we are looking for
                                //setting the year so we can get the data of its children
                                year = carYears.getKey();
                                // adding the car values to the car object
                                car = new Car();
                                car.setMake(make);
                                car.setModel(model);
                                car.setTrim(trim);
                                car.setYear(Integer.parseInt(year));

                                //Going through each of the descriptions of the vehicle, and then adding them to the object.
                                for (DataSnapshot carDesc : snapshot.child(make).child(model).child(trim).child(year).getChildren()) {
                                    //TODO IS it more efficient to Pull all vehicles and then go through each field? or should we keep it and go through each item once it comes out of the db?
                                    //The Key of each description (Category,CommonProblems,Description...)
                                    String descName = carDesc.getKey();
                                    String descValue =  carDesc.getValue().toString();
                                    car = HelperFunctions.getSpecs(descName, descValue, car);
                                    Log.d("car", "Car Details: Make" + make + " \nModel" + model +  " \n Year" +year +  " \n " +  ":DescName " + descName + "\n Desc value" + descValue );
                                    categoryVehicles.add(car);
                                } // End Of Data Snapshot
                            }
                        }
                    }
                }//End of Makes For loop
                // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
                vehicleFirebaseCallback.onCallback(categoryVehicles);
            }//End of OnData changed

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log something if there was an item not found in the description.
                Log.d("QueryListResults", "Problem getting information from the DB");
            }
        });//End of DB reference


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
    public static ArrayList<Car> CategoryAnswerParser(Hashtable<String,String> questionAnswers, String questionCategory){
        ArrayList<Car> allCarsInCategory = new ArrayList<>();
        GetAllCarsInCategory(questionCategory, new VehicleFirebaseCallback() {
            @Override
            public void onCallback(List<Car> vehicleList) {
                try{
                    // checking if the questions linkedlist is already populated with questions
                    if (allCarsInCategory.isEmpty()) {
                        // the questions returned from the database will be added to the list if the list is empty
                        allCarsInCategory.addAll(vehicleList);
                    }
                }catch (Exception err){
                    allCarsInCategory.addAll(vehicleList);
                }
            }
        });
        ArrayList<Car> listofCars = new ArrayList<>();
        // calling the onCallback method from the FirebaseCallback interface to use the arraylist of questions in the QuestionnaireFragment
        //VehicleFirebaseCallback.onCallback(allCarsInCategory);


        // Getting keySets of Hashtable and storing it into Set (only one item of the same type allowed.
        Set<String> setOfKeys = questionAnswers.keySet();

        //all cars in array list is 0. they are not being passed.
        for (Car car : allCarsInCategory ){
            for (String DBField :  setOfKeys) {
                //Switch Going through all possibilities of answers given and make all fields true if they are a valid vehicle.
                //debug
                Log.d("CommuterCategoryParser" , "COMMUTER ANSWER STRING IS!!" +  questionAnswers.get(DBField));
                //User Answer value (Example DBField: "questionAnswer" == EV: "Yes")
                String questionAnswer = questionAnswers.get(questionAnswers.get(DBField));

                //Commuter DBFields
                boolean MPG = false;
                boolean Year = false;
                boolean Convertable = false;
                boolean EV = false;

                //Sports DBFields
                boolean Weight = false;
                boolean GroundClearance = false;
                boolean Cylinders = false;

                //If the item is a commuter
                if (questionCategory.equals("CommuterQuestion")){
                    //Go through every car in the database and check if it is a valid vehicle
                    //Creating all of the variables for each answer.
                    switch(DBField) {
                        case "EV" :
                            if (questionAnswer.equals("Yes")){
                                //If they said yes to an electric car.
                                if(car.getEngine().equals("EV")){
                                    EV = true;
                                }
                                //if the car engine is not EV keep it false.
                            }else{
                                //If the answer to if they want an electric car is no
                                if(!car.getEngine().equals("EV")){
                                    EV = true;
                                }
                            }
                            break;
                        case "MPG":
                            // Check if they care about fuel efficiency
                            if (questionAnswer.equals(">35")){
                                //Check if its above 35 MPG
                                if (car.getMPG() > 35){
                                    MPG = true;
                                }

                            }else if ((questionAnswer.equals("<25"))){
                                if (car.getMPG() < 25){
                                    MPG = true;
                                }

                            }else if((questionAnswer.equals("25-35"))){
                                if (car.getMPG() > 25 && car.getMPG() <35){
                                    MPG = true;
                                }
                            }else{
                                Log.d("CategoryAnswerParser", "\n There was no field for DBField: " + DBField + " With the answer : " + questionAnswer);
                            }
                            break;
                        case "Year":
                            // Check if its in the correct range of years requested
                            if (questionAnswer.equals("<2015")){
                                if (car.getYear() < 2015){
                                    Year = true;
                                }
                            }else if(questionAnswer.equals("All")){
                                //If they decided they want an automatic
                                Year = true;
                            }
                            break;
                        case "Convertible":
                            //TODO This is not correct. We do not have doors so we cannot check if its a convertable.
                            if (questionAnswer.equals("Yes")){
                                if(car.isConvertible() == true) {
                                    Convertable = true;
                                }
                            }else{
                                // They do not want an convertable
                                if (questionAnswer.equals("No")){
                                    if(car.isConvertible() == false) {
                                        Convertable = true;
                                    }
                                }
                            }
                            break;
                        default:
                            // Default will Log a message
                            Log.d("CommuterCategoryParser" , "This Field was not found in the list: Commuter" + DBField);
                            throw new StringIndexOutOfBoundsException("This Field was not found in the list: Commuter" + DBField);
                    }//end of switch

                    // If the vehicle is a valid choice for the users wants, add it to the list. (For Commuter Vehicles)
                    if (EV == true && MPG == true && Year == true && Convertable == true){
                        // Add the vehicle to the Show Vehicle List
                        listofCars.add(car);
                    }

                }else if (questionCategory.equals("SportQuestion")){
                    switch(DBField) {
                        case "Weight":
                            if (questionAnswer.equals("<3000")){
                                //They want a lighter car
                                //Todo Get weight from the cars in the database...
                                if (car.getWeight() < 3000){
                                    //They want a heavier car
                                    Weight = true;
                                }
                            }else if ((questionAnswer.equals(">=3000"))){
                                if (car.getWeight() <= 3000){
                                    Weight = true;
                                }
                            }
                            break;
                        case "GroundClearance":
                            // Figure out how to siphon this into results
                            break;
                        case "Year":
                            // Check if its in the correct range of years requested
                            if (questionAnswer.equals("<2015")){
                                if (car.getYear() < 2015){
                                    Year = true;
                                }
                            }else if(questionAnswer.equals("All")){
                                //If they decided they want an automatic
                                Year = true;
                            }
                            break;
                        case "Convertible":
                            if (questionAnswer.equals("Yes")){
                                if(car.isConvertible() == true) {
                                    Convertable = true;
                                }
                            }else{
                                // They do not want an convertable
                                if (questionAnswer.equals("No")){
                                    if(car.isConvertible() == false) {
                                        Convertable = true;
                                    }
                                }
                            }
                            break;
                        case "Cylinders":
                            if (questionAnswer.equals("<=6")){
                                //They want a lighter car
                                //Todo Get weight from the cars in the database...
                                if (car.getCylinders() <= 6){
                                    //They want a heavier car
                                    Cylinders = true;
                                }
                            }else if ((questionAnswer.equals("8-12"))){
                                if (car.getCylinders() < 12 && car.getCylinders() > 8 ){
                                    Cylinders = true;
                                }
                            }
                            break;
                        default:
                            // Default will Log a message
                            Log.d("CommuterCategoryParser", "This Field was not found in the list Sports: " + DBField);
                            throw new StringIndexOutOfBoundsException("This Field was not found in the list Sports: " + DBField);
                    }
                    // If the vehicle is a valid choice for the users wants, add it to the list. (For Commuter Vehicles)
                    if (Cylinders == true && Weight == true && Year == true ){
                        // Add the vehicle to the Show Vehicle List
                        listofCars.add(car);
                    }
                }else if (questionCategory.equals("LuxuryQuestion")){

                }else if (questionCategory.equals("UtilityQuestion")){

                }else if (questionCategory.equals("BeaterQuestion")){

                }else if (questionCategory.equals("FamilyQuestion")){

                }else{
                    continue;
                }
            }
        }
        return listofCars;
    }





}
