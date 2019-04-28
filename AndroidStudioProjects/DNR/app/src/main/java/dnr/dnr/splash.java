package dnr.dnr;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    //splash screen implentation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //creationn of a new thread t
        Thread t = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);// the three seconds pause
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //printing the error
                }
                finally {
                    //starting new activity
                    Intent i = new Intent(splash.this,getstarted.class);
                    startActivity(i);
                    finish();
                    try {
                        //killing the previous activity
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }
}
