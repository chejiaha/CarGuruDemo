package owen.ross.carguru.models;

import java.util.ArrayList;

public class Question {

    //Question includes: String question;, List<Answers> answers;, List<String> values;
    // Question: "The Car you want is?"
    private String question;
    //Category: "Commuter"
    private String category;
    // Values: ["Commuter", "Family", ....]
    private ArrayList<String> values;
    // Answer:
    // will this store the possible answers for the question or the answer the user chose?
    private ArrayList<Answer> answers;


    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


}
