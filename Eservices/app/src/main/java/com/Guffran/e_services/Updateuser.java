package com.Guffran.e_services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Updateuser extends AppCompatActivity {
    TextView name, contact, profession, location;
    Button update;
    ImageView Userimage;
    Uri uri;
    String imageUrl, Name, Contact, Profession, Location, Key;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        checkConnection();
        Userimage = findViewById(R.id.imageView);
        contact = findViewById(R.id.contact);
        profession = findViewById(R.id.profession);
        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        update = findViewById(R.id.button3);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(Updateuser.this)
                    .load(bundle.getString("Image"))
                    .into(Userimage);
            name.setText(bundle.getString("Name"));
            contact.setText(bundle.getString("Contact"));
            profession.setText(bundle.getString("Profession"));
            location.setText(bundle.getString("Location"));
            Key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name = name.getText().toString().trim();
                Contact = contact.getText().toString().trim();
                Profession = profession.getText().toString().trim();
                Location = location.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(Updateuser.this);
                progressDialog.setMessage("User Updating.....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                uploadData();
                progressDialog.dismiss();
            }
        });
    }

    public void uploadData() {

        Userdata userdata = new Userdata(Name, Contact, Profession, Location, imageUrl, Key);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Labour Data").child(Key);
        databaseReference.setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
checkConnection();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                Toast.makeText(Updateuser.this, "User Data Updated", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.updatesnackbar);
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