package myoracle.com.quotes.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import myoracle.com.quotes.QuotesActivity;
import myoracle.com.quotes.R;
import myoracle.com.quotes.model.Quote;

/**
 * Created by Midhun on 22-04-2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final Integer WORD_LENGTH =90;
    private final String MORE =" .....";
    private ArrayList<Quote> quoteList;
    private Context applicationContext;

    public DataAdapter(ArrayList<Quote> quoteList, Context applicationContext) {
        this.quoteList = quoteList;
        this.applicationContext = applicationContext;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotes_card_cell, viewGroup, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_country.setText(getPrintText(this.quoteList.get(i).getQuote()));
        viewHolder.qt_qu.setText(this.quoteList.get(i).getAuthor());
        final String qt = this.quoteList.get(i).getQuote();
        final String au = this.quoteList.get(i).getAuthor();
        viewHolder.copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) applicationContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(au,qt);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(applicationContext,"Copied", Toast.LENGTH_SHORT).show();

            }
        });

        viewHolder.share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Log.w("string selected",qt);
                sendIntent.putExtra(Intent.EXTRA_TEXT,qt);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                v.getContext().startActivity(sendIntent);

            }
        });

        viewHolder.tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent( v.getContext(), QuotesActivity.class);
                sendIntent.putExtra("quotes",quoteList);
                sendIntent.putExtra("index",i);

                v.getContext().startActivity(sendIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public String getPrintText(String quote) {
        if(quote.length()>=WORD_LENGTH){
            return  quote.substring(0, Math.min(quote.length(),90))+MORE;
        }else {
            return quote;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_country;
        private TextView qt_qu;
        private Button copy;
        private Button share;

        public ViewHolder(View view) {
            super(view);
            qt_qu = (TextView) view.findViewById(R.id.qt_qu);
            tv_country = (TextView) view.findViewById(R.id.tv_country);
            copy = (Button) view.findViewById(R.id.copy);
            share = (Button) view.findViewById(R.id.share);
        }

    }

}