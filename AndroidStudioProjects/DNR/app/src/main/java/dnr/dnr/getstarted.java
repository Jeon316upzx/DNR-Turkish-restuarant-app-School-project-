package dnr.dnr;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class getstarted extends Activity {

//object refernces for everything on the slider page
    ViewPager pager;
    LinearLayout layout;
    Adapter adapter;

    TextView [] mydots;

    Button nextbt,prebt;
    int currentPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
//class, xml connection
        pager = (ViewPager) findViewById(R.id.slider);
        layout = (LinearLayout) findViewById(R.id.dots);

        nextbt = findViewById(R.id.next);
        prebt = findViewById(R.id.previous);


        adapter = new Adapter(getstarted.this);

        pager.setAdapter(adapter);
        adddots(0);


        pager.addOnPageChangeListener(list);

        pager.addOnPageChangeListener(list);

        nextbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nextbt.getText() == "Finish")
                {
                   Intent i = new Intent(getstarted.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    try {
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                else {
                    pager.setCurrentItem(currentPage + 1);
                }
            }
        });

        prebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(currentPage - 1);
            }
        });






    }

    //adding the black dots on the page
    public void adddots(int pos)
    {
       mydots = new TextView[3];
        layout.removeAllViews();
        for(int i =0; i < mydots.length; i++)
        {
            mydots[i] = new TextView(this);
            mydots[i].setText(Html.fromHtml("&#8226;"));
            mydots[i].setTextSize(35);
            mydots[i].setTextColor(getResources().getColor(R.color.colordots));
            layout.addView(mydots[i]);
        }

        if(mydots.length > 0)
        {
            mydots[pos].setTextColor(getResources().getColor(R.color.colordotsblack));
        }


    }

    ViewPager.OnPageChangeListener list = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
             adddots(position);
            currentPage = position;
            if(position == 0)
            {
                nextbt.setEnabled(true);
                prebt.setEnabled(false);
                prebt.setVisibility(View.INVISIBLE);

                nextbt.setText("Next");
                prebt.setText("");
            } else if(position == mydots.length - 1)
            {
                nextbt.setEnabled(true);
                prebt.setEnabled(true);
                prebt.setVisibility(View.VISIBLE);

                nextbt.setText("Finish");
                prebt.setText("Back");
            }
            else
            {
                nextbt.setEnabled(true);
                prebt.setEnabled(true);
                prebt.setVisibility(View.VISIBLE);

                nextbt.setText("Next");
                prebt.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



}
