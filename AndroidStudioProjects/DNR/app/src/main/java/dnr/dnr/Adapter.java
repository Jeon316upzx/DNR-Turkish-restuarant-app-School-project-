package dnr.dnr;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by IFEANYI on 3/7/2018.
 */

//adapter for handling the slider after the splash screen
public class Adapter extends PagerAdapter {

    //context and inflater object references
    Context c;
    LayoutInflater inf;
//construct
    public Adapter(Context c) {
        this.c  = c;
    }


    //arrays of images and their text content

    public int[] images =
            {
               R.drawable.ic_chef,
               R.drawable.ic_delivery,
               R.drawable.ic_wifi
            };
    public String[] slider_heading = {
        "FOOD",
        "DELIVERY",
        "WIFI"

    };

    public String[] slider_descriptions ={
            "Bringing turkish cuisine to your doorsteps including free first turkish tea",
            "Order and get your food delivered to you with our 24/7 delivery service",
            "Enjoy free WIFI and DSTV as you come to patronize us"
    };

    //get the count of the array
    @Override
    public int getCount() {
        return slider_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==(RelativeLayout) object;
    }

//creating a single page of the slider
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inf = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = inf.inflate(R.layout.activity_getstarted,container,false);

        ImageView img = v.findViewById(R.id.slide_img);
        TextView head = v.findViewById(R.id.slide_title);
        TextView desc = v.findViewById(R.id.slide_desc);

        img.setImageResource(images[position]);
        head.setText(slider_heading[position]);
        desc.setText(slider_descriptions[position]);

        container.addView(v);


        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
