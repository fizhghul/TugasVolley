package com.hafiizh.appnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hafiizh.appnews.Detail;
import com.hafiizh.appnews.R;

import java.util.HashMap;
import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {
    private List<HashMap<String, String>> listItems;
    private Context context;

    public AdapterNews(List<HashMap<String, String>> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HashMap<String, String> listItem = listItems.get(position);
        holder.judul.setText(listItem.get("judul"));
        holder.konten.setText(listItem.get("konten"));
        Glide.with(context).load("http://31.220.53.18/hafizhnaufal/uploads/" + listItem.get("gambar")).placeholder(android.R.drawable.ic_menu_gallery).error(android.R.drawable.ic_menu_report_image).into(holder.gambar);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(view.getContext(), Detail.class);
                a.putExtra("judul", listItem.get("judul"));
                a.putExtra("konten", listItem.get("konten"));
                a.putExtra("gambar", listItem.get("gambar"));
                a.putExtra("gambar2", listItem.get("gambar2"));
                a.putExtra("gambar3", listItem.get("gambar3"));
                view.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, konten;
        public ImageView gambar;
        public CardView card;

        public ViewHolder(View v) {
            super(v);
            judul = (TextView) v.findViewById(R.id.judul_list);
            konten = (TextView) v.findViewById(R.id.konten_list);
            gambar = (ImageView) v.findViewById(R.id.img_list);
            card = (CardView) v.findViewById(R.id.card);
        }
    }
}
