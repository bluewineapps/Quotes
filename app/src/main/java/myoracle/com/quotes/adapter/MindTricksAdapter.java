package myoracle.com.quotes.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

import myoracle.com.quotes.PrefManager;
import myoracle.com.quotes.R;
import myoracle.com.quotes.model.MindTrick;

/**
 * Created by Midhun on 16-01-2018.
 */

public class MindTricksAdapter extends PagerAdapter {

    private final Context context;

    private Boolean flag =true;
    private String key;
    private LayoutInflater layoutInflater;
    private final List<MindTrick> mindTricksMain;
    private static int indexPosision =0;

    private PrefManager prefManager;

    InterstitialAd mInterstitialAd;
    public MindTricksAdapter(Context applicationContext, List<MindTrick> mindTricks,String key) {
        super();

        this.key=key ;
        this.context=applicationContext;
        this.mindTricksMain=mindTricks;
        this.prefManager =new PrefManager(applicationContext);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-8629047556008369/1480298984");

    }



    @Override
    public int getCount() {

        return this.mindTricksMain.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mindCellView  = layoutInflater.inflate(R.layout.mind_tricks_cell, container, false);

        final TextView mindtrick_text = (TextView) mindCellView.findViewById(R.id.mindtrick_text);
        final TextView show_mindtrick_answer= (TextView) mindCellView.findViewById(R.id.show_mindtrick_answer);
        final TextView answer_txt= mindCellView.findViewById(R.id.txt_author);
        final TextView mind_question_id =mindCellView.findViewById(R.id.mind_question_id);


        answer_txt.setText(mindTricksMain.get(position).getAnswer());
        mindtrick_text.setText(mindTricksMain.get(position).getQuestion());
        mind_question_id.setText("# "+position);
        answer_txt.setVisibility(View.GONE);
        show_mindtrick_answer.setOnClickListener(new View.OnClickListener() {
            boolean ansFlag =true;
            @Override
            public void onClick(View v) {
                if(ansFlag){
                    if(((indexPosision %11) == 0) && (indexPosision>0)){

                        indexPosision++;
                        AdRequest adRequest = new AdRequest.Builder()
                                .build();

                        mInterstitialAd.loadAd(adRequest);

                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdLoaded() {
                                showInterstitial();
                            }
                        });
                    }
                    answer_txt.setVisibility(View.VISIBLE);
                    ansFlag =false;
                }else{
                    answer_txt.setVisibility(View.GONE);
                    ansFlag =true;
                }

            }
        });

        container.addView(mindCellView);
        prefManager.setMindTrickQuestionNo(key,position);
        return mindCellView;
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
