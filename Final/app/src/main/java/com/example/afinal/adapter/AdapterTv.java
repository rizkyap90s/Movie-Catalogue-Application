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
import com.example.afinal.detail.DetailTv;
import com.example.afinal.model.TV;

import java.util.List;

public class AdapterTv extends RecyclerView.Adapter<AdapterTv.Myholder> {

    Context context;
    List<TV> modelTVS;

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    public AdapterTv(Context context, List<TV> modelTVS) {
        this.context=context;
        this.modelTVS=modelTVS;
    }
    public List<TV> getTV(){
        return modelTVS;
    }
    public void setTV(List<TV> modelTVS){
        this.modelTVS=modelTVS;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myholder holder, int position) {
        holder.txttitle.setText(modelTVS.get(holder.getAdapterPosition()).getTv_title());
        Glide.with(context)
                .load(URL_IMAGE+modelTVS.get(holder.getAdapterPosition()).getTv_poster())
                .into(holder.imgposter);

        holder.imgposter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageView imgshow = new ImageView(context);
                Glide.with(context)
                        .load(URL_IMAGE+modelTVS.get(holder.getAdapterPosition()).getTv_poster())
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
                Intent intent = new Intent(context, DetailTv.class);
                intent.putExtra(DetailTv.KEY_TV, modelTVS.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });

        holder.clickdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTv.class);
                intent.putExtra(DetailTv.KEY_TV, modelTVS.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelTVS.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView txttitle;
        ImageView imgposter;
        CardView clickdetail;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txt_title);
            imgposter = itemView.findViewById(R.id.img_poster);
            clickdetail = itemView.findViewById(R.id.clickddetail);
        }
    }
}
