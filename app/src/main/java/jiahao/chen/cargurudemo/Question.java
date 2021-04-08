package jiahao.chen.cargurudemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Question implements Serializable {
    //Question includes: String question;, List<Answers> answers;, List<String> values;
    // Question: "The Car you want is?"
    private String question;
    //Category: "Commuter"
    private String category;
    // Values: ["Commuter", "Family", ....]
    private ArrayList<String> values;
    // Answer:
    private ArrayList<String> answers;



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

}


