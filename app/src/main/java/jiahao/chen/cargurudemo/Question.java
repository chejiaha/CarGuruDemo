package jiahao.chen.cargurudemo;

import java.util.List;
import java.util.Locale;

public class Question {
    //Question includes: String question;, List<Answers> answers;, List<String> values;
    private String question;
    private List<String> values;
    private String category;
    private List<String> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
