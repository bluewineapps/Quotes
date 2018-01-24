package myoracle.com.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import myoracle.com.quotes.R;
import myoracle.com.quotes.StoryDetailsActivity;
import myoracle.com.quotes.listener.RecyclerTouchListener;
import myoracle.com.quotes.model.Story;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Midhun on 07-11-2017.
 */

public class StoriesFragmentAdapter extends Fragment {


    RecyclerView recyclerView;

    List<Story> storyList=new ArrayList<Story>();
    List<Story> storyListNew =new ArrayList<Story>();
    CommonRecycleAdapter storiesAdapter;

    private int tabId ;

    public void settabId(int tabId){
        this.tabId=tabId;
    }

    public static Fragment newInstance(Context context) {
        StoriesFragmentAdapter f = new StoriesFragmentAdapter();
        return f;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        new GetStoryJsonData().execute();
        storiesAdapter = new CommonRecycleAdapter(storyList,getActivity());
        recyclerView.setAdapter(storiesAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),  LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Story story = storyList.get(position);
                Intent intent;
                intent = new Intent(getActivity(), StoryDetailsActivity.class);
                intent.putExtra("title",story.getTitle());
                intent.putExtra("story", story.getDescription());
                intent.putExtra("moral", story.getMoral());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    class GetStoryJsonData extends AsyncTask<String,Void,List<Story>> {


        @Override
        protected List<Story> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();


            String url = "https://bluewineapps.github.io/story/story.json";


            Request request = new Request.Builder()
                    .url(url).build();

            try {
                Response response = client.newCall(request).execute();

                String inputJsonString = response.body().string();
                try {
                    JSONArray storiesJsonArray = new JSONArray(inputJsonString);

                    for (int i=0;i<storiesJsonArray.length();i++){
                        JSONObject storiesJsonObject = storiesJsonArray.getJSONObject(i);
                        Story story = new Story();
                        story.setDescription(storiesJsonObject.getString("description"));
                        story.setMoral(storiesJsonObject.getString("moral"));
                        story.setStoryID(storiesJsonObject.getInt("id"));
                        story.setTitle(storiesJsonObject.getString("title"));
                        story.setUrl(storiesJsonObject.getString("img"));
                        storyListNew.add(story);
                    }



                } catch (JSONException e) {

                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Story> stories) {
            super.onPostExecute(stories);
            storyList.addAll(storyListNew);
            recyclerView.setAdapter(storiesAdapter);
            storiesAdapter.notifyDataSetChanged();
        }
    }




}
