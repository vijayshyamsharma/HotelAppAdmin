package com.mrvijay.hotelappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,phone;
    Button regis;

    TextView cuslogin,adminlogin;

    String usernameval;
    String passval;
    String phoneval;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regis=findViewById(R.id.regisapply);
        username=findViewById(R.id.usernameregisval);
        password=findViewById(R.id.passwordregisval);
        phone=findViewById(R.id.phoneregisval);

        cuslogin=findViewById(R.id.logincustomer);
        adminlogin=findViewById(R.id.loginadmin);

        cuslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegisterActivity.this,CustomerLoginActivity.class);
                startActivity(intent);

            }
        });

        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                progressDialog=new ProgressDialog(RegisterActivity.this);

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                progressDialog.show();

                usernameval = username.getText().toString();
                passval = password.getText().toString();

                    auth.createUserWithEmailAndPassword(usernameval, passval).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                Toast.makeText(RegisterActivity.this, "successfully registered...", Toast.LENGTH_SHORT).show();


                                progressDialog.hide();


                            } else {
                                Toast.makeText(RegisterActivity.this, "not a user", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });





            }
        });
    }
}