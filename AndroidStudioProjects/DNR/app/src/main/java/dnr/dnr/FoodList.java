package dnr.dnr;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FoodList extends Activity {
    //object references
    RecyclerView food_recycle;
    FirebaseDatabase food_database;
    DatabaseReference food_reference;

    ActionBar bar;

    String menu_id;

    FirebaseRecyclerAdapter<MenuItems,SubmenuHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

//        bar = getSupportActionBar();
       
                //recycler view initialization
        food_recycle = (RecyclerView) findViewById(R.id.FoodRecycler);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        food_recycle.setHasFixedSize(true);
        food_recycle.setLayoutManager(lm);


        //database instantiation and parent node creation
        food_database  = FirebaseDatabase.getInstance();
        food_reference = food_database.getReference("submenu");


        //getting intent from previous activity and checking if its empty
        if(getIntent()!= null)
        {
            menu_id = getIntent().getStringExtra("menuid").toString();
            Toast.makeText(FoodList.this, "", Toast.LENGTH_SHORT).show();
        }


        if(!menu_id.isEmpty() && menu_id != null)
        {
            loadFoodList(menu_id);
        }



    }


    //loading the food list and comparing the foreign key value with the value stored in the main menu id -->menu_id
    private void loadFoodList(String id) {
        recyclerAdapter = new FirebaseRecyclerAdapter<MenuItems, SubmenuHolder>(MenuItems.class,R.layout.food_row,SubmenuHolder.class,food_reference.orderByChild("fkey").equalTo(id)) {
            @Override
            protected void populateViewHolder(SubmenuHolder viewHolder, MenuItems model, int position) {
                viewHolder.sub_text.setText(model.getMenuitem_title());
                Picasso.with(FoodList.this).load(model.getImg_url()).into(viewHolder.sub_img);

                viewHolder.setClicklistener(new ItemClicklistener() {
                    @Override
                    public void onClick(View v, int pos, boolean isLongclick) {
                         Intent foodet = new Intent(FoodList.this,food_details.class);
                        foodet.putExtra("detailsId",recyclerAdapter.getRef(pos).getKey());
                         startActivity(foodet);

                    }
                });
            }
        };


        //setting the result of the above code to recycler view
        food_recycle.setAdapter(recyclerAdapter);
    }
}
