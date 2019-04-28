package dnr.dnr;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class myOrder_ extends AppCompatActivity {

//database object references
    FirebaseDatabase orderbase;
    DatabaseReference orderRef;

    FirebaseRecyclerAdapter<Order,orderHolder> orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_);

        //database instantiation
        orderbase = FirebaseDatabase.getInstance();
        orderRef = orderbase.getReference("orders");
//getting current user name from the common class
        loadMyOrder(Common.currentuser.getName());



    }

    //loading user order
    private void loadMyOrder(String name) {

        orderAdapter = new FirebaseRecyclerAdapter<Order, orderHolder>(Order.class,R.layout.order_row,orderHolder.class,orderRef) {
            @Override
            protected void populateViewHolder(orderHolder viewHolder, Order model, int position) {

                Picasso.with(myOrder_.this).load(model.getMenuItems().getImg_url()).into(viewHolder.o_image);
                viewHolder.o_name.setText(model.getMenuItems().getMenuitem_title());
                viewHolder.o_status.setText(model.getStatus());
                viewHolder.o_date.setText(model.getOrderdate());
                viewHolder.o_total.setText(model.getTotal());

                final Order o_clicked = model;

                viewHolder.setClicklistener(new ItemClicklistener() {
                    @Override
                    public void onClick(View v, int pos, boolean isLongclick) {
                        Snackbar.make(getCurrentFocus(),o_clicked.getStatus(),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }
}
