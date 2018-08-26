package myoracle.com.quotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by CL Accounts on 06-03-2018.
 */

public class BaseActivity extends AppCompatActivity {



    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(BaseActivity.this,SignUpActivity.class));
            return "123456";
        }else{
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }


    }


}
