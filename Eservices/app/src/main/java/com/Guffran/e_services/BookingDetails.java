package com.Guffran.e_services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class BookingDetails extends AppCompatActivity {
    TextView time,id,vprofession,vname,vcontact,vaddress,date1,date2,uname,ucontact,uaddress;
    Button delete,cancel;
    ImageView imageView;
    String Key,status,status2="You cancelled the booking",status3="Booking is cancelled from the Customer side",Imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        checkConnection();
        time=findViewById(R.id.dtime);
        id=findViewById(R.id.did);
        vprofession=findViewById(R.id.professiondetails);
        vname=findViewById(R.id.namedetails);
        vcontact=findViewById(R.id.contactdetails);
        vaddress=findViewById(R.id.addressdetails);
        date1=findViewById(R.id.date1);
        date2=findViewById(R.id.date2);
        uname=findViewById(R.id.username);
        ucontact=findViewById(R.id.usercontact);
        uaddress=findViewById(R.id.useraddress);
        delete=findViewById(R.id.ddelete);
        cancel=findViewById(R.id.dcancel);
        imageView=findViewById(R.id.imagedetails);
        Bundle bundle=getIntent().getExtras();
        time.setText(bundle.getString("Time"));
        id.setText(bundle.getString("ID"));
        vprofession.setText(bundle.getString("Vprofession"));
        vname.setText(bundle.getString("Vname"));
        vcontact.setText(bundle.getString("Vcontact"));
        vaddress.setText(bundle.getString("Vaddress"));
        date1.setText(bundle.getString("SDate1"));
        date2.setText(bundle.getString("EDate2"));
        uname.setText(bundle.getString("Username"));
        ucontact.setText(bundle.getString("Usercontact"));
        uaddress.setText(bundle.getString("Useraddress"));
        Key=bundle.getString("Key");
        status=bundle.getString("Status");
        Glide.with(this).
                load(bundle.getString("Image")).into(imageView);
        Imageurl=bundle.getString("Image");

        if (status.equals("Booking placed")){
            delete.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        }
        if (status.equals("Booking is cancelled from the Vendor side")){
            delete.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }
        if (status.equals("You cancelled the booking")){
            delete.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Dialogcancel();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Dialogdelete();
            }
        });

    }
    public void Dialogcancel(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Are you sure You want to Cancel the Booking");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(BookingDetails.this);
                progressDialog.setMessage("Canceling Booking......");

                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String UID=firebaseAuth.getCurrentUser().getUid();
              String  Time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                Bookdata bookdata = new Bookdata(id.getText().toString().trim(),vname.getText().toString().trim(),vprofession.getText().toString().trim(),vcontact.getText().toString().trim(),vaddress.getText().toString().trim(),Imageurl,
                        uname.getText().toString().trim(),ucontact.getText().toString().trim(),uaddress.getText().toString().trim(),date1.getText().toString().trim(),date2.getText().toString().trim(),Key,Time,UID,status2);

                FirebaseDatabase.getInstance().getReference("All Users")
                        .child(UID).child("My Bookings").child(Key).setValue(bookdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Toast.makeText(com.shivam.shop.Orderproducts.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                    }
                });
//
                Bookdata bookdata2 = new Bookdata(id.getText().toString().trim(),vname.getText().toString().trim(),vprofession.getText().toString().trim(),vcontact.getText().toString().trim(),vaddress.getText().toString().trim(),Imageurl,
                        uname.getText().toString().trim(),ucontact.getText().toString().trim(),uaddress.getText().toString().trim(),date1.getText().toString().trim(),date2.getText().toString().trim(),Key,Time,UID,status3);
                FirebaseDatabase.getInstance().getReference("All Bookings")
                        .child(Key).setValue(bookdata2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookingDetails.this, "booking is not cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                progressDialog.dismiss();
                Toast.makeText(BookingDetails.this, "Your Booking is cancelled ", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),Allorders.class));
                finish();




            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog=builder.show();
    }
    public void Dialogdelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure You want to Delete the Booking? " );
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(BookingDetails.this);
                progressDialog.setMessage("Deleting Booking......");
                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String UID=firebaseAuth.getCurrentUser().getUid();
                final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("All Users").child(UID).child("My Bookings");
                databaseReference2.child(Key).removeValue();
                progressDialog.dismiss();
                Toast.makeText(BookingDetails.this, "Your booking deleted", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(getApplicationContext(),Allorders.class));
                finish();




            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog=builder.show();
    }

    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.bookingdetails);
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