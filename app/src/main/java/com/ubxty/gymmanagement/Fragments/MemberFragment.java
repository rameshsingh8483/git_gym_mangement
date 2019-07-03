package com.ubxty.gymmanagement.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ubxty.gymmanagement.Activities.HomeActivity;
import com.ubxty.gymmanagement.DB.DBHelper;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {
    View view;
    UserServiceImpl userService ;

    private List<User> users = new ArrayList<>();
    RecyclerView member_rec ;

    FloatingActionButton add_member ;
    MenRecView menRecView ;

    public MemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_member, container, false);

        findViewById() ;

        getUsersFromDB() ;

        clickListener() ;


        //new InserMember("Sukh" ,"Mander").execute() ;




        return view;
    }

    private void clickListener() {

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity)getActivity()).loadFragment(new AddMemberFragment() , true , "AddMemberFragment");


//                HomeActivity homeActivity = new HomeActivity() ;
//                homeActivity.loadFragment(new AddMemberFragment() , true , "AddMemberFragment") ;

                //new InserMember("Sukh" ,"Mander").execute() ;


            }
        });


    }

    private void findViewById() {

        add_member = view.findViewById(R.id.add_member) ;
        member_rec = view.findViewById(R.id.member_rec) ;
        member_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        userService = new UserServiceImpl(getActivity());


    }


    private void getUsersFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                users = userService.getAll();
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

        menRecView  = new MenRecView(users) ;
        member_rec.setAdapter(menRecView) ;


    }

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    class MenRecView extends RecyclerView.Adapter<MenRecView.Holder> {

        List<User> list = new ArrayList<>() ;

        public MenRecView(List<User> list) {
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

            Glide.with(getContext()).load(list.get(position).getProfile_image()).into(holder.profile_image);

            holder.lin_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle() ;

                    bundle.putSerializable("userData" , list.get(position));

                    UserProfileFragment fragment = new UserProfileFragment() ;

                    fragment.setArguments(bundle) ;

                    ((HomeActivity)getActivity()).loadFragment(fragment , true , "UserProfileFragment");









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
