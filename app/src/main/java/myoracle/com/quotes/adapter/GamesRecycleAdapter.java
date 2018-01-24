package myoracle.com.quotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myoracle.com.quotes.R;
import myoracle.com.quotes.model.Games;
import myoracle.com.quotes.model.Story;

/**
 * Created by Midhun on 19-01-2018.
 */

public class GamesRecycleAdapter extends RecyclerView.Adapter<GamesRecycleAdapter.ViewHolder>{

    private List<Games> games;
    private Context context;

    public GamesRecycleAdapter(List<Games> games, Context context) {
        this.games=games;
        this.context=context;
    }

    @Override
    public GamesRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_item, null);
        GamesRecycleAdapter.ViewHolder viewHolder = new GamesRecycleAdapter.ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(GamesRecycleAdapter.ViewHolder viewHolder, int position) {

        viewHolder.text.setText(games.get(position).getName());
        Glide.with(context)
                .load(games.get(position).getImage())
                .into(viewHolder.imageViewBookCover);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView text ;

        ImageView imageViewBookCover;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            LinearLayout linearLayout=(LinearLayout) itemLayoutView.findViewById(R.id.card_cell_layoutid);

            text = itemLayoutView.findViewById(R.id.story_title);
            imageViewBookCover =itemLayoutView.findViewById(R.id.imageViewBookCover);

        }
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
