package dnr.dnr;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.viewbadger.BadgeView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //database object references
    FirebaseDatabase fdb;
    DatabaseReference dr,myuser;
    //object references
    RecyclerView menu_recycler;
    RecyclerView.LayoutManager manager;
    FirebaseUser user;
    FirebaseAuth auth;

    TextView usernametv;
//navigation view object references
    NavigationView navigationView;
    //firebase adapter
    FirebaseRecyclerAdapter<dnr.dnr.Menu,MenuHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting the title of the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        //Database
        auth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        dr =fdb.getReference("menu");
        myuser = fdb.getReference("users");
         Toast.makeText(this,"gggg",Toast.LENGTH_LONG).show();
        //recycler









        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //data loading
        menu_recycler = (RecyclerView) findViewById(R.id.myRecyclerView);
        menu_recycler.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        menu_recycler.setLayoutManager(manager);

//loading the main menu
        loadMenu();
         Intent service = new Intent(MainActivity.this,ListenOrderService.class);
        startService(service);
    }

    private void loadMenu() {

        adapter = new FirebaseRecyclerAdapter<dnr.dnr.Menu, MenuHolder>(dnr.dnr.Menu.class,R.layout.menu_row,MenuHolder.class,dr) {
            @Override
            protected void populateViewHolder(MenuHolder viewHolder, dnr.dnr.Menu model, int position) {
                   viewHolder.menu_textview.setText(model.getTitle());
                Picasso.with(getBaseContext()).load(model.getImg_url()).into(viewHolder.menu_img);

                final dnr.dnr.Menu clicked = model;
                viewHolder.setItemClicklistener(new ItemClicklistener() {
                    @Override
                    public void onClick(View v, int position, boolean onLongclick) {
                        //Snackbar.make(getCurrentFocus(),adapter.getRef(position).getKey(),Snackbar.LENGTH_LONG).show();
                      Intent menuintent = new Intent(MainActivity.this,FoodList.class);
                      menuintent.putExtra("menuid",adapter.getRef(position).getKey());
                      startActivity(menuintent);
                    }
                });
            }
        };

        menu_recycler.setAdapter(adapter);
    }
//handling on back pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //handling the creation of the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  //handling the selection of the items in the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //this is the profile icons functionality
        if (id == R.id.profile) {



                //if nobody dey logged in
                Snackbar.make(getCurrentFocus(),"debugging update nobody dey",Snackbar.LENGTH_LONG).show();
                loadDialog();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDialog() {

        final ProgressDialog pdialog = new ProgressDialog(MainActivity.this);

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.login);
        d.setCancelable(false);
        d.show();


        ImageView close = d.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        TextView reg = d.findViewById(R.id.register);
        final EditText e_email = d.findViewById(R.id.email);
        final EditText e_pass = d.findViewById(R.id.password);

        Button b_login = d.findViewById(R.id.login);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pdialog.setMessage("Logging user in...");
                pdialog.show();

                if(e_email.getText().toString().isEmpty() | e_pass.getText().toString().isEmpty())
                {
                    pdialog.dismiss();
                    Snackbar.make(getCurrentFocus(),"All fields are required",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    LoginUser();
                }
            }

            private void LoginUser() {

//                auth.signInWithEmailAndPassword(e_email.getText().toString(),e_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            pdialog.dismiss();
//                            View v = navigationView.getHeaderView(0) ;
//                            TextView header = v.findViewById(R.id.emailheadertextView);
//                            header.setText(auth.getCurrentUser().getEmail());
//                            //loadname
//                        }
//                        else
//                        {
//                            pdialog.dismiss();
//                            Snackbar.make(getCurrentFocus(),"Login failed",Snackbar.LENGTH_SHORT).show();
//                           //dont loadname
//                        }
//                    }
//                });

                pdialog.setMessage("Logging user in...");
                pdialog.show();
                myuser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DNRuser ruser = dataSnapshot.child(e_email.getText().toString()).getValue(DNRuser.class);
                        if(ruser.getPass().equals(e_pass.getText().toString()))
                        {
                            pdialog.dismiss();
                            d.dismiss();
                            Snackbar.make(getCurrentFocus(),"Success",Snackbar.LENGTH_SHORT).show();
                            Common.currentuser = ruser;
                            View v = navigationView.getHeaderView(0);
                            usernametv = (TextView) v.findViewById(R.id.usernameheadertextView);
                            usernametv.setText(ruser.getName());

                        }
                        else
                        {
                            d.dismiss();
                            pdialog.dismiss();
                            Snackbar.make(getCurrentFocus(),"Failed",Snackbar.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();

                loadRegister();

            }
        });


    }



    private void loadRegister() {
        final Dialog di = new Dialog(this);
        di.setContentView(R.layout.register);
        di.setCancelable(false);
        di.show();

        ImageView regclose = di.findViewById(R.id.regclose);
        regclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                di.dismiss();
            }
        });

            final EditText reg_username = di.findViewById(R.id.regemail);
            final EditText reg_phone =  di.findViewById(R.id.regphone);
            final EditText reg_add = di.findViewById(R.id.regaddress);
            final EditText reg_pass = di.findViewById(R.id.regpassword);
           Button sub = di.findViewById(R.id.register);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(reg_username.getText().toString().isEmpty()| reg_phone.getText().toString().isEmpty()| reg_add.getText().toString().isEmpty()|reg_pass.getText().toString().isEmpty())
                {
                    Snackbar.make(getCurrentFocus(),"All fields are required",Snackbar.LENGTH_SHORT).show();
                }else
                {
//
                    myuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(reg_username.getText().toString()).exists())
                            {
                                Snackbar.make(getCurrentFocus(),"User already exists",Snackbar.LENGTH_SHORT).show();
                            }
                            else
                            {

                                DNRuser dnRuser = new DNRuser(reg_username.getText().toString(),reg_add.getText().toString(),reg_phone.getText().toString(),reg_pass.getText().toString());
                                myuser.child(reg_username.getText().toString()).setValue(dnRuser);
                                Snackbar.make(getCurrentFocus(), "Success", Snackbar.LENGTH_SHORT).show();
                                di.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
//navigation side bar items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           Intent home = new Intent(this,MainActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_orders) {
            Intent order = new Intent(this,myOrder_.class);
            startActivity(order);
        } else if (id == R.id.nav_notify) {

        }  else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
