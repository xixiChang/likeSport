package ccc.tcl.com.sprotappui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import ccc.tcl.com.sprotappui.R;

public class SportFragment extends Fragment {

    private Context context;
    private Spinner spinner;
    private static final String TAG = "SportFragment";
    private ListPopupWindow popWnd;


    public SportFragment() {
    }

    public static SportFragment getInstance() {
        return new SportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sport, container, false);
//        initView(view);
        return view;
    }

//    private void initView(View view) {
//        spinner = (Spinner) view.findViewById(R.id.sport_type_spinner);
//        setListener();
//    }

    private void setListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: >>" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
