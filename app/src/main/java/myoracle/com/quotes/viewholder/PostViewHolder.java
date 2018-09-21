package myoracle.com.quotes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import myoracle.com.quotes.R;
import myoracle.com.quotes.model.Post;

/**
 * Created by CL Accounts on 06-03-2018.
 */

public class PostViewHolder  extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public ImageView post_author_photo;
    public TextView numStarsView;
    public TextView bodyView;
    public TextView time;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star2);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);
        post_author_photo =itemView.findViewById(R.id.post_author_photo);
        time =itemView.findViewById(R.id.post_time);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);

        if(post.time ==null || post.time == "")
            time.setText("");
        else
            time.setText(getLocalTime(post.time));

        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);
        if(post.isMale ==null ||post.isMale)
             post_author_photo.setImageResource(R.drawable.boy6_32);
        else
            post_author_photo.setImageResource(R.drawable.woman24);
        starView.setOnClickListener(starClickListener);

    }

    private String getLocalTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy hh:mm ");

        if (time != null || !time.isEmpty()) {
            try {
                Date date = df.parse(time);
                long localTime = date.getTime() + TimeZone.getDefault().getRawOffset();
                date = new Date(localTime);
                String formattedDate = df2.format(date);
                return formattedDate;
            } catch (ParseException e) {
                Log.d("time error ", e.getMessage());
            }
        }
            return "";

    }
}
