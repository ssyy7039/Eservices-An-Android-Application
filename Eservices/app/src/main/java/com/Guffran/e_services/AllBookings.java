package com.Guffran.e_services;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllBookings extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<Bookdata> bookdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookings);
        checkConnection();

        recyclerView=(RecyclerView)findViewById(R.id.brecyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(AllBookings.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");

        bookdataList=new ArrayList<>();
        final Bookingadapter bookingadapter = new Bookingadapter(AllBookings.this,bookdataList);
        recyclerView.setAdapter(bookingadapter);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String uid= firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("All Users").child(uid).child("My Bookings");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookdataList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){


                    Bookdata bookdata = itemSnapshot.getValue(Bookdata.class);
                    bookdata.setKey(itemSnapshot.getKey());
                    bookdataList.add(bookdata);

                }

                bookingadapter.notifyDataSetChanged();
                if (bookingadapter.getItemCount()==0){
                    findViewById(R.id.nobookings).setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                if (bookingadapter.getItemCount()!=0){
                    findViewById(R.id.nobookings).setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

          }

    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.allbookbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(1000);

        if (actionNetwork!=null){
            if (actionNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                snackbar.dismiss();
            }
            if (actionNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                snackbar.dismiss();
            }
        }else {
            snackbar.show();

            Toast.makeText(this, "Internet connection may not available", Toast.LENGTH_SHORT).show();
        }
    }

}
