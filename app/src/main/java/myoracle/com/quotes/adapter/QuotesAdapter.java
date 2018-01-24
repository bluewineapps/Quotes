package myoracle.com.quotes.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import myoracle.com.quotes.R;
import myoracle.com.quotes.model.Quote;

/**
 * Created by Midhun on 19-04-2017.
 */

public class QuotesAdapter extends PagerAdapter {

    private final Context context;
    private final ArrayList<Quote> quotes;
    private LayoutInflater layoutInflater;
    private Integer index;
    private Boolean indexFlag;


    public QuotesAdapter(Context context, ArrayList<Quote> quotesList, Integer index) {
        this.context = context;
        this.quotes = quotesList;
        this.index=index;
        this.indexFlag=true;
    }

    @Override
    public int getCount() {

        return this.quotes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        if(indexFlag){
            position=index;
            indexFlag =false;
        }
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView  = layoutInflater.inflate(R.layout.quotes_cell, collection, false);
        final TextView textView = (TextView) itemView.findViewById(R.id.quotesview);

        TextView textViewAuthor = (TextView) itemView.findViewById(R.id.quotesAuthor);
        textView.setText(this.quotes.get(position).getQuote());
        textViewAuthor.setText(this.quotes.get(position).getAuthor());
        collection.addView(itemView);

        textView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",textView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(v.getContext(),"Copied", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
