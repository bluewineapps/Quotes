package myoracle.com.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;


/**
 * Created by Midhun on 11-11-2017.
 */

public class StoryDetailsActivity extends AppCompatActivity {

    public static final String TAG = StoryDetailsActivity.class.getSimpleName();
    private static StoryDetailsActivity storyDetailsActivity;


    TextView descriptionView;

    TextView moral;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String description=intent.getStringExtra("story");
        Log.v("discription  ",description);

        descriptionView= (TextView) findViewById(R.id.story_dec);
        descriptionView.setText(description);
        //descriptionView.setf

        setTitle(intent.getStringExtra("title"));

        moral= (TextView) findViewById(R.id.story_moral);
        moral.setText(intent.getStringExtra("moral"));


        storyDetailsActivity = this;

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }


    public static synchronized StoryDetailsActivity getInstance() {
        return storyDetailsActivity;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
}
