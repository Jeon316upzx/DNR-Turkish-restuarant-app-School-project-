package dnr.dnr;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IFEANYI on 4/5/2018.
 */
public class food_details extends AppCompatActivity {

    //object references
    TextView d_foodname,d_foodprice,d_fooddesc;
    ImageView d_foodimage;
    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton fab;
    DNRuser u;
    MenuItems m;

    ElegantNumberButton enb;
    String mydetailId;
    String dt;
//database object references
    FirebaseDatabase database;
    DatabaseReference ref,myuser,order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        //database initialization
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("submenu");
        myuser = database.getReference("users");
        order = database.getReference("orders");

//connecting java object references to xml components
        enb = (ElegantNumberButton) findViewById(R.id.elegantnum);//a third party library from github
        fab = (FloatingActionButton) findViewById(R.id.add2Cart);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mycollapsingtb);
        d_foodimage = (ImageView) findViewById(R.id.detailsImage);
        d_foodname = (TextView) findViewById(R.id.detfoodname);
        d_foodprice = (TextView) findViewById(R.id.detailfoodprice);
        d_fooddesc = (TextView) findViewById(R.id.detailfooddesc);

//gettingt the intent from previous activity and checking if its empty
        if(getIntent() != null)
        {
            mydetailId = getIntent().getStringExtra("detailsId");
        }
        if(!mydetailId.isEmpty())
        {
            getDetail(mydetailId);
        }

       //adding order button settings
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog confirm = new Dialog(food_details.this);
                confirm.setContentView(R.layout.confirmdetails);
                confirm.setCancelable(false);
                confirm.show();

                final TextView n = confirm.findViewById(R.id.confirmName);
                final TextView p = confirm.findViewById(R.id.confirmPhone);
                final TextView a = confirm.findViewById(R.id.confirmAddress);
                final TextView t = confirm.findViewById(R.id.confirmTotal);
                final TextView d = confirm.findViewById(R.id.confirmDate);

                Button s = confirm.findViewById(R.id.confirmdetails);
                ImageView c = confirm.findViewById(R.id.close);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirm.dismiss();
                    }
                });
                Calendar calendar  = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                final String mydate = sdf.format(calendar.getTime());

                 myuser.child(Common.currentuser.getName()).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                           u = dataSnapshot.getValue(DNRuser.class);
                         n.setText(u.getName());
                         p.setText(u.getPhone());
                         a.setText(u.getAddress());
                         d.setText(mydate);
                         int ans = Integer.parseInt(enb.getNumber()) * Integer.parseInt(d_foodprice.getText().toString());
                         t.setText(ans+"");
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });


                s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Order or = new Order(u,m,d.getText().toString(),"Placed",t.getText().toString());
                  order.child(Common.currentuser.getName()).setValue(or).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Intent i = new Intent(food_details.this,MainActivity.class);
                          startActivity(i);
                      }
                  });

                    }
                });


            }
        });
    }

    //get the details of the food item
    private void getDetail(String mydetailId) {
        ref.child(mydetailId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                m = dataSnapshot.getValue(MenuItems.class);
                Picasso.with(food_details.this).load(m.getImg_url()).into(d_foodimage);

                d_foodname.setText(m.getMenuitem_title());
                d_foodprice.setText(m.getPrice()+"");
                d_fooddesc.setText(m.getDescription());
                toolbarLayout.setTitle(m.getMenuitem_title());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
