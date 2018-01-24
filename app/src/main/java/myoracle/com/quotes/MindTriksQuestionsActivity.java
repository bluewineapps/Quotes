package myoracle.com.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import myoracle.com.quotes.adapter.MindTricksAdapter;
import myoracle.com.quotes.adapter.MindTricksFragmentAdapter;
import myoracle.com.quotes.adapter.QuotesAdapter;
import myoracle.com.quotes.model.MindTrick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Midhun on 19-01-2018.
 */

public class MindTriksQuestionsActivity extends AppCompatActivity {

    private int index;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<MindTrick> mindTricks = new ArrayList<>();
    private MindTricksAdapter mindTricksAdapter;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_quotes);


        toolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);
        setTitle("Mind tricks Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.viewPager = (ViewPager) findViewById(R.id.pager);

        this.mindTricksAdapter = new MindTricksAdapter(getApplicationContext(), mindTricks);
        this.viewPager.setAdapter(mindTricksAdapter);
        this.viewPager.setCurrentItem(10,true);



        new MindTrickDownloader().execute("https://bluewineapps.github.io/mind%20tricks.json");
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    class MindTrickDownloader extends AsyncTask<String,Integer,JSONArray> {
        private ArrayList<MindTrick> mindTrickList = new ArrayList<MindTrick>();
        PrefManager prefManager = new PrefManager(getApplicationContext());
        int item =prefManager.getMindTrickQuestionNo();
        OkHttpClient okHttpClient = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            mindTrickList.clear();
            super.onPreExecute();

        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            String url =strings[0];
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                String apiStringResponse = response.body().string();
                JSONArray jsonArray = new JSONArray(apiStringResponse);

                for (int index =0;index<jsonArray.length();index++){
                    MindTrick mindTrick = new MindTrick();
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    mindTrick.setAnswer(jsonObject.getString("a"));
                    mindTrick.setQuestion(jsonObject.getString("q"));
                    mindTricks.add(mindTrick);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            super.onPostExecute(jsonArray);

            mindTricks.addAll(mindTrickList);
            viewPager.setAdapter(mindTricksAdapter);
            viewPager.setCurrentItem(item);
            mindTricksAdapter.notifyDataSetChanged();


        }
    }
}
