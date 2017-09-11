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

    public ChooseItem(List<ChooseItemModel> data) {
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_chooses, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.item_icon.setImageResource(data.get(position).getIconId());
            holder.item_name.setText(data.get(position).getItemName());

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
