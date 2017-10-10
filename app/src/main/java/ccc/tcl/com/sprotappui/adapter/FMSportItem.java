package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.flyco.labelview.LabelView;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;


/**
 * Created by user on 17-8-21.
 */

public class FMSportItem extends RecyclerView.Adapter<FMSportItem.ViewHolder> {

    private List<PlatFormActivity> data;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener{
        void onClick(View view, int position);
    }
    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public FMSportItem(List<PlatFormActivity> data) {
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragement_sport, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        PlatFormActivity activity = data.get(position);
        holder.name.setText(activity.getName());
        holder.hotValue.setText(activity.getHot_value());
        if (activity.isNew())
            holder.label.setVisibility(View.VISIBLE);
//        Glide.with(holder.itemView).load(activity.getImage_url()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(holder.itemView, position);
            }
        });
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
