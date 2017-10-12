package ccc.tcl.com.sprotappui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;
import ccc.tcl.com.sprotappui.model.DayRate;
import ccc.tcl.com.sprotappui.model.RateItem;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by user on 17-8-21.
 */

public class DayRateItem extends RecyclerView.Adapter<DayRateItem.ViewHolder> {

    private List<RateItem> data;

    public DayRateItem(List<RateItem> data) {
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_rate, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.rating.setText(data.get(position).getRating() + "");
        Glide.with(holder.itemView).load(data.get(position).getImage_url()).into(holder.image);
        //holder.image.setImageResource(Integer.parseInt(data.get(position).getImage_url()));//will be modified
        holder.name.setText(data.get(position).getName());
        holder.desc.setText(data.get(position).getRetain());
        holder.dist.setText(data.get(position).getStep());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView rating;
        CircleImageView image;
        TextView name, desc, dist;

        ViewHolder(View itemView) {
            super(itemView);
            rating = (TextView) itemView.findViewById(R.id.item_day_rate_rating);
            image = (CircleImageView) itemView.findViewById(R.id.item_day_rate_image);
            name = (TextView) itemView.findViewById(R.id.item_day_rate_name);
            desc = (TextView) itemView.findViewById(R.id.item_day_rate_user_desc);
            dist = (TextView) itemView.findViewById(R.id.item_day_rate_user_distance);
        }
    }
}
