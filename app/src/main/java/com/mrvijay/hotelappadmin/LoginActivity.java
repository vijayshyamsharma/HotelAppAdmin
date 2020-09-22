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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login;

    String usernameval;
    String passval;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    ProgressDialog progressDialog;

    boolean loginstatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.loginapply);
        username=findViewById(R.id.usernameadmin);
        password=findViewById(R.id.passwordadmin);

        SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);

        boolean status=sharedPreferences.getBoolean("onlineStatus",false);

        if(status==true)
        {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog=new ProgressDialog(LoginActivity.this);

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                progressDialog.show();

                usernameval = username.getText().toString();
                passval = password.getText().toString();


                auth.signInWithEmailAndPassword(usernameval, passval).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                            startActivity(intent);
                            finish();

                            SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("onlineStatus", true);
                            editor.putString("emailid", usernameval);

                            editor.commit();

                            progressDialog.hide();


                        } else {
                            Toast.makeText(LoginActivity.this, "not a user", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
    }
}


