package dnr.dnr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by IFEANYI on 4/5/2018.
 */
public class orderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//order holder desigm pattern implementation
    TextView o_name;
    TextView o_total;
    TextView o_date;
    TextView o_status;
    ImageView o_image;

    ItemClicklistener clicklistener;

    public orderHolder(View itemView) {
        super(itemView);

        o_name = itemView.findViewById(R.id.orderItemName);
        o_total = itemView.findViewById(R.id.orderItemtotal);
        o_date = itemView.findViewById(R.id.orderItemDate);
        o_status = itemView.findViewById(R.id.orderItemStatus);
        o_image = itemView.findViewById(R.id.orderImage);
        itemView.setOnClickListener(this);
    }


    public void setClicklistener(ItemClicklistener clicklistener) {
        this.clicklistener = clicklistener;
    }

    @Override
    public void onClick(View view) {
        clicklistener.onClick(view,getAdapterPosition(),false);
    }
}
