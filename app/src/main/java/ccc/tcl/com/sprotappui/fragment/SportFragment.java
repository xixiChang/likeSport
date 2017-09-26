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

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.LayoutActivity;
import ccc.tcl.com.sprotappui.adapter.FMSportItem;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;

public class SportFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<PlatFormActivity> platFormActivityList = new ArrayList<>();
    private Context context;
    private String mTitle;
    private FMSportItem adapter;

    public SportFragment() {
    }

    public static SportFragment getInstance(String title) {
        SportFragment sf = new SportFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        initData();
    }

    private void initData() {
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sport, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fm_sport_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        adapter = new FMSportItem(platFormActivityList);
        adapter.setListener(new FMSportItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, LayoutActivity.class);
                intent.putExtra("id",platFormActivityList.get(position).getId());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

}
