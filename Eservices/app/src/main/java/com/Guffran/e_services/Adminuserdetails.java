package com.Guffran.e_services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adminuserdetails extends AppCompatActivity {
    ImageView imageView;
    TextView profession,name,contact,location;
    Button Edit,Delete;
    String key="";
    String imageUrl="";
    private String Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuserdetails);
        name= (TextView) findViewById(R.id.name1);
        checkConnection();
        profession = (TextView) findViewById(R.id.profession1);
        contact = (TextView) findViewById(R.id.contact1);
        location = findViewById(R.id.address);
        Edit=findViewById(R.id.edit);
        Delete=findViewById(R.id.delete);
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
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Adminuserdetails.this,Updateuser.class);
                intent.putExtra("Name",name.getText().toString().trim());
                intent.putExtra("Profession",profession.getText().toString().trim());
                intent.putExtra("Contact",contact.getText().toString().trim());
                intent.putExtra("Location",location.getText().toString().trim());
                intent.putExtra("Image",imageUrl);
                intent.putExtra("Key",key);
                startActivity(intent);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogdelete();
            }
        });
    }


    public void Dialogdelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure You want to Delete the User? " );
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   checkConnection();
                final ProgressDialog progressDialog=new ProgressDialog(Adminuserdetails.this);
                progressDialog.setMessage("Deleting User......");
                progressDialog.show();
                   final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("Labour Data");
                databaseReference2.child(key).removeValue();
                progressDialog.dismiss();
                Toast.makeText(Adminuserdetails.this, "User  deleted", Toast.LENGTH_SHORT).show();
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
        View view=findViewById(R.id.admindetailsnackbar);
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
