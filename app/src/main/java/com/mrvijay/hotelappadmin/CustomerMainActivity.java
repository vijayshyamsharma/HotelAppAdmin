package com.mrvijay.hotelappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mrvijay.hotelappadmin.datamodels.RoomData;
import com.mrvijay.hotelappadmin.ui.fragments.BookingFragment;
import com.mrvijay.hotelappadmin.ui.fragments.HomeFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.view.View.GONE;

public class CustomerMainActivity extends AppCompatActivity {

    boolean status;

    Bitmap bitmap;



    ProgressDialog progressDialog;



    ProgressBar progressBar;







    FrameLayout frameLayout;

    HomeFragment homeFragment;


    BottomNavigationView bottomNavigationView;

    FloatingActionButton fab;

    public static final String HOME_FRAGMENT_MESSAGE="HOME_FRAGMENT";
    public static final String BOOKING_FRAGMENT_MESSAGE="BOOKING_FRAGMENT";

    CollectionReference roomsref= FirebaseFirestore.getInstance().collection("rooms");






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);


        SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);

        status=sharedPreferences.getBoolean("onlineStatuscus",false);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("usertype", "cus");

        editor.commit();




        frameLayout=findViewById(R.id.framelayoutcontainercus);
        bottomNavigationView=findViewById(R.id.navigationview);
        fab=findViewById(R.id.fab);


        homeFragment=new HomeFragment();


        getSupportFragmentManager().beginTransaction().add(R.id.framelayoutcontainercus,homeFragment,HOME_FRAGMENT_MESSAGE).commit();


    }






    static String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void onBackPressed() {

        if(status==true)
        {
            Intent intent=new Intent(CustomerMainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}