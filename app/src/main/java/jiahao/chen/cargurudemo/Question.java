package jiahao.chen.cargurudemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class Question implements Serializable {
    //Question includes: String question;, List<Answers> answers;, List<String> values;
    private String question;
    private String category;
    private List<String> values;
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




/*
package jiahao.chen.cargurudemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class Question implements Parcelable {
    //Question includes: String question;, List<Answers> answers;, List<String> values;
    private String question;
    private String category;
    private List<String> values;
    private List<String> answers;

    //Empty Constructor
    public Question() { }
    //parcelable constructor
    public Question(Parcel in) {
        question = in.readString();
        category = in.readString();
        values = in.readArrayList(Question.class.getClassLoader());
        answers = in.readArrayList(Question.class.getClassLoader());
    }



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(category);
        dest.writeList(values);
        dest.writeList(answers);

    }


    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


}

*/
