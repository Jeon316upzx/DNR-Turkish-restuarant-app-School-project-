package dnr.dnr;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrderService extends Service implements ChildEventListener{

    //a service that is on the look out for notifications
    FirebaseDatabase db;
    DatabaseReference orderListen;
    public ListenOrderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        orderListen = db.getReference("orders");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orderListen.addChildEventListener(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
       Order o = dataSnapshot.getValue(Order.class);
        MyNotify(dataSnapshot.getKey(),o);
    }

    private void MyNotify(String key, Order o) {

        Intent intt = new Intent(getBaseContext(),myOrder_.class);
        intt.putExtra("PhoneNum",o.getUs().getPhone());
        PendingIntent pend = PendingIntent.getActivity(getBaseContext(),0,intt,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder not = new NotificationCompat.Builder(getBaseContext());
        not.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("DNR")
                .setContentInfo("Your Order has been updated")
                .setContentText("Order by" + key + "updated")
                .setContentIntent(pend)
        .setContentInfo("DNR")
        .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,not.build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
