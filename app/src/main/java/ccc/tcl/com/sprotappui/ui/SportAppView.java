package ccc.tcl.com.sprotappui.ui;



/**
 * Created by user on 17-9-13.
 */

public interface SportAppView<T> {
    void onSuccess(T response);
    void onError(String msg);
}
