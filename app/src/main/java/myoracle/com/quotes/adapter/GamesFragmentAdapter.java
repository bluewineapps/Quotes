package myoracle.com.quotes.adapter;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import myoracle.com.quotes.MindTriksQuestionsActivity;
import myoracle.com.quotes.R;
import myoracle.com.quotes.StoryDetailsActivity;
import myoracle.com.quotes.listener.RecyclerTouchListener;
import myoracle.com.quotes.model.Games;
import myoracle.com.quotes.model.Story;
import myoracle.com.quotes.model.Wallpaper;
import myoracle.com.quotes.model.WallpaperMain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Midhun on 19-01-2018.
 */

public class GamesFragmentAdapter extends Fragment  {


    RecyclerView recyclerView;
    List<Games> gamesList=new ArrayList<Games>();

    GamesRecycleAdapter gamesAdapter;

    private int tabId ;

    public void settabId(int tabId){

        this.tabId=tabId;
    }

    public static GamesFragmentAdapter newInstance() {
        
        Bundle args = new Bundle();
        
        GamesFragmentAdapter fragment = new GamesFragmentAdapter();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_main, null);
        setUpView(root);
        return root;
    }

    void setUpView(ViewGroup root){
        recyclerView =(RecyclerView) root.findViewById(R.id.recyclerView);
        setUPList();
    }

    void setUPList(){


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gamesAdapter = new GamesRecycleAdapter(gamesList,getActivity());
        recyclerView.setAdapter(gamesAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);


       new GamesListDownlaoderTask().execute("https://bluewineapps.github.io/mindtricks/mindtriks.json");

       recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent;
                intent = new Intent(getActivity(), MindTriksQuestionsActivity.class);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    class GamesListDownlaoderTask extends AsyncTask<String,Integer,JSONArray> {

        OkHttpClient client = new OkHttpClient();
        @Override
        protected void onPreExecute() {
            gamesList.clear();
            super.onPreExecute();

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            String url =params[0];
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try{
                Response response = client.newCall(request).execute();
                String apiResponseString=response.body().string();
                JSONArray jsonArray = new JSONArray(apiResponseString);

                for(int index =0;index<jsonArray.length();index++){
                    try {
                        JSONObject gameJsonObject = jsonArray.getJSONObject(index);
                        Games games = new Games();
                        games.setImage(gameJsonObject.getString("gameImage"));
                        games.setName(gameJsonObject.getString("gameName"));
                        games.setGameData(gameJsonObject.getString("gameFile"));

                    } catch (JSONException e) {
                        Log.v("",e.getMessage());
                    }

                }
                return null;

            }catch (Exception e){
                Log.v("",e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            super.onPostExecute(jsonArray);
            wallpaperList.addAll(wallpaperMains);
            recyclerView.setAdapter(wallPaperAdapter);
            wallPaperAdapter.notifyDataSetChanged();

        }
    }


}
