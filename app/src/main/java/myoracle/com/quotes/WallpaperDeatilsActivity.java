package myoracle.com.quotes;

import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Midhun on 14-11-2017.
 */

public class WallpaperDeatilsActivity extends AppCompatActivity {

    public static final String TAG = WallpaperDeatilsActivity.class.getSimpleName();
    private static WallpaperDeatilsActivity wallpaperDeatilsActivity;

    public static int details_count =0;

    InterstitialAd mInterstitialAd;
    ImageView wallPaperDetailsImageview;
    ImageButton wallpaperDetailDownloadButton;
    ImageButton wallpaperDetailSetButton;
    ProgressBar progressBar;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private String imageUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_activity_details);

        wallPaperDetailsImageview =(ImageView) findViewById(R.id.wallpaperDetail_imageViews);
        System.out.println(Build.VERSION.SDK_INT );
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        wallpaperDeatilsActivity = this;

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

        progressBar =(ProgressBar) findViewById(R.id.progress);

        mInterstitialAd = new InterstitialAd(this);

        if((details_count == 1) || ((details_count%7 == 0)&&(details_count != 0))){

            mInterstitialAd.setAdUnitId("ca-app-pub-8629047556008369/1480298984");

            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    showInterstitial();
                }
            });
        }
        details_count ++;

        System.out.println("==== "+checkPermission());
        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
                openActivity();
            } else {
                openActivity();
            }
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        final Intent intent = getIntent();
        imageUrl =intent.getStringExtra("wallpaper");
        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("wallpaper"))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(wallPaperDetailsImageview);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
       // floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

//        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TODO something when floating action menu first item clicked
//
//            }
//        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(intent.getStringExtra("wallpaper"))
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super
                                    Bitmap> glideAnimation) {


                                try {
                                    WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);

                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.wallpaperDetail_coordinatoeLayout),
                                            R.string.wallpaperupdated, Snackbar.LENGTH_SHORT);
                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.YELLOW);
                                    snackbar.show();
                                } catch (IOException e) {
                                    Log.v("",e.toString());
                                }

                            }
                        });
                }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ImageDownload(getApplicationContext()).execute(intent.getStringExtra("wallpaper"));

            }
        });
        
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    class ImageDownload extends AsyncTask<String, String, String> {

        private Context context;

        public ImageDownload(Context context) {
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {

            String src = params[0];
            String dst = Environment.getExternalStorageDirectory().toString();;

            try {
                File file = Glide.with(context)
                        .load(src)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();

                File dstFile = new File(dst);
                if (!dstFile.exists()) {
                    boolean success = dstFile.createNewFile();
                    if (!success) {
                        return null;
                    }
                }

                InputStream in = null;
                OutputStream out = null;

                try {
                    in = new BufferedInputStream(new FileInputStream(file));
                    out = new BufferedOutputStream(new FileOutputStream(dst));

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.flush();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.wallpaperDetail_coordinatoeLayout),
                    R.string.downloadCompleted, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

        }
    }

    private void openActivity() {

    }



    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(WallpaperDeatilsActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(WallpaperDeatilsActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            }
        } else {

            openActivity();
        }
    }

    public static synchronized WallpaperDeatilsActivity getInstance() {
        return wallpaperDeatilsActivity;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
}