package myoracle.com.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

/**
 * Created by Midhun on 24-04-2017.
 */

public class SplashActivity extends AppCompatActivity {

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

        AdView mAdView = (AdView) findViewById(R.id.adViewSplash);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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

}
