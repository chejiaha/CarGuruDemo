package jiahao.chen.cargurudemo;

public class CarModel {

    public String Model;
    public int MPG;
    public String Engine;
    public int Seats;
    public int Year;

    public CarModel() {
    }

    public CarModel(String model, int MPG, String engine, int seats, int year) {
        Model = model;
        this.MPG = MPG;
        Engine = engine;
        Seats = seats;
        Year = year;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
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

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
