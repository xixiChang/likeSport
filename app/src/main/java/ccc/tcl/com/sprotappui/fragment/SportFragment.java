package ccc.tcl.com.sprotappui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.LayoutActivity;
import ccc.tcl.com.sprotappui.adapter.FMSportItem;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;

public class SportFragment extends Fragment {



    public SportFragment() {
    }

    public static SportFragment getInstance(String title) {
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
