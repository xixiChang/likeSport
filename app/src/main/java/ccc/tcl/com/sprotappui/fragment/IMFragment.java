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
import android.widget.AdapterView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.DayRateActivity;
import ccc.tcl.com.sprotappui.activity.MySportTeamActivity;
import ccc.tcl.com.sprotappui.activity.ScoreActivity;
import ccc.tcl.com.sprotappui.adapter.ChooseItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;


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
