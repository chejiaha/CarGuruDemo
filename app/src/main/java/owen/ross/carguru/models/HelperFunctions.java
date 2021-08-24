package owen.ross.carguru.models;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import owen.ross.carguru.R;

public class HelperFunctions {

//    /*
//     * This method is created to send a fragment from one fragment to the other.
//     *
//     * Takes in:
//     *  Fragment FragmentName : The Fragment That you want to instantiate.
//     *  Bundle bundle         : The bundle with all of the objects already populated.
//     *  int IdOfNavHostUI     : This is used to specify which view you want to replace.
//     *          BY default in our application set to the id of the "navigation_host_fragment" ID
//     *  NOTE:
//     */
//    public void switchFragments (Fragment fragmentName, int idOfNavHostUI, Bundle bundle){
//        //If the bundle is not empty add the argument
//        if (bundle.isEmpty() == false){
//            fragmentName.setArguments(bundle);
//        }
//        // If idOfNavHostUI is null, then set it to the navigation_host_fragment
//        idOfNavHostUI = idOfNavHostUI != 0 ? idOfNavHostUI : R.id.nav_host_fragment;
//
//        // Create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//        // Create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        // Replace the FrameLayout specifying the navigation layout ID and the new Fragment
//        fragmentTransaction.replace(idOfNavHostUI, fragmentName);
//        fragmentTransaction.commit(); // save the changes
//    }



}
