package myoracle.com.quotes.adapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import myoracle.com.quotes.R;

/**
 * Created by CL Accounts on 06-03-2018.
 */

public class StoreFragment extends Fragment {

    private static final String TAG = StoreFragment.class.getSimpleName();
    private static final String URL = "https://api.androidhive.info/json/movies_2017.json";



    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.feed_back, container, false);



        return view;
    }


    }
