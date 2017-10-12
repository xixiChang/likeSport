package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.labelview.LabelView;

import org.w3c.dom.Text;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.UserSport;
import ccc.tcl.com.sprotappui.utils.Util;


/**
 * Created by user on 17-8-21.
 */

public class UserSportTeamItem extends RecyclerView.Adapter<UserSportTeamItem.ViewHolder> {

    private List<PlatFormActivity> data;
    private OnRecyclerViewItemClickListener listener;

    public UserSportTeamItem(List<PlatFormActivity> data) {
        this.data = data;
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_sport_team, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.itemView).load(data.get(position).getImage_url()).into(holder.icon);
        holder.name.setText(data.get(position).getName());
        String[] status = {"正常","延期","完成","取消"};
        holder.status.setText(status[Integer.parseInt(data.get(position).getStatus())]);
        holder.time.setText(data.get(position).getStart_time()+" - "+data.get(position).getEnd_time());
        holder.value.setText(data.get(position).getJoin_num()+" 参与");
        String behavior = "已参与";
//        if (data.get(position).getJoiner().contains(data.get(position).getUser_id()))
//        if (Util.stringToList(data.get(position).getJoiner()).contains(data.get(position).getUser_id()))

        if (data.get(position).getUser_id().equals(data.get(position).getPublish_user_id()))
            behavior = "发起人";

        holder.behavior.setText(behavior);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name, status, time, value;
        LabelView behavior;

        ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_user_sport_image);
            name = (TextView) itemView.findViewById(R.id.item_user_sport_name);
            status = (TextView) itemView.findViewById(R.id.item_user_sport_status);
            time = (TextView) itemView.findViewById(R.id.item_user_sport_time);
            value = (TextView) itemView.findViewById(R.id.item_user_sport_value);
            behavior = (LabelView) itemView.findViewById(R.id.item_user_sport_behavior);
        }
    }
}
