package com.mrvijay.hotelappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mrvijay.hotelappadmin.datamodels.RoomData;
import com.mrvijay.hotelappadmin.ui.fragments.BookingFragment;
import com.mrvijay.hotelappadmin.ui.fragments.HomeFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;

    Uri uri;

    ProgressDialog progressDialog;



    ProgressBar progressBar;

    TextView addimagetext;

    ImageView addimage;

    TextInputEditText roomno, roomcapacity, roomtype, roomprice,checkindate,checkoutdate;
    TextView posteddate;


    StorageReference storageReference1;

    int GALLERY=1;

    AlertDialog alertDialog;
    FrameLayout frameLayout;

    HomeFragment homeFragment;
    BookingFragment bookingFragment;

    BottomNavigationView bottomNavigationView;

    FloatingActionButton fab;

    public static final String HOME_FRAGMENT_MESSAGE="HOME_FRAGMENT";
    public static final String BOOKING_FRAGMENT_MESSAGE="BOOKING_FRAGMENT";

    CollectionReference roomsref=FirebaseFirestore.getInstance().collection("rooms");

    StorageReference storage=FirebaseStorage.getInstance().getReference();



    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);

        status=sharedPreferences.getBoolean("onlineStatus",false);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("usertype", "admin");

        editor.commit();



        frameLayout=findViewById(R.id.framelayoutcontainer);
        bottomNavigationView=findViewById(R.id.navigationview);
        fab=findViewById(R.id.fab);


        homeFragment=new HomeFragment();
        bookingFragment=new BookingFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.framelayoutcontainer,homeFragment,HOME_FRAGMENT_MESSAGE).commit();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                View view1=getLayoutInflater().inflate(R.layout.roomadd_dialog,null);
                addimage=view1.findViewById(R.id.roomaddimage);
                roomno=view1.findViewById(R.id.roomnumberaddval);
                roomcapacity=view1.findViewById(R.id.roomcapcityaddval);
                roomprice=view1.findViewById(R.id.roompriceaddval);
                roomtype=view1.findViewById(R.id.roomtypeaddval);
                checkindate=view1.findViewById(R.id.roomcheckinaddval);
                checkoutdate=view1.findViewById(R.id.roomcheckoutaddval);
                progressBar=view1.findViewById(R.id.progressbar);
                posteddate=view1.findViewById(R.id.dateadd);

                posteddate.setText(getDate(System.currentTimeMillis()));

                 addimagetext=view1.findViewById(R.id.addimagetext);
                addimagetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        requestPermission();

                    }
                });





                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(true)
                        .setView(view1)
                        .setPositiveButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                if(bitmap!=null && !roomno.getText().toString().isEmpty()
                                 && !roomtype.getText().toString().isEmpty() && !roomprice.getText().toString().isEmpty()
                                && !roomcapacity.getText().toString().isEmpty() && !checkindate.getText().toString().isEmpty()
                                && !checkoutdate.getText().toString().isEmpty())
                                {



                                    storageReference1=storage.child(roomno.getText().toString());

                                   storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                           savedata();
                                       }
                                   });

                                     progressDialog=new ProgressDialog(MainActivity.this);

                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                                    progressDialog.show();






                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "fill all the details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                alertDialog.dismiss();
                            }
                        });

           alertDialog=builder.create();
           alertDialog.show();


            }


        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id=item.getItemId();

                switch (id)
                {
                    case R.id.hometab:
                        createFragment(homeFragment,HOME_FRAGMENT_MESSAGE);
                        break;

                    case R.id.bookedtab:
                        createFragment(bookingFragment,BOOKING_FRAGMENT_MESSAGE);
                        break;

                }

                return true;
            }



        });






    }

    void savedata()
    {





        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {





                RoomData roomData=new RoomData("true",roomcapacity.getText().toString(),String.valueOf(uri),roomno.getText().toString(),
                        roomprice.getText().toString(),roomtype.getText().toString(),checkindate.getText().toString(),
                        checkoutdate.getText().toString(),getDate(System.currentTimeMillis()));




                roomsref.add(roomData);



                progressDialog.hide();


            }
        });

    }

    void createFragment(Fragment fragment,String message)
    {





            Fragment fragment1=getSupportFragmentManager().findFragmentByTag(message);

            if(fragment1==null)
            {

                getSupportFragmentManager().beginTransaction().add(R.id.framelayoutcontainer,fragment,message).commit();
            }
            else {

                if (message.equals(HOME_FRAGMENT_MESSAGE)) {

                    getSupportFragmentManager().beginTransaction().hide(bookingFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(homeFragment).commit();

                } else if (message.equals(BOOKING_FRAGMENT_MESSAGE)) {

                    getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(bookingFragment).commit();
                }


            }



    }

    void savePic()
    {

    }







    public void choosePhotoFromGallary() {



        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent,GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                uri=contentURI;
                addimagetext.setVisibility(GONE);



                try {
                   bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    Glide.with(this).load(bitmap).centerCrop().placeholder(R.drawable.ic_launcher_background)
                            .into(addimage);
                    progressBar.setVisibility(View.VISIBLE);









               


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    void requestPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "Allow the permission...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY);
            }


        }
        else
        {
            choosePhotoFromGallary();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}