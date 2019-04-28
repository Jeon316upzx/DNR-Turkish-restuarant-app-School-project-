package dnr.dnr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by IFEANYI on 3/21/2018.
 */
public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

//menu holder deign pattern for the main menu
    public TextView menu_textview;
    public ImageView menu_img;


    private ItemClicklistener itemClicklistener;

    public MenuHolder(View itemView) {
        super(itemView);

        menu_textview = itemView.findViewById(R.id.menu_name);
        menu_img = itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClicklistener(ItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    @Override
    public void onClick(View view) {
        itemClicklistener.onClick(view,getAdapterPosition(),false);

    }
}
