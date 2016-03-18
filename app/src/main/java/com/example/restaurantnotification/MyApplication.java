package com.example.restaurantnotification;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        /*ADD A BEACON MANAGER ---------------------------------------------------------------------

        To enable beacon monitoring, let’s first create ourselves an instance of BeaconManager
        (the gateway to interactions with Estimote Beacons).

        We expect the monitoring in our app to work at all times, no matter which activity is
        currently in use. To achieve that, we’ll instantiate and store our BeaconManager object
        in a subclass of the Application class.

        [NB] Android documentation says this about the Application: Base class for those who need to
        maintain global application state. This is exactly what we need!
        */
        beaconManager = new BeaconManager(getApplicationContext());


        /*START MONITORING -------------------------------------------------------------------------

        Next step, let’s create a beacon region defining our monitoring geofence, and use it to start
        monitoring. For the purposes of this tutorial, we’ll stick to a single beacon, but it’s just
        as easy to target entire groups of beacons by setting the major and/or minor to null.
         */
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            /*
            Keep in mind: When monitoring a region that spans multiple beacons, there will be a single
            “enter” event when the first matching beacon is detected; and a single “exit” event when none
            of the matching beacons can be detected anymore. There’s no way to keep track of “interim”
            beacons’ “enters” and “exits”—other than creating a single region per each beacon of course.
             */

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("Restaurant AL VIALETTO", "Click for the dishes of the day");
            }
            @Override
            public void onExitedRegion(Region region) {
                /* Once the beacon is out of range, it still takes about 30 seconds to truly recognize
                that fact. This built-in, non-adjustable delay is there to prevent false “exit”
                events, e.g., when a crowd temporarily obstructs the beacon and it stops being
                detectable for a few seconds.
                */
            }
        });


        /*
        Use startMonitoring and stopMonitoring to control monitoring. Events are delivered to
        onEnteredRegion and onExitedRegion methods of the MonitoringListener (defined before).
         */
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 17957, 56571));
            }
        });
    }


    // SHOW A NOTIFICATION ------------------------------------------------------------------------

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        //notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notifyIntent.putExtra("notify",1);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_restaurant_menu)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


}
