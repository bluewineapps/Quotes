package myoracle.com.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.Map;
import java.util.Random;

/**
 * Created by Midhun on 24-04-2017.
 */

public class SplashActivity extends AppCompatActivity implements InMobiBanner.BannerAdListener {

    TextView splashQuotes;
    TextView txtAuthor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        splashQuotes = (TextView) findViewById(R.id.splash_quotes);
        txtAuthor = (TextView) findViewById(R.id.txt_author);
        String[] quotesArray =getResources().getStringArray(R.array.shortQuotes);
        String[] contentQuote = quotesArray[new Random().nextInt(quotesArray.length)].split("\\n");
        Animation fadeIn = new AlphaAnimation(0.0f,1.0f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(5000);
        splashQuotes.setAnimation(fadeIn);
        splashQuotes.setText(contentQuote[0]);

        txtAuthor.setText(contentQuote[1]);
        InMobiSdk.init(this, "b10100781e9448faa652450fe6aea95c");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        InMobiBanner bannerAd = new InMobiBanner(this, 1539335970997L);
        RelativeLayout adContainer = findViewById(R.id.banner);
        float density = getResources().getDisplayMetrics().density;
        RelativeLayout.LayoutParams bannerLp = new RelativeLayout.LayoutParams((int) (520 * density), (int) (50 * density));
        adContainer.addView(bannerAd, bannerLp);
        bannerAd.load();
        bannerAd.setListener(this);
        openActivity();
    }


    private void openActivity() {

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), TabActivity.class));
                finish();
            }
        }, secondsDelayed * 1500);
    }

    @Override
    public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {
       // Toast.makeText(getApplicationContext(),inMobiAdRequestStatus.getMessage(),Toast.LENGTH_LONG).show();
        Log.v("onAdLoadFailed:", "onAdLoadFailed: "+inMobiAdRequestStatus.getMessage());
    }

    @Override
    public void onAdDisplayed(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdDismissed(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {

    }

    @Override
    public void onUserLeftApplication(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {

    }
}
