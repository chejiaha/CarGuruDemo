package owen.ross.carguru.models;

import com.google.firebase.database.DataSnapshot;

/*
* This class is used for checking if the data was received from the database.
* If the data is recieved the onSuccess Method will be run
* If the data fails the onFailure method will run and we will
*
 */
public interface GetDataListener {
    //this is for callbacks
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
