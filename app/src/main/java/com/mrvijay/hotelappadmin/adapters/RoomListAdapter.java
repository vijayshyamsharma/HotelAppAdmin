package com.mrvijay.hotelappadmin.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mrvijay.hotelappadmin.LoginActivity;
import com.mrvijay.hotelappadmin.R;
import com.mrvijay.hotelappadmin.datamodels.RoomData;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.MyViewHolder> {


    Context context;
    ArrayList<RoomData> arrayList;

    CollectionReference collectionReference= FirebaseFirestore.getInstance().collection("rooms");



    String userType,email;

    ProgressDialog progressDialog;

    public RoomListAdapter(Context context, ArrayList<RoomData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        SharedPreferences sharedPreferences = context.getSharedPreferences("status", MODE_PRIVATE);

        userType=sharedPreferences.getString("usertype","none");




       if(userType.equals("cus"))
        {
            email=sharedPreferences.getString("emailidcus","none");
        }



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelroomitem,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder(view);

        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final RoomData roomData=arrayList.get(position);


        Glide.with(context).load(roomData.getImgroom()).centerCrop().into(holder.imageView);

        holder.roomname.setText("room-no: "+roomData.getNameroom());
        holder.roomtype.setText("room-type: "+roomData.getTyperoom());
        holder.roomprice.setText("room-price: "+roomData.getPriceroom());
        holder.roomcapacity.setText("capacity: "+roomData.getCaproom());

        holder.checkindate.setText("check-in date: "+roomData.getCheckindate());
        holder.checkoutdate.setText("check-out date: "+roomData.getCheckoutdate());



        if(userType.equals("cus"))
        {


            if(roomData.getBooked().equals("true"))
            {
                holder.available.setText("available");
                holder.available.setTextColor(Color.WHITE);
                holder.btn.setVisibility(View.VISIBLE);
                holder.btn.setTextColor(Color.WHITE);

                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progressDialog=new ProgressDialog(context);

                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                        progressDialog.show();

                        collectionReference.whereEqualTo("nameroom",roomData.getNameroom())
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                {
                                    collectionReference.document(documentSnapshot.getId())
                                            .update("booked",email);

                                    progressDialog.hide();

                                    holder.btn.setVisibility(View.GONE);

                                    arrayList.clear();

                                    notifyDataSetChanged();
                                }
                            }
                        });

                    }
                });

            }
            else
            {
                if(email.equals(roomData.getBooked()))
                holder.available.setText("booked by you");

                else
                    holder.available.setText("booked by: "+roomData.getBooked());


                holder.available.setTextColor(Color.RED);
                holder.btn.setVisibility(View.GONE);
                holder.btn.setTextColor(Color.RED);

            }

        }
        else if(userType.equals("admin"))
        {
            if(roomData.getBooked().equals("true"))
            {
                holder.available.setText("available");
                holder.available.setTextColor(Color.WHITE);
                holder.btn.setVisibility(View.GONE);

            }
            else
            {
                holder.available.setText("booked by: "+roomData.getBooked());
                holder.available.setTextColor(Color.RED);
                holder.btn.setVisibility(View.GONE);
                holder.btn.setTextColor(Color.RED);


            }

        }


        holder.posteddate.setText("posted on: "+roomData.getPosteddate());








    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView roomname,roomtype,roomprice,roomcapacity,checkindate,checkoutdate,posteddate,available;

        ImageView imageView;

        Button btn;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            roomname=itemView.findViewById(R.id.roomname);
            roomtype=itemView.findViewById(R.id.roomtype);

            roomprice=itemView.findViewById(R.id.roomprice);
            roomcapacity=itemView.findViewById(R.id.roomcapcity);

            imageView=itemView.findViewById(R.id.roomimage);

            checkindate=itemView.findViewById(R.id.checkindateav);
            checkoutdate=itemView.findViewById(R.id.checkoutdateav);
            available=itemView.findViewById(R.id.roomavailable);

            posteddate=itemView.findViewById(R.id.postedon);

            btn=itemView.findViewById(R.id.bookbtn);

        }
    }
}
