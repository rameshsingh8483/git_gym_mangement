package com.ubxty.gymmanagement.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.ubxty.gymmanagement.Activities.HomeActivity;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    ImageView iv_user ;
    View view ;

    FloatingActionButton btn_call ;
    TextView name , phone , address ,join_date , full_fees , pay_fees , pending_fees  ;

    User user ;
    SpeedDialView speedDialView ;
    UserServiceImpl userService ;




    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_profile, container, false);

        findViewById() ;

        setData() ;

        clickListener() ;


        return  view ;

    }

    private void clickListener() {




        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.add_member:


                        //userService = new UserServiceImpl(getActivity());


                        new DeleteMember().execute() ;


                        break;

                    case R.id.phone:


                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                        startActivity(intent);

                        break;




                    default:
                        return false;
                }

                return  true ;
            }
        });



        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (phone.getText().toString().isEmpty()){

                    Toast.makeText(getContext(), "No Number Available", Toast.LENGTH_LONG).show();

                }else {

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                    startActivity(intent);

                }


            }
        });




    }

    private void setData() {


        Glide.with(getContext()).load(user.getProfile_image()).into(iv_user) ;

        name.setText("" + user.getName());
        phone.setText("" + user.getPhone());
        address.setText("" + user.getAddress());
        join_date.setText("" + user.getJoindate());
        full_fees.setText("" + user.getTotalfees());
        pay_fees.setText("" + user.getPayfees()) ;
        pending_fees.setText("" + user.getPendingfees()) ;

    }

    private void findViewById() {

        btn_call = view.findViewById(R.id.btn_call) ;
        iv_user = view.findViewById(R.id.iv_user) ;
        name = view.findViewById(R.id.name) ;
        phone = view.findViewById(R.id.phone) ;
        address = view.findViewById(R.id.address) ;
        join_date = view.findViewById(R.id.join_date) ;
        full_fees = view.findViewById(R.id.full_fees) ;
        pay_fees = view.findViewById(R.id.pay_fees) ;
        pending_fees = view.findViewById(R.id.pending_fees) ;

        userService = new UserServiceImpl(getActivity());


        speedDialView = view.findViewById(R.id.speedDial);
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder( R.id.add_member, R.drawable.delete)
                        .create()


        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder( R.id.phone, R.drawable.ic_telephone)
                        .create()


        );



        user = (User) getArguments().getSerializable("userData");


    }




    public class DeleteMember extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.



                userService.deleteUser(user) ;


                Thread.sleep(2000);
            } catch (Exception e) {

                e.fillInStackTrace();
                return false;
            }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(name)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(phone);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //     mAuthTask = null;
            //   showProgress(false);

            if (success) {

                Toast.makeText(getContext(), "User Deleted...", Toast.LENGTH_LONG).show();
                //menRecView.notifyDataSetChanged();
                //    setAdapter();

                ((HomeActivity)getActivity()).loadFragment(new MemberFragment() , true , "MemberFragment");


//                HomeActivity homeActivity = new HomeActivity() ;
//                homeActivity.loadFragment(new MemberFragment() , true , "MemberFragment") ;


                // usersTextView.setText("Users \n\n " + users);
            } else {
                // mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            //mAuthTask = null;
            //showProgress(false);


        }

    }







}
