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

import java.util.List;

public class Bookingadapter  extends RecyclerView.Adapter<bookviewholder>{
    private Context context;
    private List<Bookdata> bookdataList;

    public Bookingadapter(Context context, List<Bookdata> bookdataList) {
        this.context = context;
        this.bookdataList = bookdataList;
    }

    @NonNull
    @Override
    public bookviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardbook,parent,false);
        return  new bookviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookviewholder holder, int position) {
        Glide.with(context)
                .load(bookdataList.get(position).getVendorImageurl())
                .into(holder.imageView);
        holder.id.setText(bookdataList.get(position).getBookingID());
        holder.time.setText(bookdataList.get(position).getTime());
        holder.profession.setText(bookdataList.get(position).getVendorProfession());
        holder.name.setText(bookdataList.get(position).getVendorname());
        holder.contact.setText(bookdataList.get(position).getVendorContact());
        holder.status.setText(bookdataList.get(position).getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookingDetails.class);
                intent.putExtra("Image",bookdataList.get(holder.getAdapterPosition()).getVendorImageurl());
                intent.putExtra("Vprofession",bookdataList.get(holder.getAdapterPosition()).getVendorProfession());
                intent.putExtra("Vname",bookdataList.get(holder.getAdapterPosition()).getVendorname());
                intent.putExtra("Vcontact",bookdataList.get(holder.getAdapterPosition()).getVendorContact());
                intent.putExtra("Vaddress",bookdataList.get(holder.getAdapterPosition()).getVendorAddress());
                intent.putExtra("ID",bookdataList.get(holder.getAdapterPosition()).getBookingID());
                intent.putExtra("Username",bookdataList.get(holder.getAdapterPosition()).getUsername());
                intent.putExtra("Usercontact",bookdataList.get(holder.getAdapterPosition()).getUsercontact());
                intent.putExtra("Useraddress",bookdataList.get(holder.getAdapterPosition()).getUseraddress());
                intent.putExtra("SDate1",bookdataList.get(holder.getAdapterPosition()).getDate1());
                intent.putExtra("EDate2",bookdataList.get(holder.getAdapterPosition()).getDate2());
                intent.putExtra("Key",bookdataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Time",bookdataList.get(holder.getAdapterPosition()).getTime());
                intent.putExtra("Status",bookdataList.get(holder.getAdapterPosition()).getStatus());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookdataList.size();
    }

}


class bookviewholder extends RecyclerView.ViewHolder{
    CardView cardView;
    ImageView imageView;
    TextView id,time,name,profession,contact,status;

    public bookviewholder(@NonNull View itemView) {
        super(itemView);
        cardView=itemView.findViewById(R.id.cardviewbook);
        imageView=itemView.findViewById(R.id.bimage);
        id=itemView.findViewById(R.id.bid);
        time=itemView.findViewById(R.id.btime);
        name=itemView.findViewById(R.id.bname);
        profession=itemView.findViewById(R.id.bprofession);
        contact=itemView.findViewById(R.id.bconatct);
        status=itemView.findViewById(R.id.status);
    }
}
