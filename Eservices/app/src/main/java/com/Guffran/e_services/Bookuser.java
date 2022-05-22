package com.Guffran.e_services;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class Bookuser extends AppCompatActivity {
    EditText name,contact,address;
    TextView date1,date2;
    ImageButton calender1,calender2;
    Button book;
    String Date1,Date2,Vendorname,Vendorprofession,Vendorcontact,Vendoraddress,Imageurl,Key,Time,Username,Usercontact,Useraddress,BookingId,status="Booking confirmed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookuser);
        checkConnection();
        date1=findViewById(R.id.date1);
        date2=findViewById(R.id.date2);
        name=findViewById(R.id.name2);
        contact=findViewById(R.id.contact2);
        address=findViewById(R.id.address2);
        book=findViewById(R.id.cbook);
        calender1=findViewById(R.id.calender1);
        calender2=findViewById(R.id.calender2);
        Calendar calendar=Calendar.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final  int month=calendar.get(Calendar.MONTH);
        final  int day=calendar.get(Calendar.DAY_OF_MONTH);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Bookuser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                         Date1=dayOfMonth+"/"+month+"/"+year;
                        date1.setText(Date1);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Bookuser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        Date1=dayOfMonth+"/"+month+"/"+year;
                        date1.setText(Date1);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Bookuser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                         Date2=dayOfMonth+"/"+month+"/"+year;
                        date2.setText(Date2);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Bookuser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        Date2=dayOfMonth+"/"+month+"/"+year;
                        date2.setText(Date2);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        Bundle bundle=getIntent().getExtras();
        Vendorname=bundle.getString("Name");
        Vendorprofession=bundle.getString("Profession");
        Vendorcontact=bundle.getString("Contact");
        Vendoraddress=bundle.getString("Location");
        Imageurl=bundle.getString("Image");
        Key=bundle.getString("Key");
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Username=name.getText().toString().trim();
                Usercontact=contact.getText().toString().trim();
                Useraddress=address.getText().toString().trim();
                if (Date1==null){
                    Toast.makeText(Bookuser.this, "Please select your start date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Date2==null){
                    Toast.makeText(Bookuser.this, "Please select your End date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Username.isEmpty()){
                    name.setError("Please provide your name");
                    name.requestFocus();
                    return;
                }
                if (Usercontact.isEmpty() && Usercontact.length()<10){
                    contact.setError("Please provide your valid ContactNo.");
                    contact.requestFocus();
                    return;
                }
                if (Useraddress.isEmpty()){
                    address.setError("Please provide your address");
                    address.requestFocus();
                    return;
                }
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String UID=firebaseAuth.getCurrentUser().getUid();
                BookingId= String.valueOf(System.currentTimeMillis());
                Time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Bookdata bookdata=new Bookdata(BookingId,Vendorname,Vendorprofession,Vendorcontact,Vendoraddress,Imageurl,Username,Usercontact,Useraddress,Date1,Date2,Key,Time,UID,status);
                FirebaseDatabase.getInstance().getReference("All Users").child(UID)
                        .child("My Bookings").child(Time).setValue(bookdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Toast.makeText(Bookuser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference("All Bookings")
                        .child(Time).setValue(bookdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent= new Intent(getApplicationContext(),Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(Bookuser.this, "Your booking is successfully done", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Bookuser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.Bookuser);
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