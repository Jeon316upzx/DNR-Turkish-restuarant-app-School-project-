package dnr.dnr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by IFEANYI on 3/27/2018.
 */
public class SubmenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//submenu holder
    ImageView sub_img;
    TextView sub_text;

    ItemClicklistener clicklistener;

    public SubmenuHolder(View itemView) {
        super(itemView);
        sub_img =itemView.findViewById(R.id.submenu_image);
        sub_text = itemView.findViewById(R.id.submenu_name);

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
