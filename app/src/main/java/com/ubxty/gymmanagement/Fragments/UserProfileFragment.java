package com.ubxty.gymmanagement.Fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.ubxty.gymmanagement.Activities.HomeActivity;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.Utility;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    ImageView iv_user ;
    View view ;

    FloatingActionButton btn_call ;
    TextView name ,pay_fees_date , phone , address ,join_date , full_fees , pay_fees , pending_fees  ;

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

        getUsersFromDB() ;


        clickListener() ;


        return  view ;

    }

    @Override
    public void onResume() {
        super.onResume();

        getUsersFromDB() ;

        //setData() ;

    }

    private void clickListener() {







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

    private void payfeesDialog() {

        final EditText reg_date  ,  full_fees , pay_fees , pending_fees , pay_date  ;

        Button btn_submit , btn_cencel ;

         final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pay_fees_dialog);


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0f;
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

        reg_date = dialog.findViewById(R.id.reg_date) ;
        full_fees = dialog.findViewById(R.id.full_fees) ;
        pay_fees = dialog.findViewById(R.id.pay_fees) ;
        pending_fees = dialog.findViewById(R.id.pending_fees) ;
        pay_date = dialog.findViewById(R.id.pay_date) ;
        btn_submit = dialog.findViewById(R.id.btn_submit) ;
        btn_cencel = dialog.findViewById(R.id.btn_cencel) ;

        full_fees.setText(""+ user.getTotalfees());
        pay_fees.setText(""+ user.getPayfees());
        pending_fees.setText(""+ user.getPendingfees()) ;
        reg_date.setText(""+ user.getJoindate()) ;


        pay_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                showCalendar(pay_date) ;



            }
        });





        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {

                        user.setPayfees(user.getTotalfees());
                        user.setPendingfees("0") ;
                        user.setPayFees_status("1");
                        user.setPayfull_fees_date(pay_date.getText().toString()) ;

                        userService.updateUsers(user) ;

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void agentsCount) {
                        //usersTextView.setText("Users \n\n " + users);

                        dialog.dismiss() ;
                        Toast.makeText(getContext() , "Fees Pay Sucessfully", Toast.LENGTH_SHORT).show();
                        setData() ;


                    }
                }.execute();







            }
        });


        btn_cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss() ;

            }
        });

    }



    public  void showCalendar(final EditText join_date){
        int syear , smonth , sday;

        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

        String clicked_date = join_date.getText().toString();
        String str_year , str_month,str_day;

//        if(clicked_date.isEmpty()) {
//
//            str_year = clicked_date.substring(0, 4);
//            str_month = clicked_date.substring(5, 7);
//            str_day = clicked_date.substring(8, 10);
//
//            syear = Integer.parseInt(str_year);
//            smonth = Integer.parseInt(str_month);
//            sday = Integer.parseInt(str_day);
//
//        }else{


        str_year = date.substring(0, 4);
        str_month = date.substring(5, 7);
        str_day = date.substring(8, 10);

        syear = Integer.parseInt(str_year);
        smonth = Integer.parseInt(str_month);
        smonth = smonth - 1 ;
        sday = Integer.parseInt(str_day);

//        }



        DatePickerDialog dpd = new DatePickerDialog(getActivity() , new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String strmonth ;
                String strday;


                int mon = monthOfYear + 1 ;
                if (mon < 10){
                    strmonth = "0"+mon ;

                }else{

                    strmonth = ""+mon ;

                } if (dayOfMonth < 10){

                    strday = "0"+dayOfMonth ;

                }else{

                    strday = "" + dayOfMonth ;

                } join_date.setText(year+"/"+strmonth+"/"+strday) ;

//                list.get(i).setCalendar(join_date.getText().toString()) ;





            } },syear,smonth,sday); dpd.show();
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

        if (user.getPayFees_status().equalsIgnoreCase("1")) {

            pay_fees_date.setText("" + user.getPayfull_fees_date());

        }else {

            pay_fees_date.setText("Pending yet");


        }

            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder( R.id.pay_fees , R.drawable.delete)
                            .create()


            );
            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder( R.id.collapseActionView, R.drawable.ic_telephone)
                            .create()


            );

        if (user.getPayFees_status().equalsIgnoreCase("0")){

            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder( R.id.beginning, R.drawable.ic_pay)
                            .create()


            );


        }




        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.pay_fees :


                        //userService = new UserServiceImpl(getActivity());

                        new DeleteMember().execute() ;


                        break;

                    case R.id.collapseActionView :


                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                        startActivity(intent);

                        break;


                    case  R.id.beginning  :

                        payfeesDialog() ;

                        break ;


                    default:
                        return false;
                }

                return  true ;
            }
        });





    }


    private void getUsersFromDB() {



        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                int[] s = {getArguments().getInt("userData")} ;

                List<User> users = userService.getUserByid(s);

                user = users.get(0) ;
                Log.e("AsyncTask","users" + users.size()) ;

                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //usersTextView.setText("Users \n\n " + users);

                setData() ;


            }
        }.execute();

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
        pay_fees_date = view.findViewById(R.id.pay_fees_date) ;

        speedDialView = view.findViewById(R.id.speedDial);
        userService = new UserServiceImpl(getActivity());





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
