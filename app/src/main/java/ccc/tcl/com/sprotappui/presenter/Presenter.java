package ccc.tcl.com.sprotappui.presenter;


import ccc.tcl.com.sprotappui.ui.SportAppView;

/**
 * Created by user on 17-9-13.
 */

public interface Presenter {
    void onCreate();
    void onStart();
    void onStop();
    void onPause();
    void attachView(SportAppView view);
}
