package myoracle.com.quotes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Midhun on 21-12-2017.
 */

public class PrefManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE =0;

    private static final String PREF_NAME ="quotes-and-status-welcome";
    private static final String IS_FIRST_TIME_LAUNCH ="isFirstTimeLaunch";


    private static final String MIND_TRICK_QUESTION_NO ="MIND_TRICK_INDEX";

    public PrefManager(Context context){
        this.context=context;
        this.sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public  Integer getMindTrickQuestionNo() {
        return sharedPreferences.getInt(MIND_TRICK_QUESTION_NO,0);
    }

    public  void setMindTrickQuestionNo(Integer mindTrickQuestionNo) {

        editor.putInt(MIND_TRICK_QUESTION_NO,mindTrickQuestionNo);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }


}
