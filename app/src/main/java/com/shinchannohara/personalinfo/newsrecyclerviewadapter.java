package com.shinchannohara.personalinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class newsrecyclerviewadapter extends RecyclerView.Adapter<newsrecyclerviewadapter.ViewHolder> {

    private Context context;
    private List<Articles> articlelist;

    public newsrecyclerviewadapter(Context context, List<Articles> articlelist) {
        this.context = context;
        this.articlelist = articlelist;
    }


    @NonNull
    @Override
    public newsrecyclerviewadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsrecyclerviewadapter.ViewHolder holder, int position) {
        Articles article = articlelist.get(position);

        holder.newstitle.setText(article.getTitle());
        holder.newsdisc.setText(article.getDescription());
        holder.newsauthor.setText(article.getAuthor());
        Picasso.get().load(article.getUrlToImage()).into(holder.newsimg);
        String date = article.getPublishedAt().substring(0,10);
        holder.newsdate.setText(date);
        holder.newsurl.setText(article.getUrl());
    }

    @Override
    public int getItemCount() {
        return articlelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView newstitle,newsdisc,newsauthor,newsdate,newsurl;
        public ImageView newsimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newstitle = (TextView)itemView.findViewById(R.id.news_Post_Title);
            newsdisc = (TextView)itemView.findViewById(R.id.news_Post_Desc);
            newsauthor = (TextView)itemView.findViewById(R.id.news_Post_source);
            newsimg = (ImageView) itemView.findViewById(R.id.news_Post_Image);
            newsdate = (TextView)itemView.findViewById(R.id.news_Post_date);
            newsurl = (TextView)itemView.findViewById(R.id.news_Post_url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = newsurl.getText().toString();
                    Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }
    }

}
