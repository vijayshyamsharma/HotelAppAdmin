package com.mrvijay.hotelappadmin.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mrvijay.hotelappadmin.R;
import com.mrvijay.hotelappadmin.adapters.RoomListAdapter;
import com.mrvijay.hotelappadmin.datamodels.RoomData;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    RoomListAdapter roomListAdapter;

    ArrayList<RoomData> roomData=new ArrayList<>();

    CollectionReference collectionReference= FirebaseFirestore.getInstance().collection("rooms");



    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.home_fragment,container,false);

        recyclerView=view.findViewById(R.id.roomslistadmin);
        progressBar=view.findViewById(R.id.progressbar1);

        getData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roomListAdapter=new RoomListAdapter(requireContext(),roomData);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(roomListAdapter);

    }

    void getData()
    {
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for(DocumentChange documentSnapshot: queryDocumentSnapshots.getDocumentChanges())
                {
                    roomData.add(documentSnapshot.getDocument().toObject(RoomData.class));



                }

                roomListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
