package myoracle.com.quotes.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import myoracle.com.quotes.R;
import myoracle.com.quotes.model.MindTrick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Midhun on 15-01-2018.
 */

public class MindTricksFragmentAdapter extends Fragment implements View.OnClickListener  {

    private int tabId;
    private ViewPager viewPager;
    private MindTricksAdapter mindTricksAdapter;
    private TextView mindtrick_text;
    private TextView mindtrick_answer_text;
    private int index ;
    private ArrayList<MindTrick> mindTricksMain = new ArrayList<>();

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }
    
    public static MindTricksFragmentAdapter newInstance() {
        
        Bundle args = new Bundle();
        
        MindTricksFragmentAdapter fragment = new MindTricksFragmentAdapter();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.mind_tricks_cell, null);

       // Button mindtrick_answer =(Button) root.findViewById(R.id.mindtrick_answer);

        //mindtrick_answer.setOnClickListener(this);

        setUpView(root);
        return root;
    }

    void setUpView(ViewGroup root){

        mindtrick_text = (TextView) root.findViewById(R.id.mindtrick_text);
       // mindtrick_answer_text =(TextView)root.findViewById(R.id.mindtrick_answer_text);
        mindtrick_answer_text.setVisibility(View.INVISIBLE);
        setUPList();
    }

    private void setUPList() {

        new MindTrickDownloader().execute("https://bluewineapps.github.io/mind%20tricks.json");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.mindtrick_answer:
//                mindtrick_answer_text.setVisibility(View.VISIBLE);

//                break;
            default:
                break;
        }

    }

    class MindTrickDownloader extends AsyncTask<String,Integer,JSONArray> {
        private ArrayList<MindTrick> mindTricks = new ArrayList<MindTrick>();


        OkHttpClient okHttpClient = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            mindTricksMain.clear();
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
            mindTricksMain.addAll(mindTricks);
            mindtrick_text.setText(mindTricks.get(index).getQuestion());
        }
    }
}
