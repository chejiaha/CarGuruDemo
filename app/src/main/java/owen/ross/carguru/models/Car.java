package owen.ross.carguru.models;

import java.io.Serializable;

public class Car implements Serializable {

    // do we need all of these variables?
    // should we use a dictionary like Kevin suggested?
    private String Make;
    private String Model;
    private int Year;
    private String Category;
    private String[] CommonProblems;
    private String[]  Recalls;
    private String[]  Ratings;
    private String Description;
    private int Doors;
    private int MPG;
    private int HorsePower;
    private String Engine;
    private int Seats;
    private int Price;
    private String Trim;
    private int Cylinders;
    private String Drivetrain;
    private int Torque;
    private boolean Convertible;

    public Car() {
    }

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

    public String[] getCommonProblems() {
        return CommonProblems;
    }

    public void setCommonProblems(String[] commonProblems) {
        CommonProblems = commonProblems;
    }

    public String[] getRecalls() {
        return Recalls;
    }

    public void setRecalls(String[] recalls) {
        Recalls = recalls;
    }

    public String[] getRatings() {
        return Ratings;
    }

    public void setRatings(String[] ratings) {
        Ratings = ratings;
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

    public int getMPG() {
        return MPG;
    }

    public void setMPG(int MPG) {
        this.MPG = MPG;
    }

    public int getHorsePower() {
        return HorsePower;
    }

    public void setHorsePower(int horsePower) {
        HorsePower = horsePower;
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

    public boolean isConvertible() {
        return Convertible;
    }

    public void setConvertible(boolean convertible) {
        Convertible = convertible;
    }
}
