package myoracle.com.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import myoracle.com.quotes.QuotesCardActivity;
import myoracle.com.quotes.R;
import myoracle.com.quotes.listener.RecyclerTouchListener;
import myoracle.com.quotes.model.Categories;
import myoracle.com.quotes.model.Quote;

/**
 * Created by Midhun on 07-11-2017.
 */

public class QuotesFragmentAdapter extends Fragment {

    private List<Categories> categoriesList = new ArrayList<Categories>();
    private CategoriesAdapter categoriesAdapter;



    RecyclerView recyclerView;

    private int tabId ;



    public void settabId(int tabId){
        this.tabId=tabId;
    }

    public static Fragment newInstance(Context context) {

        QuotesFragmentAdapter f = new QuotesFragmentAdapter();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_main, null);
        setUpView(root);
        return root;
    }

    void setUpView(ViewGroup viewGroup){
        recyclerView=(RecyclerView) viewGroup.findViewById(R.id.recyclerView);
        setUPList();
    }

    void setUPList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(createCategoryList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new myoracle.com.quotes.DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(categoriesAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Categories categories = categoriesList.get(position);
                Intent intent;
                intent = new Intent(getActivity(), QuotesCardActivity.class);
                intent.putExtra("quotes", (Serializable) categories.getQuoteList());
                intent.putExtra("categoryTitle", categories.getCategory());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    private List<Categories> createCategoryList() {

        try {
            JSONObject quotesJsonObject = new JSONObject(readJsonRaw());
            JSONArray categoriesJsonArray = quotesJsonObject.getJSONArray("Categories");

            for (int index = 0; index < categoriesJsonArray.length(); index++) {
                JSONObject categoriesObj = categoriesJsonArray.getJSONObject(index);
                JSONArray quotesJsonArray = categoriesObj.getJSONArray("quotes");
                List<Quote> quoteList = new ArrayList<Quote>();
                for (int j = 0; j < quotesJsonArray.length(); j++) {
                    JSONObject quotesObject = quotesJsonArray.getJSONObject(j);

                    Quote quote;
                    try {
                        quote = new Quote(quotesObject.getString("quote"), quotesObject.getString("quoteauthor"));
                    } catch (Exception e) {
                        quote = new Quote(quotesObject.getString("quote"), "");
                    }

                    quoteList.add(quote);
                }
                this.categoriesList.add(new Categories(categoriesObj.getString("name"), index, quoteList,
                        categoriesObj.getString("icon"), categoriesObj.getString("genre")));
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return categoriesList;
        //categoriesAdapter.notifyDataSetChanged();
    }

    private String readJsonRaw() {

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.quotes);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String inputJsonString = new String(buffer, "UTF-8");
            return inputJsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
