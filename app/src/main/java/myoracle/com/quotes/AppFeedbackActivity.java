package myoracle.com.quotes;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;

import myoracle.com.quotes.model.Feedback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by CL Accounts on 08-02-2018.
 */

public class AppFeedbackActivity extends AppCompatActivity {


    private static final String URL="https://maya-4a0e7.firebaseapp.com/api/feedbacks";

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText message;
    private Button submit;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);

        name =findViewById(R.id.feedback_name);
        email=findViewById(R.id.feedback_email);
        phone=findViewById(R.id.feedback_phone);
        message=findViewById(R.id.feedback_message);
        submit=findViewById(R.id.feedback_submit);

        builder = new AlertDialog.Builder(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feedback feedback=getFormData();
                if(feedback.getName().isEmpty()||feedback.getName()==null){
                    name.setError("This field can not be blank");
                }else if(feedback.getEmail().isEmpty()||feedback.getEmail()==null){
                    email.setError("This field can not be blank");
                }else if(feedback.getMessage().isEmpty()||feedback.getMessage()==null){
                   message.setError("This field can not be blank");
                }else{
                    new UpdateFeedbackBackgroundTask().execute(feedback);
                }


            }
        });
    }

    private Feedback getFormData() {

        Feedback feedback = new Feedback();

        feedback.setName(name.getText().toString());
        feedback.setEmail(email.getText().toString());
        feedback.setPhone(phone.getText().toString());
        feedback.setMessage(message.getText().toString());

        return  feedback;
    }


    private void clearFormData() {



       name.setText("");
       email.setText("");
       phone.setText("");
       message.setText("");

    }

   class UpdateFeedbackBackgroundTask extends AsyncTask<Feedback,Integer,JSONArray>{

       OkHttpClient okHttpClient = new OkHttpClient();

       @Override
       protected JSONArray doInBackground(Feedback... feedbacks) {

           Feedback feedback =feedbacks[0];

           String json ="{\"name\":\""+feedback.getName()+"\",\"email\":\""+feedback.getEmail()+"\",\"phone\":\"" + feedback.getPhone() + "\",\"message\":\"" + feedback.getMessage() + "\"}";

           RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
           Request request = new Request.Builder()
                   .url(URL)
                   .post(body)
                   .build();
           Response response = null;
           try {
               response = okHttpClient.newCall(request).execute();

           } catch (IOException e) {
               e.printStackTrace();
           }


           return null;
       }


       @Override
       protected void onPostExecute(JSONArray jsonArray) {

           super.onPostExecute(jsonArray);
           clearFormData();

           builder.setMessage("Thank You For Sharing Your Feedback.")
                   .setCancelable(false)
                   .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           finish();
                       }
                   });

           AlertDialog alert = builder.create();
           //Setting the title manually
           alert.setTitle("Feedback updated Successfully.");
           alert.show();


       }





   }
}
