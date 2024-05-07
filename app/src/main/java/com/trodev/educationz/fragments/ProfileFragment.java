package com.trodev.educationz.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trodev.educationz.R;
import com.trodev.educationz.User;
import com.trodev.educationz.activity.SignInActivity;

public class ProfileFragment extends Fragment {

    CardView btn_logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    TextView nameET, vp_tv, email_TV, pass_TV, hd_tv, logout_TV;
    LinearLayout data_ll;
    CardView notification_button, Feedback_btn;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        btn_logout = view.findViewById(R.id.btn_logout);
        nameET = view.findViewById(R.id.nameET);
        vp_tv = view.findViewById(R.id.vp_tv);
        hd_tv = view.findViewById(R.id.hd_tv);
        data_ll = view.findViewById(R.id.data_ll);
        email_TV = view.findViewById(R.id.email_TV);
        pass_TV = view.findViewById(R.id.pass_TV);
        logout_TV = view.findViewById(R.id.logout_TV);

        /*Card view design*/
        notification_button = view.findViewById(R.id.notification_button);
        Feedback_btn = view.findViewById(R.id.Feedback_btn);

        Feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(getActivity(), ProgressActivity.class));
            }
        });

        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(getContext(), NotificationActivity.class);
                // startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignInActivity.class));
                Toast.makeText(getContext(), "log-out successful", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
            }
        });


        data_ll.setVisibility(View.GONE);
        hd_tv.setVisibility(View.GONE);
        vp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data_ll.setVisibility(view.VISIBLE);
                hd_tv.setVisibility(View.VISIBLE);
                vp_tv.setVisibility(View.GONE);

            }
        });

        hd_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data_ll.setVisibility(view.GONE);
                vp_tv.setVisibility(View.VISIBLE);
                hd_tv.setVisibility(View.GONE);
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {

                    /*get user name from firebase database*/
                    String uname = userProfile.usersname;
                    String uemail = userProfile.email;
                    String pass = userProfile.password;

                    /*set name on profile*/
                    nameET.setText(uname);
                    email_TV.setText("E-mail:- " + uemail);
                    pass_TV.setText("Password:- " + pass);

                    logout_TV.setText("Good bye " + uname);

                    /*toast sms*/
                    Toast.makeText(getActivity(), uname + " your data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

//        mySwitch = view.findViewById(R.id.mySwitch);
//        sharedPreferences = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
//
//        // Load the saved switch state (if any)
//        boolean switchState = sharedPreferences.getBoolean(SWITCH_STATE, false);
//        mySwitch.setChecked(switchState);
//
//        // Send switch state to Activity2
//        Intent intent = new Intent(getActivity(), NotificationActivity.class);
//        PaymentOnlineActivity.SwitchState state = new PaymentOnlineActivity.SwitchState(switchState);
//        intent.putExtra("switch_state", state);
//        startActivity(intent);

        return view;
    }

    // Parcelable class to hold switch state
//    public static class SwitchState implements Parcelable {
//        public final boolean isChecked;
//
//        public SwitchState(boolean isChecked) {
//            this.isChecked = isChecked;
//        }
//
//        protected SwitchState(Parcel in) {
//            isChecked = in.readByte() != 0;
//        }
//
//        public static final Parcelable.Creator<SwitchState> CREATOR = new Creator<SwitchState>() {
//            @Override
//            public SwitchState createFromParcel(Parcel in) {
//                return new SwitchState(in);
//            }
//
//            @Override
//            public SwitchState[] newArray(int size) {
//                return new SwitchState[size];
//            }
//        };
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeByte((byte) (isChecked ? 1 : 0));
//        }
//    }
}