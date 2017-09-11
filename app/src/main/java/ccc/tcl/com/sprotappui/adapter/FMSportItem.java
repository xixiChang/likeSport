package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.labelview.LabelView;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;
import ccc.tcl.com.sprotappui.model.Sport;


/**
 * Created by user on 17-8-21.
 */

public class FMSportItem extends RecyclerView.Adapter<FMSportItem.ViewHolder> {

    private List<Sport> data;

    public FMSportItem(List<Sport> data) {
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragement_sport, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LabelView label;
        TextView name, hotValue, leftTime;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_fm_sport_image);
            label = (LabelView) itemView.findViewById(R.id.item_fm_sport_is_new);
            name = (TextView) itemView.findViewById(R.id.item_fm_sport_name);
            hotValue = (TextView) itemView.findViewById(R.id.item_fm_sport_hot_value);
            leftTime = (TextView) itemView.findViewById(R.id.item_fm_sport_left_time);
        }
    }
}