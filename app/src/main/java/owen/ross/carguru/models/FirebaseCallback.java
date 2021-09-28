package owen.ross.carguru.models;

import java.util.List;

/*
 * This interface will act as a middle man between the database class and the fragment classes.
 * This interface will give us the ability to use values outside of the onDataChange() method,
 * by using this interface we can put all of the Firebase function in a seperate class
 *
 * For more information go here: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
 * or watch this video: https://www.youtube.com/watch?v=OvDZVV5CbQg
 */
public interface FirebaseCallback {
    void onCallback(List<Question> list);
}

