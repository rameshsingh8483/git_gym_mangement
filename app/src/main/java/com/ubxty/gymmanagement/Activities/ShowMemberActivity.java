package com.ubxty.gymmanagement.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ubxty.gymmanagement.Fragments.MemberFragment;
import com.ubxty.gymmanagement.Fragments.UserProfileFragment;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.Utility;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.modal.AllUser;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowMemberActivity extends AppCompatActivity {

    RecyclerView recView ;
    List<User> list = new ArrayList<>() ;

    String data = "" ;
    UserServiceImpl userService ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member);
        userService = new UserServiceImpl(getApplicationContext());
        recView = findViewById(R.id.recView) ;
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        data = getIntent().getStringExtra("data") ;


        getUsersFromDB() ;



    }


    private void getUsersFromDB() {



        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                if (data.equalsIgnoreCase("1")){

                    list = userService.getAll() ;

                }
                if (data.equalsIgnoreCase("7")){

                    list = userService.getAllDelete() ;

                } else if (data.equalsIgnoreCase("2")){

                    list = userService.thisMonthUser(Utility.currentMonthYear()) ;


                }else  if (data.equalsIgnoreCase("5")){

                    list  = userService.fullPayUser() ;

                }else  if (data.equalsIgnoreCase("6")){

                    list  = userService.pendingPayUser() ;

                }else  if (data.equalsIgnoreCase("3")){

                    List<User> Alllist = new ArrayList<>() ;
                    Alllist = userService.getAll();

                    for (User user : Alllist) {

                        if (!user.getPayfull_fees_date().equalsIgnoreCase("0")) {

                            Date d1 = new Date();
                            Date d2 = new Date(user.getPayfull_fees_date());

                            long day = TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());

                            if (day > -7 && day < 0) {

                                list.add(user);

                            }

                        }
                    }

                }else  if (data.equalsIgnoreCase("4")){

                    List<User> Alllist = new ArrayList<>() ;
                    Alllist = userService.getAll();

                    for (User user : Alllist) {

                        if (!user.getPayfull_fees_date().equalsIgnoreCase("0")) {

                            Date d1 = new Date();
                            Date d2 = new Date(user.getPayfull_fees_date());

                            long day = TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());

                            if (day > -1 && day < 0) {

                                list.add(user) ;

                            }

                        }
                    }

                }


                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //usersTextView.setText("Users \n\n " + users);

                setAdapter() ;

            }
        }.execute();

    }

    private void setAdapter() {


        RecycleMember recycleMember = new RecycleMember(list) ;
        recView.setAdapter(recycleMember) ;



    }


    class  RecycleMember extends RecyclerView.Adapter<RecycleMember.Holder> {


        List<User> list = new ArrayList<>() ;

        public RecycleMember(List<User> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext()) ;
            View view = inflater.inflate(R.layout.member_item , viewGroup , false );

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, final int position) {


            holder.name.setText(list.get(position).getName()) ;
            holder.phone.setText(list.get(position).getPhone());
            holder.join_date.setText(list.get(position).getJoindate());

            Glide.with(getApplicationContext()).load(list.get(position).getProfile_image()).into(holder.profile_image);

            holder.lin_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent= new Intent(getApplicationContext() , HomeActivity.class) ;

                    Log.w("dddssd","UIDDDD" + list.get(position) .getUid()) ;
                    intent.putExtra("UID",list.get(position).getUid()) ;

                    startActivity(intent) ;

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size() ;
        }

        class Holder extends RecyclerView.ViewHolder {

            TextView name  , phone , join_date;
            ImageView profile_image;
            LinearLayout lin_root ;


            public Holder(@NonNull View itemView) {
                super(itemView);


                lin_root = itemView.findViewById(R.id.lin_root) ;
                name = itemView.findViewById(R.id.name) ;
                phone = itemView.findViewById(R.id.phone) ;
                join_date = itemView.findViewById(R.id.join_date) ;
                profile_image = itemView.findViewById(R.id.profile_image);
            }
        }

    }





}
