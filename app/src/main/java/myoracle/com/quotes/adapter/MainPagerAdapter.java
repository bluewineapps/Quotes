package myoracle.com.quotes.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import myoracle.com.quotes.model.Games;

/**
 * Created by midhun 29-10-2017
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

   private CharSequence Titles[];
   private int NumbOfTabs;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if(position ==0){

            WinesAdapter winesAdapter = new WinesAdapter();
            return winesAdapter;

        }else if(position ==1){

            WallpaperFragmentAdapter wallpaperFragmentAdapter = new WallpaperFragmentAdapter();
            wallpaperFragmentAdapter.settabId(1);
            return wallpaperFragmentAdapter;

        }else if(position == 2){
            QuotesFragmentAdapter quotesFragmentAdapter = new QuotesFragmentAdapter();
            quotesFragmentAdapter.settabId(2);
            return quotesFragmentAdapter;
        }else if(position == 3){
            GamesFragmentAdapter gamesFragmentAdapter = new GamesFragmentAdapter();
            gamesFragmentAdapter.settabId(2);
            return gamesFragmentAdapter;
        }else{
            StoriesFragmentAdapter storiesFragmentAdapter = new StoriesFragmentAdapter();
            storiesFragmentAdapter.settabId(3);
            return storiesFragmentAdapter;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}