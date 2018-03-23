package myoracle.com.quotes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star2);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);
        post_author_photo =itemView.findViewById(R.id.post_author_photo);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);
        if(post.isMale ==null ||post.isMale)
             post_author_photo.setImageResource(R.drawable.boy6_32);
        else
            post_author_photo.setImageResource(R.drawable.woman24);
        starView.setOnClickListener(starClickListener);

    }
}
