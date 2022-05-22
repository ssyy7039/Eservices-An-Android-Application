package com.Guffran.e_services;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class Userdetails extends AppCompatActivity {
    ImageView imageView;
    TextView profession,name,contact,location;
    Button book;
    String key="";
    String imageUrl="";
    private String Id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        name= (TextView) findViewById(R.id.name1);
        checkConnection();
        profession = (TextView) findViewById(R.id.profession1);
        contact = (TextView) findViewById(R.id.contact1);
        location = findViewById(R.id.address);
        book=findViewById(R.id.book1);
        imageView = (ImageView) findViewById(R.id.image1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name.setText("Name::"+bundle.getString("Name"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            contact.setText("Contact::"+bundle.getString("Contact"));
            profession.setText("Profession::"+bundle.getString("Profession"));
            location.setText("Location::"+bundle.getString("Location"));
            Glide.with(this).
                    load(bundle.getString("Image")).into(imageView);


        }
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Userdetails.this,Bookuser.class);
                intent.putExtra("Name",name.getText().toString().trim());
                intent.putExtra("Profession",profession.getText().toString().trim());
                intent.putExtra("Contact",contact.getText().toString().trim());
                intent.putExtra("Location",location.getText().toString().trim());
                intent.putExtra("Image",imageUrl);
                intent.putExtra("Key",key);
                startActivity(intent);
            }
        });
    }

    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.userdetailssnackbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000000);

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
