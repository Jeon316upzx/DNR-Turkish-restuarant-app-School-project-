package dnr.dnr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by IFEANYI on 4/4/2018.
 */

//incomplete functionality
public class CartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView cart_img;
    TextView totalprice, quantity,cart_name;
    ItemClicklistener clicklistener;



    public CartAdapter(View itemView) {
        super(itemView);
        cart_img = itemView.findViewById(R.id.cartitemimg);
        totalprice = itemView.findViewById(R.id.cartitemtotalprice);
        quantity = itemView.findViewById(R.id.cartitemquantitybyprice);
        cart_name = itemView.findViewById(R.id.cartitemname);

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
