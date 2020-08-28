package com.zackmathews.catpictures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView adapter to display {@link CatImage} objects in a timeline.
 * Note: Given more time I would add infinite scrolling + suggested search terms +
 * Timeline repo to include many different kinds of cards
 * (Ads, suggested search terms, images, etc)
 */
public class CatImageAdapter extends RecyclerView.Adapter<CatImageAdapter.ItemCard> {

    private List<CatImage> data = new ArrayList<>();

    public void setData(List<CatImage> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_card_view, parent, false);
        ItemCard ic = new ItemCard(v);
        return ic;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCard holder, int position) {
        final CatImage catImage = data.get(position);
        Picasso.get().load(catImage.url).into(holder.iv);
        holder.tv.setText(catImage.toString());
    }

    static class ItemCard extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ItemCard(@NonNull View itemView) {
            super(itemView);
            this.iv = itemView.findViewById(R.id.catImageView);
            this.tv = itemView.findViewById(R.id.catTextView);
        }
    }
}
