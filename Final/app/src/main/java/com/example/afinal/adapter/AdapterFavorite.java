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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.example.afinal.detail.Favorite_detail;
import com.example.afinal.model.Favorite;

import java.util.LinkedList;
import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyHolder> {

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    LinkedList<Favorite> favorites;
    Context context;

    public AdapterFavorite(Context context) {
        this.context = context;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(LinkedList<Favorite> favorites) {
        this.favorites = favorites;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.txttitle.setText(favorites.get(holder.getAdapterPosition()).getFav_title());
        Glide.with(context)
                .load(URL_IMAGE+favorites.get(holder.getAdapterPosition()).getFav_poster())
                .into(holder.imgposter);
        holder.clickdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String show = favorites.get(holder.getAdapterPosition()).getFav_poster();
                Toast.makeText(context, show, Toast.LENGTH_SHORT).show();
            }
        });
        holder.imgposter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageView imgshow = new ImageView(context);
                Glide.with(context)
                        .load(URL_IMAGE+favorites.get(holder.getAdapterPosition()).getFav_poster())
                        .into(imgshow);
                AlertDialog.Builder showpicture = new AlertDialog.Builder(context);
                showpicture.setCustomTitle(imgshow);
                Dialog dialog = showpicture.create(); dialog.show();
                return false;
            }
        });

        holder.clickdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Favorite_detail.class);
                intent.putExtra(Favorite_detail.FAV, favorites.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        holder.imgposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Favorite_detail.class);
                intent.putExtra(Favorite_detail.FAV, favorites.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getFavorites().size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgposter;
        TextView txttitle;
        CardView clickdetail;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgposter = itemView.findViewById(R.id.img_poster);
            txttitle = itemView.findViewById(R.id.txt_title);
            clickdetail = itemView.findViewById(R.id.clickddetail);

        }
    }
}
