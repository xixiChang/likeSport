package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.data.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by user on 17-8-21.
 */

public class JoinerItem extends RecyclerView.Adapter<JoinerItem.ViewHolder> {

    private List<UserInfo> data;
    private OnRecyclerViewItemClickListener listener;

    public JoinerItem(List<UserInfo> data) {
        this.data = data;
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }

    public void setListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_joiner, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Glide.with(holder.itemView).load(data.get(position).getImage_url()).into(holder.image);
        holder.name.setText(data.get(position).getName());
        holder.retain.setText(data.get(position).getRetain());

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
        CircleImageView image;
        TextView name, retain;

        ViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.item_ac_joiner_head);
            name = (TextView) itemView.findViewById(R.id.item_ac_joiner_name);
            retain = (TextView) itemView.findViewById(R.id.item_ac_joiner_retain);
        }
    }
}
