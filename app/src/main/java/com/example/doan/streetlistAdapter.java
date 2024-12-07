package com.example.doan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class streetlistAdapter extends RecyclerView.Adapter<streetlistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<streetlist> liststreetlists = new ArrayList<>();
    public streetlistAdapter(Context context, ArrayList<streetlist> liststreetlists) {
        this.context = context;
        this.liststreetlists = liststreetlists;
    }
    @NonNull
    @Override
    public streetlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.streetlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull streetlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ImageView.setImageResource(this.liststreetlists.get(position).getImage());
        holder.textView.setText(this.liststreetlists.get(position).getStreetName());
    }

    @Override
    public int getItemCount() {
        return this.liststreetlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ImageView;
        private TextView textView;
        private RatingBar ratingbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ImageView = (ImageView) itemView.findViewById(R.id.flag_img);
            this.textView = (TextView) itemView.findViewById(R.id.tv_street);

        }
    }
}
