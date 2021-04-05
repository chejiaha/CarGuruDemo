package jiahao.chen.cargurudemo;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

/*
 * This class must implement Serializable so it can be passed from one page to another
 * This class is used to create a carmodel object that will store a vehicles information.
 */
public class CarModel implements Serializable {

    public String Make;
    public String Model;
    public int Year;
    public String Category;
    public String[] CommonProblems;
    public String[]  Recalls;
    private String[]  Ratings;
    public String Description;
    public int Doors;
    public int MPG;
    public int HorsePower;
    public String Engine;
    public int Seats;
    public int Price;
    public String Trim;
    private int Cylinders;
    private String Drivetrain;
    private int Torque;

    public CarModel() {
    }

    public CarModel(String model, int MPG, String engine, int seats, int year) {
        this.MPG = MPG;
        Engine = engine;
        Seats = seats;
    }
    //Creating Getters and setters for all attributes

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String[]  getCommonProblems() {
        return CommonProblems;
    }

    public void setCommonProblems(String[]  commonProblems) {
        CommonProblems = commonProblems;
    }

    public String[]  getRecalls() {
        return Recalls;
    }

    public void setRecalls(String[]  recalls) {
        Recalls = recalls;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDoors() {
        return Doors;
    }

    public void setDoors(int doors) {
        Doors = doors;
    }

    public int getHorsePower() {
        return HorsePower;
    }

    public void setHorsePower(int horsePower) {
        HorsePower = horsePower;
    }

    public int getMPG() {
        return MPG;
    }

    public void setMPG(int MPG) {
        this.MPG = MPG;
    }

    public String getEngine() {
        return Engine;
    }

    public void setEngine(String engine) {
        Engine = engine;
    }

    public int getSeats() {
        return Seats;
    }

    public void setSeats(int seats) {
        Seats = seats;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getTrim() {
        return Trim;
    }

    public void setTrim(String trim) {
        Trim = trim;
    }

    public String[] getRatings() {
        return Ratings;
    }

    public void setRatings(String[] ratings) {
        Ratings = ratings;
    }

    public int getCylinders() {
        return Cylinders;
    }

    public void setCylinders(int cylinders) {
        Cylinders = cylinders;
    }

    public String getDrivetrain() {
        return Drivetrain;
    }

    public void setDrivetrain(String drivetrain) {
        Drivetrain = drivetrain;
    }

    public int getTorque() {
        return Torque;
    }

    public void setTorque(int torque) {
        Torque = torque;
    }

}
