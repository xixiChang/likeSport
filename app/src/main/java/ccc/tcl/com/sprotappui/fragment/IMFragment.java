package ccc.tcl.com.sprotappui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ccc.tcl.com.sprotappui.R;


public class IMFragment extends Fragment {

    public IMFragment() {

    }

    public static IMFragment getInstance() {
        return new IMFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_im, container, false);
    }
}
