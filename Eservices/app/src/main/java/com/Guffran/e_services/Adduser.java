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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Adduser extends AppCompatActivity {
    TextView name,contact,profession,location;
    Button Add;
    FloatingActionButton camera;
    ImageView Userimage;
    Uri uri;
    String imageUrl,Name,Contact,Profession,Location,Key;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        FirebaseApp.initializeApp(this);
        checkConnection();
        contact=findViewById(R.id.contact);
        Userimage=findViewById(R.id.imageView);
        location=findViewById(R.id.location);
        name=findViewById(R.id.name);
        profession=findViewById(R.id.profession);
        Add=findViewById(R.id.button3);
        camera=findViewById(R.id.camera1);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker=new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker,1);
            }
        });
        progressDialog=new ProgressDialog(Adduser.this);
        progressDialog.setTitle("Adding user.");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCancelable(false);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name=name.getText().toString().trim();
                Contact=contact.getText().toString().trim();
                Profession=profession.getText().toString().trim();
                Location=location.getText().toString().trim();
                if (uri==null){
                    Toast.makeText(Adduser.this, "Please Upload user Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Profession.isEmpty()){
                    profession.setError("Enter the profession");
                    profession.requestFocus();
                    return;
                }
                if (Name.isEmpty()){
                    name.setError("Enter the name of the user");
                    name.requestFocus();
                    return;
                }

                if (Contact.isEmpty()){
                    contact.setError("Enter the contact");
                    contact.requestFocus();
                    return;
                }
                if (Location.isEmpty()){
                    location.setError("Enter the location");
                    location.requestFocus();
                    return;
                }

                progressDialog.show();

                StorageReference storageReference= FirebaseStorage.getInstance()
                        .getReference().child("User Image").child(uri.getLastPathSegment());
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri urlImage = uriTask.getResult();
                        imageUrl = urlImage.toString();
                        uploadData();
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });

            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            //startActivity(new Intent(Registeration.this,Home.class));
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            Userimage.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();

    }

    public void uploadData(){
       Userdata userdata=new Userdata(Name,Contact,Profession,Location,imageUrl,Key);
        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Labour Data").child(myCurrentDateTime).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Adduser.this, "User Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Adduser.this, "uploading of the User is Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.addsnackbar);
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

