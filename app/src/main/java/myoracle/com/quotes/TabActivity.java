package myoracle.com.quotes;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.analytics.Tracker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.kobakei.ratethisapp.RateThisApp;

import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import myoracle.com.quotes.adapter.MainPagerAdapter;
import myoracle.com.quotes.notification.NotificationPublisher;


/**
 * Created by Midhun on 27-10-2017.
 */

public class TabActivity extends AppCompatActivity implements InMobiInterstitial.InterstitialAdRequestListener{

    public static final String TAG = TabActivity.class.getSimpleName();
    private static TabActivity mInstance;
    private static int add_count =0;
    AlertDialog.Builder builder;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager pager;

    MainPagerAdapter adapter;
    CharSequence Titles[] = {"WINES","WALLPAPERS","QUOTES","MIND TRICKS","STORIES"};
    CoordinatorLayout coordinatorLayout;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);



        InMobiBanner bannerAd = new InMobiBanner(this, 1540093933823L);
        RelativeLayout adContainer = findViewById(R.id.banner2);
        float density = getResources().getDisplayMetrics().density;
        RelativeLayout.LayoutParams bannerLp = new RelativeLayout.LayoutParams((int) (620 * density), (int) (50 * density));
        adContainer.addView(bannerAd, bannerLp);
        bannerAd.load();


        PrefManager prefManager = new PrefManager(getApplicationContext());
        builder = new AlertDialog.Builder(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

       // scheduleNotification(getNotification("5 second delay"), 600000);



        if(prefManager.isSignUp() && !prefManager.isWinesFirstTimeLaunch() &&(FirebaseAuth.getInstance().getCurrentUser()==null)){
            prefManager.setSignUp(false);
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        }

        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.tab_coordinator);
        setUpTabs();

        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();


        mInstance = this;

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

        // Monitor launch times and interval from installation
        RateThisApp.onCreate(this);
        RateThisApp.Config config = new RateThisApp.Config(3, 2);
        RateThisApp.init(config);
        // If the condition is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);


        add_count++;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected()))) {
            Toast.makeText(getApplicationContext(), "You can read only Quotes.. No Internet Connection Now !!!", Toast.LENGTH_LONG).show();


            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

        if (prefManager.isWinesFirstTimeLaunch()) {
            prefManager.setWinesFirstTimeLaunch(false);
            builder.setMessage("Hai, We are trying to improve your experience, Added user interactions . Features called Wines. ")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            TabLayout.Tab tab = tabs.getTabAt(0);
                            tab.select();

                        }
                    });

            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(" Happily Introducing Wines ..");
            alert.show();
        }


        final InMobiInterstitial interstitial = new InMobiInterstitial(this,1538886935874L, new InMobiInterstitial.InterstitialAdListener2() {
            @Override
            public void onAdRewardActionCompleted(InMobiInterstitial ad, Map rewards) {}
            @Override
            public void onAdDisplayed(InMobiInterstitial ad) {
                Log.d(TAG, "onAdDisplayed: ");
            }
            @Override
            public void onAdDismissed(InMobiInterstitial ad) {
                Log.d(TAG, "onAdDismissed: ");
            }
            @Override
            public void onAdInteraction(InMobiInterstitial ad, Map params) {}
            @Override
            public void onAdLoadSucceeded(final InMobiInterstitial ad) {
                Log.d(TAG, "onAdLoadSucceeded: ");
            }

            @Override
            public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdDisplayFailed: ");
            }

            @Override
            public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdWillDisplay: ");
            }

            @Override
            public void onAdLoadFailed(InMobiInterstitial ad, InMobiAdRequestStatus requestStatus) {
                Log.d(TAG, "onAdLoadFailed: " + requestStatus.getMessage());
            }

            @Override
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdReceived: ");
            }

            @Override
            public void onUserLeftApplication(InMobiInterstitial ad){
                Log.d(TAG, "onUserLeftApplication: ");
            }
        });
        interstitial.load();

        int secondsDelayed = 10;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(interstitial.isReady())
                    interstitial.show();
            }
        }, secondsDelayed * 1500);

    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {

        super.onPause();
    }



    public static synchronized TabActivity getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }





    void setUpTabs() {
        adapter = new MainPagerAdapter(this.getSupportFragmentManager(), Titles, Titles.length);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        setupTabIcons();
    }

    private void setupTabIcons() {
    //        tabs.getTabAt(0).setIcon(R.mipmap.ic_launcher);
    //        tabs.getTabAt(1).setIcon(R.mipmap.ic_launcher);
    //        tabs.getTabAt(2).setIcon(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Toast.makeText(getApplicationContext(),R.string.share, Toast.LENGTH_SHORT).show();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "More than 3000 Selected Quotes Download Now : https://play.google.com/store/apps/details?id=myoracle.com.quotes");
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                startActivity(sendIntent);
                return true;
            case R.id.action_rate_us:
                launchMarket();
                return true;
            case R.id.action_rate_feedback:
                Intent myIntent= new Intent(getApplicationContext(), AppFeedbackActivity.class);
                startActivity(myIntent);
                return true;
            case  R.id.privacy_policy:
                Intent myIntents= new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(myIntents);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }



    private void scheduleNotification(Notification notification, int delay) {

        System.out.println("----------------------------- Sheduller started -----------------");
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(getRandomMsg());
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.logo);

        return builder.build();
    }


    public CharSequence getRandomMsg() {

        String[] quotesArray =getResources().getStringArray(R.array.shortQuotes);
        String[] contentQuote = quotesArray[new Random().nextInt(quotesArray.length)].split("\\n");
        return contentQuote[0];
    }


    @Override
    public void onAdRequestCompleted(InMobiAdRequestStatus inMobiAdRequestStatus, InMobiInterstitial inMobiInterstitial) {

    }
}
