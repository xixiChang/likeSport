package ccc.tcl.com.sprotappui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ccc.tcl.com.sprotappui.R;

public class SportFragment extends Fragment {



    public SportFragment() {
    }

    public static SportFragment getInstance() {
        SportFragment sf = new SportFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sport, container, false);
        return view;
    }


}
