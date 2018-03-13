package myoracle.com.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import myoracle.com.quotes.NewPostActivity;
import myoracle.com.quotes.R;
import myoracle.com.quotes.SignUpActivity;

/**
 * Created by CL Accounts on 06-03-2018.
 */

public class WinesAdapter extends Fragment  {

    private int tabId ;
    private FloatingActionButton floatingActionButton;

    public void settabId(int tabId){
        this.tabId=tabId;
    }


    public static Fragment newInstance(Context context) {
        WinesAdapter winesAdapter = new WinesAdapter();
        return  winesAdapter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.wines_main_activity, null);
        floatingActionButton=(FloatingActionButton) root.findViewById(R.id.fab_new_post);


        loadFragment(new RecentPostsFragment());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( FirebaseAuth.getInstance().getCurrentUser()==null){
                    startActivity(new Intent(getActivity(), SignUpActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), NewPostActivity.class));
                }

            }
        });
        return root;
    }

    private void loadFragment(RecentPostsFragment recentPostsFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,recentPostsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
