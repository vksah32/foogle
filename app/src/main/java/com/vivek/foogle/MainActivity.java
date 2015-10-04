package com.vivek.foogle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start service
        createNotification(this, "Lous");
        // minimize window

    }

    public void createNotification(Context context, String restaurant) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher,
                "Message received", System.currentTimeMillis());
        // Hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //adding LED lights to notification
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        Intent intent = new Intent("android.intent.action.VIEW",
                Uri.parse("http://my.example.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        notification.setLatestEventInfo(context, "Message",
                "New message received", pendingIntent);
        notificationManager.notify(0, notification);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "lalallal",Toast.LENGTH_SHORT).show();
        startService(new Intent(getBaseContext(), MyService.class));

    }
}
