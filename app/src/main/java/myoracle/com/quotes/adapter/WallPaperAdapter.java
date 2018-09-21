package myoracle.com.quotes.adapter;

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
import myoracle.com.quotes.model.WallpaperMain;

/**
 * Created by Midhun on 13-11-2017.
 */

public class WallPaperAdapter extends RecyclerView.Adapter<WallPaperAdapter.MyViewHolder>{


    private List<WallpaperMain> wallpaperMains;
    private Context context;

    public WallPaperAdapter(List<WallpaperMain> wallpaperMains, Context context){
        this.wallpaperMains =wallpaperMains;
        this.context=context;
    }

    @Override
    public WallPaperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, null);

        WallPaperAdapter.MyViewHolder viewHolder = new WallPaperAdapter.MyViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context)
                .load(wallpaperMains.get(position).getWallpaper().getMedium())
                .into(holder.waIlpapermageView);

        holder.wallpaperItem_ViewCount.setText(wallpaperMains.get(position).getWallpaper().getView());
       // holder.waIlpapermageView.setImageResource(R.drawable.name);
    }

    @Override
    public int getItemCount()
    {

        return wallpaperMains.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView wallpaperItem_ViewCount;
        ImageView waIlpapermageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            waIlpapermageView =(ImageView) itemView.findViewById(R.id.wallpaperItem_imageView);
            wallpaperItem_ViewCount =itemView.findViewById(R.id.wallpaperItem_ViewCount);
        }
    }
}
