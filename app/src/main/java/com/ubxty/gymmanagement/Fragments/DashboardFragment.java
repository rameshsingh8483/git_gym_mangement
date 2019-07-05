package com.ubxty.gymmanagement.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.ubxty.gymmanagement.Activities.ShowMemberActivity;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.TimeUtil;
import com.ubxty.gymmanagement.Util.Utility;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.modal.AllUser;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1" ;
    private static final String ARG_PARAM2 = "param2" ;

    File[]  files ;

    private List<User> users = new ArrayList<>();
    List<User> allList = new ArrayList<>() ;
    List<User> monthRegisterlist = new ArrayList<>() ;
    List<User> expWeeklist = new ArrayList<>() ;
    List<User> expMonthlist = new ArrayList<>() ;
    List<User> fullPayList = new ArrayList<>() ;
    List<User> halfPayList = new ArrayList<>() ;
    List<User> DeleteList = new ArrayList<>() ;
    Gson gson =  new Gson() ;

    TextView diff_txt ;
    UserServiceImpl userService ;

    TextView delete_member_count ;

    LinearLayout lin_total ,lin_month_user , delete_member ,lin_exp_week ,lin_exp_month  , lin_full_pay ,lin_half_pay ;

    TextView totol_acc , month_register ,exp_weak , exp_month , full_pay_count , half_pay_count ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View  view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        findViewById(view) ;

        getUsersFromDB() ;

        //  setData() ;

        clickListener() ;

        return view ;

    }

    @Override
    public void onResume() {
        super.onResume();


        // setData();
    }



    private void getUsersFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                users = userService.getAll();
                monthRegisterlist = userService.thisMonthUser(Utility.currentMonthYear());
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //usersTextView.setText("Users \n\n " + users);

                totol_acc.setText("( "+users.size()+" )");


                setData();

            }
        }.execute();

    }


    private void clickListener() {

        delete_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(DeleteList.size() == 0){

                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();

                }else {

                    Intent intent = new Intent(getContext(), ShowMemberActivity.class);
                    intent.putExtra("ModalList", (Serializable) DeleteList);
                    intent.putExtra("type" , "delete") ;
                    startActivity(intent);

                }

            }
        });

        lin_full_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fullPayList.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();
                }else {

                    Intent intent = new Intent(getContext(), ShowMemberActivity.class);
                   // intent.putExtra("ModalList", (Serializable) fullPayList);
                    intent.putExtra("data" , "5") ;
                    startActivity(intent);
                }

            }
        });

        lin_half_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(halfPayList.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();

                }else {

                    Intent intent = new Intent(getContext(), ShowMemberActivity.class);
                    //intent.putExtra("ModalList", (Serializable) halfPayList);
                    intent.putExtra("data" , "6") ;
                    startActivity(intent);
                }



            }
        });


        lin_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(users.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();

                }else {

                    Intent intent = new Intent(getContext(), ShowMemberActivity.class);
                   // intent.putExtra("ModalList", (Serializable) users);
                    intent.putExtra("data" , "1") ;
                    startActivity(intent);
                }


            }
        });

        lin_month_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(monthRegisterlist.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();

                }else {
//                    Intent intent = new Intent(getContext(), MembersActivity.class);
//                    intent.putExtra("ModalList", monthRegisterlist);
//                    intent.putExtra("type" , "") ;
//                    startActivity(intent);

                }

            }
        });

        lin_exp_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expWeeklist.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();
                }else {


//                    Intent intent = new Intent(getContext(), MembersActivity.class);
//                    intent.putExtra("ModalList", expWeeklist);
//                    intent.putExtra("type" , "") ;
//                    startActivity(intent);

                }
            }
        });

        lin_exp_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expMonthlist.size() == 0){
                    Toast.makeText(getContext() , "No Member" , Toast.LENGTH_LONG).show();
                }else {
//
//                    Intent intent = new Intent(getContext(), MembersActivity.class);
//                    intent.putExtra("ModalList", expMonthlist);
//                    intent.putExtra("type" , "") ;
//                    startActivity(intent);

                }
            }
        });
    }

    private void setData() {

        try {

            allList.clear();
            monthRegisterlist.clear();
            expWeeklist.clear();
            expMonthlist.clear();
            fullPayList.clear();
            halfPayList.clear();
            DeleteList.clear();

            boolean fullFees = false ;



            // File newdir= getContext().getDir("Registration Files", Context.MODE_PRIVATE);






            for (User  user : users) {

                Log.e("fullPayList" +user.getJoin_month() ,"LISSTTTT" + Utility.currentMonthYear()) ;

                if (user.getJoin_month().equalsIgnoreCase(Utility.currentMonthYear())){
                    monthRegisterlist.add(user) ;
                }


                if (user.getTotalfees().equalsIgnoreCase(user.getPayfees())){

                    fullPayList.add(user) ;
                    fullFees = true ;

                }else{

                    halfPayList.add(user);
                    fullFees = false ;

                }




               // allList.add(user) ;

                if(user.getJoindate().contains(Utility.currentMonth())) {

                    monthRegisterlist.add(user);

                }



//
//                if(fullFees) {
//
//                    String day  = TimeUtil.dateCalculate(user.getJoindate() , "MM/dd/yyyy") ;
//
//                    Log.w("day","day" + day) ;
//
//
//                    //    String Day = String.valueOf(TimeUnit.DAYS.convert(abc, TimeUnit.MILLISECONDS));
//
//                    int dayCount = Integer.parseInt(day);
//
//                    if(dayCount > 24 && dayCount < 31) {
//
//                        expWeeklist.add(user);
//
//                    } else {
//
//                        if(dayCount > 30) {
//
//                            if (Utility.UpdateFeesExpMonth(user)){
//
//                                expMonthlist.add(user);
//                            }
//
//                        }
//
//
//                    }
//
//
//                }



            }




            Log.w("monthRegisterlist","monthRegisterlist" + monthRegisterlist.size()) ;
            Log.w("monthRegisterlist","expWeeklist" + expWeeklist.size()) ;
            Log.w("monthRegisterlist","expMonthlist" + expMonthlist.size()) ;
            Log.w("monthRegisterlist","fullPayList" + fullPayList.size()) ;
            Log.w("monthRegisterlist","halfPayList" + halfPayList.size()) ;





            if(monthRegisterlist.size() > 0){
                month_register.setText("( "+ monthRegisterlist.size()+" )" );
            }

            if(expWeeklist.size() > 0){
                exp_weak.setText("( "+ expWeeklist.size()+" )" );
            }
            if(expMonthlist.size() > 0){
                exp_month.setText("( "+ expMonthlist.size()+" )" );
            }

            if(fullPayList.size()>0)
                full_pay_count.setText("( "+fullPayList.size()+" )");

            if(halfPayList.size()>0)
                half_pay_count.setText("( "+halfPayList.size()+" )");

        }catch (Exception ee){

            Log.w("Exceptionn",">>>" + ee.getMessage()) ;

        }


    }

    private void findViewById(View view) {



        //diff_txt = view.findViewById(R.id.diff_txt) ;
        lin_total = view.findViewById(R.id.lin_total) ;
        lin_month_user = view.findViewById(R.id.lin_month_user) ;
        lin_exp_week = view.findViewById(R.id.lin_exp_week) ;
        lin_exp_month = view.findViewById(R.id.lin_exp_month) ;

        totol_acc = view.findViewById(R.id.totol_acc) ;
        month_register = view.findViewById(R.id.month_register) ;
        exp_weak = view.findViewById(R.id.exp_weak) ;
        exp_month = view.findViewById(R.id.exp_month) ;
        full_pay_count = view.findViewById(R.id.full_pay_count) ;
        half_pay_count = view.findViewById(R.id.half_pay_count) ;

        lin_full_pay = view.findViewById(R.id.lin_full_pay) ;
        lin_half_pay = view.findViewById(R.id.lin_half_pay) ;

        delete_member_count = view.findViewById(R.id.delete_member_count) ;
        delete_member = view.findViewById(R.id.delete_member) ;
        userService = new UserServiceImpl(getActivity());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
