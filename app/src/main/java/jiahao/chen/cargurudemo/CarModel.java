package jiahao.chen.cargurudemo;

import java.time.Year;
import java.util.List;

public class CarModel {

    public String Make;
    public String Model;
    public String Year;

    public String Category;
    public List<String> CommonProblems;
    public List<String> Recalls;
    public String Description;
    public int Doors;
    public int MPG;
    public int HorsePower;
    public String Engine;
    public int Seats;

    public CarModel() {
    }

    public CarModel(String model, int MPG, String engine, int seats, int year) {
        this.MPG = MPG;
        Engine = engine;
        Seats = seats;
    }

    //public String getModel() {
    //    return Model;
    //}

    //public void setModel(String model) {
    //    Model = model;
    //}

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

    //public int getYear() {
     //   return Year;
    //}

    //public void setYear(int year) {
      //  Year = year;
    //}
}
