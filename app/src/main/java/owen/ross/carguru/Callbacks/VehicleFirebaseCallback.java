package owen.ross.carguru.Callbacks;

import java.util.ArrayList;
import java.util.List;

import owen.ross.carguru.Models.Car;
import owen.ross.carguru.Models.Question;

/*
 * The list of vehicles are
 */
public interface VehicleFirebaseCallback {
    void onCallbackCarList(List<Car> vehicleList);

    void onCallbackStringArrayList(ArrayList<String> cars);

    void onCallbackQuestionList(List<Question> list);
}
