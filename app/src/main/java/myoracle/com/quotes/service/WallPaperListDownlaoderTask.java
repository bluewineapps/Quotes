package myoracle.com.quotes.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Midhun on 14-11-2017.
 */

public class WallPaperListDownlaoderTask extends AsyncTask<String,Integer,String> {

    OkHttpClient client = new OkHttpClient();



    @Override
    protected String doInBackground(String... params) {

        String url =params[0];

        Request request = new Request.Builder()
                .url(url)
                .build();
        try{
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            return response.body().toString();
        }catch (IOException e){
            Log.v("",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
