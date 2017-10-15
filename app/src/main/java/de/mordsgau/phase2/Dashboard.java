package de.mordsgau.phase2;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.mordsgau.phase2.adapter.SectionsPagerAdapter;

public class Dashboard extends AppCompatActivity {

    /* Fragment indices */
    public static final int RECENT = 0;
    public static final int STATISTICS = 1;
    public static final int SETTINGS = 2;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        runNotificationWatchdog(executorService);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(mSectionsPagerAdapter);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://images.unsplash.com/photo-1467380119941-dc5acf7c6325?dpr=1&auto=compress,format&fit=crop&w=2853&h=&q=80&cs=tinysrgb&crop=");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://images.unsplash.com/reserve/wPCyys8TPCHY3GXm2N2D_ssp_inthewoods_1.jpg?dpr=1&auto=compress,format&fit=crop&w=1650&h=&q=80&cs=tinysrgb&crop=");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://images.unsplash.com/photo-1466428996289-fb355538da1b?dpr=1&auto=compress,format&fit=crop&w=2850&h=&q=80&cs=tinysrgb&crop=");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }

    @NonNull
    private Future<?> runNotificationWatchdog(ExecutorService executorService) {
        return executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final MqttClient subscriber = new MqttClient("tcp://www.mordsgau.de:1883", "example_subscriber", new MemoryPersistence());
                    subscriber.setCallback(new MqttCallback() {

                        @Override
                        public void connectionLost(Throwable cause) {
                            Log.e("ERROR", "lost connection");
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.d(Dashboard.class.getName(), "Received message: " + message);
                            final String[] topicSplit = topic.split("/");
                            final String titleContext = topicSplit[topicSplit.length - 1];
                            final String readableName;
                            switch(titleContext) {
                                case "car_sharing":
                                    readableName = "Car Sharing verfügbar";
                                    break;
                                default:
                                    throw new IllegalArgumentException("Illegal topic name "+titleContext);
                            }
                            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.ic_car_sharing)
                                    .setContentTitle(readableName)
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Eine Route an Ihren Arbeitsplatz über autonomen Bus ist verfügbar\nAbfahrt in 10 Minuten"));
                            int mNotificationId = 001;
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                            //NOOP
                        }
                    });
                    subscriber.connect();
                    Log.i("INFO", "Connected");
                    subscriber.subscribe("example_subscriber/push_notifications/+");
                    synchronized (mViewPager) {
                        try {
                            mViewPager.wait();
                        } catch (InterruptedException e) {
                            Log.e("ERROR", "Exception while waiting: ", e);
                        }
                    }
                } catch (MqttException ex) {
                    Log.e("ERROR", "Exception occurred during MqttClient initialization", ex);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }
}
