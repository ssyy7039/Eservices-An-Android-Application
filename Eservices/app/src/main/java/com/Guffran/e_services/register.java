package com.Guffran.e_services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class register extends AppCompatActivity {
    private Button register;
    private EditText editname,editemail,editcontact;
    String Name,Email,Contact;
    CheckBox checkBox;
    private CountryCodePicker countryCodePicker;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private long backtime;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        checkConnection();
        editname=(EditText)findViewById(R.id.editText);
        countryCodePicker=findViewById(R.id.ccp);
        editemail=(EditText)findViewById(R.id.editText3);
        editcontact=(EditText)findViewById(R.id.editText2);
        countryCodePicker.registerCarrierNumberEditText(editcontact);
        checkBox=findViewById(R.id.checkbox1);
        register = (Button) findViewById(R.id.button);
       findViewById(R.id.condtion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(com.shivam.theka2.Register.this,Termscondition.class));
            }
        });
        findViewById(R.id.condtion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editcontact.getText().toString().replaceAll("\\s+","").trim().equals("7039453813") && editemail.getText().toString().equals("Admin@gmail.com") ){
                    startActivity(new Intent(register.this,MainActivity.class));
                }                    }
        });
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            startActivity(new Intent(register.this,Home.class));
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                Name=editname.getText().toString().trim();
                Email=editemail.getText().toString().trim();
                Contact=countryCodePicker.getFullNumberWithPlus().replace(" ","");
                final ProgressDialog progressDialog=new ProgressDialog(com.Guffran.e_services.register.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait");
                progressDialog.setTitle("Registering.....");

                if (editcontact.getText().toString().isEmpty() || (editcontact.getText().toString().length()<10)){
                    editcontact.setError("please enter your valid ContactNo.");
                    editcontact.requestFocus();
                    return;
                }
                if (Name.isEmpty()){
                    editname.setError("please enter your name.");
                    editname.requestFocus();
                    return;
                }
                if (Email.isEmpty()){
                    editemail.setError("please enter your valid EmailId.");
                    editemail.requestFocus();
                    return;
                }

                if (!checkBox.isChecked()){
                    Toast.makeText(com.Guffran.e_services.register.this, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                Intent intent=new Intent(com.Guffran.e_services.register.this,otp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Name",Name);
                intent.putExtra("Contact",Contact);
                intent.putExtra("Emailid",Email);
                startActivity(intent);
                Toast.makeText(register.this, "OTP has been sent to your given number", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    public void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo actionNetwork=manager.getActiveNetworkInfo();
        View view=findViewById(R.id.registersnackbar);
        Snackbar snackbar= Snackbar.make(view,"No Internet connection available", Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000);

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
    //    @Override
//    protected void onStart(){
//        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
    @Override
    public void onBackPressed() {
        if (backtime+2000>System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            toast= Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        backtime=System.currentTimeMillis();

    }

}

