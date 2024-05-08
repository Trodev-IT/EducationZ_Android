package com.trodev.educationz.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trodev.educationz.R;
import com.trodev.educationz.User;
import com.trodev.educationz.activity.SignInActivity;

public class ProfileFragment extends Fragment {

//    CardView btn_logout;
//    private FirebaseUser user;
//    private DatabaseReference reference;
//    private String userID;
//    TextView nameET, vp_tv, email_TV, pass_TV, hd_tv, logout_TV;
//    LinearLayout data_ll;
//    CardView notification_button, Feedback_btn;

    ImageView avatarTv, coverTv;
    TextView nameTv, emailTv, ageTv, instituetTv;
    FloatingActionButton fab;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //StorageReference storageReference;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init firebase
        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Users");

        //init views
        avatarTv= view.findViewById(R.id.avatarTv);
        nameTv= view.findViewById(R.id.nameTv);
        emailTv= view.findViewById(R.id.emailTv);
        ageTv= view.findViewById(R.id.ageTv);
        instituetTv= view.findViewById(R.id.instituteTv);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check until required data get
                for (DataSnapshot ds : snapshot.getChildren()){

                    //get data
                    String username = "Name: "+ ds.child("usersname").getValue();
                    String email = "Email: "+ ds.child("email").getValue();
                    String age = "Age: "+ ds.child("age").getValue();
                    String image = ""+ ds.child("image").getValue();
                    String institute= "Institute Name: "+ds.child("institute").getValue();
                    //String cover = ""+ ds.child("cover").getValue();

                    //set data
                    nameTv.setText(username);
                    emailTv.setText(email);
                    ageTv.setText(age);
                    instituetTv.setText(institute);

                    try {
                        Picasso.get().load(image).into(avatarTv);
                    } catch (Exception e){
                        Picasso.get().load(R.drawable.add_image).into(avatarTv);
                    }

//                    try {
//                        /*Picasso.get().load(cover).into(coverTv);*/
//                        Picasso.get().load(cover).into(coverTv);
//                    } catch (Exception e){
//                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}