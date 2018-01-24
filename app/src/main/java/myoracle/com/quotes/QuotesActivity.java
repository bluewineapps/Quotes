package myoracle.com.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import myoracle.com.quotes.adapter.QuotesAdapter;
import myoracle.com.quotes.model.Quote;

/**
 * Created by Midhun on 19-04-2017.
 */

public class QuotesActivity extends AppCompatActivity {

    public static final String TAG = QuotesActivity.class.getSimpleName();
    private static QuotesActivity quotesActivity;

    public static int details_count =0;
   // InterstitialAd mInterstitialAd;

    private Toolbar toolbar;
    ViewPager viewPager;
    QuotesAdapter quotesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        ArrayList<Quote> quoteList = (ArrayList<Quote>) bundle.get("quotes");
        Integer index = (Integer) bundle.get("index");
        setContentView(R.layout.activity_quotes);
        Toast.makeText(this,"Long Click Copy", Toast.LENGTH_SHORT).show();


        toolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.quotesAdapter = new QuotesAdapter(this, quoteList,index);
        this.viewPager.setAdapter(quotesAdapter);
        quotesActivity = this;

//        AnalyticsTrackers.initialize(this);
//        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
//
//     //   AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
//
//        AdView mAdView = (AdView) findViewById(R.id.adViewDetails);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotes_cell_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quote_share:

                TextView textView = (TextView) findViewById(R.id.quotesview);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textView.getText());
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                startActivity(sendIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }


    public static synchronized QuotesActivity getInstance() {
        return quotesActivity;
    }

//    public synchronized Tracker getGoogleAnalyticsTracker() {
//        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//    }
}
