package com.vivek.foogle;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ParseException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;





import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private NotificationCompat.Builder mBuilder;


    LocationRequest mLocationRequest;
    private ArrayList<ParseObject> mListOfRestaurants = new ArrayList<ParseObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        getAllREstaurants();

        buildGoogleApiClient();
//        if (mGoogleApiClient != null) {
//                mGoogleApiClient.connect();
//            }
        //start service

        mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Foogle")
                        .setContentText("New Menu received!")
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(Notification.CATEGORY_RECOMMENDATION);

        // minimize window
        createNotification(this, "Lous");

    }

    private void getAllREstaurants() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> restaurantList, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + restaurantList.size() + " restaurants");

                    mListOfRestaurants.addAll(restaurantList);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }


        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
    public void createNotification(Context context, String restaurant) {
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(R.mipmap.ic_launcher,
//                "New Menu", System.currentTimeMillis());
//        // Hide the notification after its selected
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        //adding LED lights to notification
//        notification.defaults |= Notification.DEFAULT_LIGHTS;
//
//        Intent intent = new Intent(this,
//                Test.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                intent, 0);
//        notification.setLatestEventInfo(context, "Foogle",
//                "New menu received", pendingIntent);
//        notificationManager.notify(0, notification);
        Intent resultIntent = new Intent(this, Test.class);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
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

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Log.d("DEBUXX","HGCWGHCSHG");
            Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
        }
        createLocationRequest();
        boolean mRequestingLocationUpdates = true;
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override

    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void onLocationChanged(Location location) {
        mLastLocation = location;
//        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
        Toast.makeText(MainActivity.this, "changed location", Toast.LENGTH_SHORT).show();
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude()));
        TextView textView1 = (TextView) findViewById(R.id.textView3);
        TextView textView2 = (TextView) findViewById(R.id.textView4);
//        Location loc1 = new Location("");
//        loc1.setLatitude(43.706306 );
//        loc1.setLongitude(-72.288588);
//
//        Location loc2 = new Location("");
//        loc2.setLatitude(43.706309);
//        loc2.setLongitude(-72.288587);
        for(int i =0; i < mListOfRestaurants.size(); i++){
            Log.d("LOOP",""+i);
            Location dest = new Location("");
            dest.setLatitude((double) mListOfRestaurants.get(i).get("latitude"));
            dest.setLongitude((double) mListOfRestaurants.get(i).get("longitude"));
            double distance = mLastLocation.distanceTo(dest);
            textView1.setText( ""+distance +" " +mListOfRestaurants.get(i).get("Name"));
            Log.d("LOOP",""+mListOfRestaurants.get(i).get("Name"));
            if (distance < (double) mListOfRestaurants.get(i).get("rad")){
                textView2.setText( ""+mListOfRestaurants.get(i).get("Name"));
            }

        }





//        textView2.setText( ""+loc2.distanceTo(mLastLocation));


    }



}
