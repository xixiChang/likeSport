package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;


/**
 * Created by user on 17-8-21.
 */

public class ChooseItem extends RecyclerView.Adapter<ChooseItem.ViewHolder> {

    private List<ChooseItemModel> data;
    private OnRecyclerViewItemClickListener listener;

    public ChooseItem(List<ChooseItemModel> data) {
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
                .inflate(R.layout.item_user_chooses, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.item_icon.setImageResource(data.get(position).getIconId());
            holder.item_name.setText(data.get(position).getItemName());
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
        ImageView item_icon;
        TextView item_name;

        ViewHolder(View itemView) {
            super(itemView);
            item_icon = (ImageView) itemView.findViewById(R.id.choose_item_icon);
            item_name = (TextView) itemView.findViewById(R.id.choose_item_text);
        }
    }
}
