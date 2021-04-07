package jiahao.chen.cargurudemo;

import java.io.Serializable;
import java.util.ArrayList;

public class PassArrayListTest implements Serializable {
    private ArrayList<Question> questionList;

    public PassArrayListTest(ArrayList<Question> questionList){
        this.setQuestionList(questionList);
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
