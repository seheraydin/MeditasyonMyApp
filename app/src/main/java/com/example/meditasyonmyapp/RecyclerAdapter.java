package com.example.meditasyonmyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Meditasyon> liste;
    public  RecyclerAdapter(List<Meditasyon> list)
    {
        liste=list;
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        viewHolder.baslik_text.setText(liste.get(position).getBaslik());
        viewHolder.aciklama_text.setText(liste.get(position).getAciklama());
        Picasso.with(viewHolder.meditasyon_image.getContext()).load(liste.get(position).getResim()).into(viewHolder.meditasyon_image);

    }

    @Override
    public int getItemCount() {
        return liste.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView card_view;
        public TextView baslik_text;
        public TextView aciklama_text;
        public ImageView meditasyon_image;

        public ViewHolder(View view) {
            super(view);
            this.card_view = view.findViewById(R.id.idCard_view);
            this.baslik_text=view.findViewById(R.id.idMeditasyonTitle);
            this.aciklama_text=view.findViewById(R.id.idMeditasyonSubTitle);
            this.meditasyon_image=view.findViewById(R.id.idMeditasyonImage);

        }
    }
}
