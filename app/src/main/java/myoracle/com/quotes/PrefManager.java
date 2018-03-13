package myoracle.com.quotes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

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
    private static final String IS_WINE_FIRST_TIME_LAUNCH ="isWineFirstTimeLaunch";
    private static final String IS_WINE_FIRST_TIME_CREATE ="isWineFirstTimeCreate";
    private static final String IS_WINE_SIGN_UP ="isSignUp";



    public PrefManager(Context context){
        this.context=context;
        this.sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public  Integer getMindTrickQuestionNo(String key) {

        return sharedPreferences.getInt(key,0);
    }

    public  void setMindTrickQuestionNo(String key,Integer mindTrickQuestionNo) {

        editor.putInt(key,mindTrickQuestionNo);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

    public void setWinesFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_WINE_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }

    public boolean isWinesFirstTimeLaunch(){
        return sharedPreferences.getBoolean(IS_WINE_FIRST_TIME_LAUNCH,true);
    }


    public void setWinesFirstTimeCreate(boolean isFirstTime){
        editor.putBoolean(IS_WINE_FIRST_TIME_CREATE,isFirstTime);
        editor.commit();
    }

    public boolean isWinesFirstTimeCreate(){
        return sharedPreferences.getBoolean(IS_WINE_FIRST_TIME_CREATE,true);
    }

    public  boolean isSignUp(){
        return sharedPreferences.getBoolean(IS_WINE_SIGN_UP,true);
    }
    public void setSignUp(boolean isFirstTime){
        editor.putBoolean(IS_WINE_SIGN_UP,isFirstTime);
        editor.commit();
    }
}
