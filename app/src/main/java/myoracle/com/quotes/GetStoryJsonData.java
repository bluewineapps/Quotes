package myoracle.com.quotes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import myoracle.com.quotes.model.Story;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Midhun on 30-10-2017.
 */

public class GetStoryJsonData extends AsyncTask<String,Void,List<Story>> {


    @Override
    protected List<Story> doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();


        String url = "https://bluewineapps.github.io/story";


        Request request = new Request.Builder()
                .url(url).build();

        try {
            Response response = client.newCall(request).execute();
            Log.v("- RESPONSE -",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
