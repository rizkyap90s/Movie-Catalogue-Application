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
import com.example.afinal.detail.DetailMovie;
import com.example.afinal.model.Movie;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyHolder>{

    Context context;
    List<Movie> modelMovies;
    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";


    public AdapterMovie(Context context, List<Movie> modelMovies) {
        this.context=context;
        this.modelMovies=modelMovies;
    }
    public List<Movie> getMovie(){
        return modelMovies;
    }
    public void setMovies(List<Movie> modelMovies){
        this.modelMovies=modelMovies;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.txttitle.setText(modelMovies.get(holder.getAdapterPosition()).getMovie_title());
        Glide.with(context)
                .load(URL_IMAGE + modelMovies.get(holder.getAdapterPosition()).getMovie_poster())
                .into(holder.imgposter);

        holder.imgposter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageView imgshow = new ImageView(context);
                Glide.with(context)
                        .load(URL_IMAGE + modelMovies.get(holder.getAdapterPosition()).getMovie_poster())
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
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(DetailMovie.KEY_MOVIE, modelMovies.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.clickdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(DetailMovie.KEY_MOVIE, modelMovies.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelMovies.size();
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
}   }
