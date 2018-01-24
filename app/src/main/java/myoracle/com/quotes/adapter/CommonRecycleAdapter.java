package myoracle.com.quotes.adapter;

/**
 * Created by Midhun on 28-10-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myoracle.com.quotes.R;
import myoracle.com.quotes.model.Story;

/**
 * Created by midhun 29-10-2017
 */

public class CommonRecycleAdapter extends RecyclerView.Adapter<CommonRecycleAdapter.ViewHolder> {

    private List<Story> stories;
    private Context context;

    public CommonRecycleAdapter(List<Story> stories, Context context) {
        this.stories=stories;
        this.context=context;
    }

    @Override
    public CommonRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.text.setText(stories.get(position).getTitle());
        Glide.with(context)
                .load(stories.get(position).getUrl())
                .into(viewHolder.imageViewBookCover);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView text ;

        ImageView imageViewBookCover;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            text = itemLayoutView.findViewById(R.id.story_title);
            imageViewBookCover =itemLayoutView.findViewById(R.id.imageViewBookCover);

        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}