package owen.ross.carguru.models;

import java.util.ArrayList;
import java.util.List;

/*
 * The list of vehicles are
 */
public interface VehicleFirebaseCallback {
    void onCallbackCarList(List<Car> vehicleList);

    void onCallbackStringArrayList(ArrayList<String> cars);

    void onCallbackQuestionList(List<Question> list);
}
