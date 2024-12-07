package com.example.doan;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class helpfulnessAdapter extends RecyclerView.Adapter<helpfulnessAdapter.ViewHolder> {
    private Context context;
    private ArrayList<helpfulnessList> helpfulnessList = new ArrayList<>();
    public helpfulnessAdapter(Context context, ArrayList<helpfulnessList> helpfulnessList) {
        this.context = context;
        this.helpfulnessList = helpfulnessList;
    }
    @NonNull
    @Override
    public helpfulnessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.helpfulnesslist, parent, false);
        return new helpfulnessAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull helpfulnessAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ImageView2.setImageResource(this.helpfulnessList.get(position).getImageStreet());
        holder.textView2.setText(this.helpfulnessList.get(position).getStreetName());
        holder.ImageView1.setImageResource(this.helpfulnessList.get(position).getImageAvatar());
        holder.textView1.setText(this.helpfulnessList.get(position).getNameAcc());
    }

    @Override
    public int getItemCount() {
        return this.helpfulnessList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ImageView1;
        private TextView textView1;
        private ImageView ImageView2;
        private TextView textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ImageView1 = (ImageView) itemView.findViewById(R.id.user_avatar);
            this.textView1 = (TextView) itemView.findViewById(R.id.title);
            this.ImageView2 = (ImageView) itemView.findViewById(R.id.imagelocation);
            this.textView2 = (TextView) itemView.findViewById(R.id.tv_street);
        }
    }
}
