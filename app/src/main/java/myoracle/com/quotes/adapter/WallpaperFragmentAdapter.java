package myoracle.com.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import myoracle.com.quotes.R;
import myoracle.com.quotes.WallpaperDeatilsActivity;
import myoracle.com.quotes.listener.RecyclerTouchListener;
import myoracle.com.quotes.model.Wallpaper;
import myoracle.com.quotes.model.WallpaperMain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Midhun on 13-11-2017.
 */

public class WallpaperFragmentAdapter extends Fragment {



    private List<WallpaperMain> wallpaperList = new ArrayList<WallpaperMain>();
    private List<WallpaperMain> wallpaperMains = new ArrayList<>();
    private WallPaperAdapter wallPaperAdapter;
    RecyclerView recyclerView;

    private int tabId ;

    public void settabId(int tabId){
        this.tabId=tabId;
    }

    public static Fragment newInstance(Context context) {
        WallpaperFragmentAdapter wallpaperFragmentAdapter = new WallpaperFragmentAdapter();
        return wallpaperFragmentAdapter;
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

        new WallPaperListDownlaoderTask().execute("https://raw.githubusercontent.com/bluewineapps/bluewineapps.github.io/master/wallpaper.json");


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        wallPaperAdapter = new WallPaperAdapter(wallpaperList,getActivity());
        recyclerView.setAdapter(wallPaperAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                updateViewCount(wallpaperList.get(position).getId().toString());
                callWallPaperDetails(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void updateViewCount(String position) {

      //  new WallpaperViewUpdate().execute("https://quotesandstatus.herokuapp.com/api/v1/wallpaper/"+position);

    }

    private void callWallPaperDetails(int position) {

        Intent intent;
        intent = new Intent(getActivity(), WallpaperDeatilsActivity.class);
        intent.putExtra("wallpaper",wallpaperList.get(position).getWallpaper().getLarge());
        intent.putExtra("position",position);
        startActivity(intent);
    }


    class WallpaperViewUpdate extends  AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {


            OkHttpClient client = new OkHttpClient();

            String url =strings[0];


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class WallPaperListDownlaoderTask extends AsyncTask<String,Integer,JSONArray> {


        OkHttpClient client = new OkHttpClient();
        @Override
        protected void onPreExecute() {
           wallpaperList.clear();
           super.onPreExecute();

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            String url =params[0];
            System.out.println(url);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try{

                Response response = client.newCall(request).execute();
                String apiResponseString=response.body().string();

                JSONArray jsonArray = new JSONArray(apiResponseString);

                for(int index =0;index<jsonArray.length();index++){
                    try {
                        JSONObject wallpaperMainsJsonObj = jsonArray.getJSONObject(index);
                        WallpaperMain wallpaperMain = new WallpaperMain();
                        wallpaperMain.setId(wallpaperMainsJsonObj.getInt("id"));
                        Wallpaper wallpaper = new Wallpaper();
                        JSONObject wallpaperJsonObj = wallpaperMainsJsonObj.getJSONObject("wallpaper");
                        wallpaper.setLarge(wallpaperJsonObj.getString("large"));
                        wallpaper.setSmall(wallpaperJsonObj.getString("small"));
                        wallpaper.setMedium(wallpaperJsonObj.getString("medium"));

                        wallpaperMain.setWallpaper(wallpaper);
                        wallpaperMains.add(wallpaperMain);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("eror happent in json paesinf");
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
