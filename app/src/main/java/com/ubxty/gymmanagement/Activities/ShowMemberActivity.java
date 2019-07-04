package com.ubxty.gymmanagement.Activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ubxty.gymmanagement.Fragments.MemberFragment;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.modal.AllUser;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ShowMemberActivity extends AppCompatActivity {

    RecyclerView recView ;
    List<User> list = new ArrayList<>() ;
    Gson gson = new Gson() ;
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

                    list = userService.getAll();

                }else if (data.equalsIgnoreCase("5")){

                    list  = userService.fullPayUser() ;

                }else  if (data.equalsIgnoreCase("6")){

                    list  = userService.pendingPayUser() ;
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
        public void onBindViewHolder(@NonNull Holder holder, int position) {


            holder.name.setText(list.get(position).getName()) ;
            holder.phone.setText(list.get(position).getPhone());
            holder.join_date.setText(list.get(position).getJoindate());

            Glide.with(getApplicationContext()).load(list.get(position).getProfile_image()).into(holder.profile_image);




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
