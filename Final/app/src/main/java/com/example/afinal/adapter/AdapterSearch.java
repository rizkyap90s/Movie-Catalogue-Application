package com.example.afinal.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.example.afinal.detail.DetailSearch;
import com.example.afinal.model.Search;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.MyHolder> {
    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";


    Context context;
    List<Search> searches;

    public AdapterSearch(Context context, List<Search> searches) {
        this.context = context;
        this.searches = searches;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.txttitle.setText(searches.get(holder.getAdapterPosition()).getSearch_title());
        Glide.with(context)
                .load(URL_IMAGE + searches.get(holder.getAdapterPosition()).getSearch_poster())
                .into(holder.imgposter);

        holder.imgposter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageView imgshow = new ImageView(context);
                Glide.with(context)
                        .load(URL_IMAGE + searches.get(holder.getAdapterPosition()).getSearch_poster())
                        .into(imgshow);
                AlertDialog.Builder showpicture = new AlertDialog.Builder(context);
                showpicture.setCustomTitle(imgshow);
                Dialog dialog = showpicture.create(); dialog.show();
                return false;
            }
        });
        holder.imgposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSearch.class);
                intent.putExtra(DetailSearch.KEY_SEARCH, searches.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.clickdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSearch.class);
                intent.putExtra(DetailSearch.KEY_SEARCH, searches.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txttitle;
        ImageView imgposter;
        CardView clickdetail;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txt_title);
            imgposter = itemView.findViewById(R.id.img_poster);
            clickdetail = itemView.findViewById(R.id.clickddetail);
        }
    }
}
