package com.ubxty.gymmanagement.Activities;

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

import com.ubxty.gymmanagement.Fragments.MemberFragment;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.database.entity.User;

import java.util.ArrayList;

public class ShowMemberActivity extends AppCompatActivity {

    RecyclerView recView ;
    ArrayList<User> list = new ArrayList<>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member);

        recView = findViewById(R.id.recView) ;
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        list = (ArrayList<User>) getIntent().getSerializableExtra("ModalList");

        RecycleMember recycleMember = new RecycleMember(list) ;

        recView.setAdapter(recycleMember) ;

    }


    class  RecycleMember extends RecyclerView.Adapter<RecycleMember.Holder> {


        ArrayList<User> list = new ArrayList<>() ;

        public RecycleMember(ArrayList<User> list) {
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
