package com.Guffran.e_services;

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

import java.util.ArrayList;
import java.util.List;

public class Adminuseradapter extends  RecyclerView.Adapter<AdminuserHolder>{
    private Context context;
    private List<Userdata> userdataList;

    public Adminuseradapter(Context context, List<Userdata> userdataList) {
        this.context = context;
        this.userdataList = userdataList;
    }

    @Override
    public AdminuserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.carduser,parent,false);
        return  new AdminuserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminuserHolder holder, int position) {
        Glide.with(context)
                .load(userdataList.get(position).getImageurl())
                .into(holder.imageView);
        holder.Name.setText(userdataList.get(position).getName());
        holder.Profession.setText(userdataList.get(position).getProfession());
        holder.Location.setText(userdataList.get(position).getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Adminuserdetails.class);
                intent.putExtra("Image",userdataList.get(holder.getAdapterPosition()).getImageurl());
                intent.putExtra("Name",userdataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Contact",userdataList.get(holder.getAdapterPosition()).getContacNo());
                intent.putExtra("Profession",userdataList.get(holder.getAdapterPosition()).getProfession());
                intent.putExtra("Location",userdataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("Key",userdataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userdataList.size();

    }
    public void filteredList(ArrayList<Userdata> filterList) {
        userdataList=filterList;
        notifyDataSetChanged();
    }
}
class AdminuserHolder extends RecyclerView.ViewHolder {

    TextView Name,Profession,Location;
    CardView cardView;
    ImageView imageView;

    public AdminuserHolder(@NonNull View itemView) {
        super(itemView);
        Name=itemView.findViewById(R.id.cardname);
        Profession=itemView.findViewById(R.id.cardprofession);
        Location=itemView.findViewById(R.id.cardlocation);
        cardView=itemView.findViewById(R.id.cardview);
        imageView=itemView.findViewById(R.id.cardimage);
    }
}
