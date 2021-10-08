package owen.ross.carguru.Models;

public class Answer {

    private String description;
    private String value;

    // feel free to make this an empty constructor if it makes adding data easier
    public Answer(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
