package com.ubxty.gymmanagement.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ubxty.gymmanagement.Activities.HomeActivity;

import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.Utility;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.healperdialog.HelperDialog;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {
    View view;
    UserServiceImpl userService ;

    private List<User> users = new ArrayList<>();
    private List<User> allusers = new ArrayList<>();
    private List<User> payfullfeesUser = new ArrayList<>();
    RecyclerView member_rec ;

    FloatingActionButton add_member ;
    MenRecView menRecView ;
    HelperDialog helperDialog ;
    EditText et_search ;
    ImageView iv_close ;
    TextView no_member , delete_all ;

    public MemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_member, container, false);

        helperDialog = new HelperDialog(getActivity()) ;


        findViewById() ;

        getUsersFromDB() ;

        clickListener() ;


        //new InserMember("Sukh" ,"Mander").execute() ;




        return view;
    }

    private void clickListener() {


        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.clear();
                users.addAll(allusers) ;
                et_search.setText("");
                setAdapter();



            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                users.clear() ;
                for (User user : allusers){

                    if (user.getName().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim())){

                        users.add(user) ;


                    }


                }

                setAdapter();



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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

        delete_all = view.findViewById(R.id.delete_all) ;
        no_member = view.findViewById(R.id.no_member) ;
        iv_close = view.findViewById(R.id.iv_close) ;
        et_search = view.findViewById(R.id.et_search) ;
        add_member = view.findViewById(R.id.add_member) ;
        member_rec = view.findViewById(R.id.member_rec) ;
        member_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        userService = new UserServiceImpl(getActivity());

    }


    private void getUsersFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                allusers.clear();
                users = userService.getAll();
                allusers.addAll(users) ;
                payfullfeesUser = userService.fullPayUser() ;
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //usersTextView.setText("Users \n\n " + users);

                setAdapter() ;
                updateAllUser() ;



            }
        }.execute();

}




    public void updateAllUser() {

        try {


            for (User user : payfullfeesUser) {

                long day = Utility.getDaysBetweenDates(user.getPayfull_fees_date());

                Log.w("updateAllUser", "day" + day);

                if (day > 30) {

                    user.setPayfull_fees_date("0");
                    user.setPayFees_status("0");
                    updateUser(user, "no");

                }
            }
        }catch (Exception e){

            e.printStackTrace();
        }



    }

    private void setAdapter() {

        if (users.size() > 0){

            menRecView  = new MenRecView(users) ;
            member_rec.setAdapter(menRecView) ;
            no_member.setVisibility(View.GONE) ;

        }else{

            no_member.setVisibility(View.VISIBLE) ;
            member_rec.setVisibility(View.GONE);


        }



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
                    bundle.putInt("userData" ,list.get(position).getUid() );

                    //bundle.putSerializable("userData" , list.get(position).getUid());
                    UserProfileFragment fragment = new UserProfileFragment() ;
                    fragment.setArguments(bundle) ;
                    ((HomeActivity)getActivity()).loadFragment(fragment , true , "UserProfileFragment");



                }
            });


            holder.lin_root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    longPressDialog(list.get(position)) ;


                    return false;
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

    private void longPressDialog(final User user) {

        TextView delete , contact ;
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.long_press_dialog);


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0f;
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

        delete = dialog.findViewById(R.id.delete)  ;
        contact = dialog.findViewById(R.id.contact)  ;


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helperDialog.showLoader() ;
                user.setIs_deleted("1") ;

                updateUser(user ,"update") ;
                dialog.dismiss() ;

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", user.getPhone() , null)) ;
                startActivity(intent);
                dialog.dismiss();


            }
        });




    }


    private void updateUser(final User user , final String type) {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                userService.updateUsers(user) ;
                users = userService.getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                //usersTextView.setText("Users \n\n " + users);
                helperDialog.dismissLoader(); ;
                if (type.equalsIgnoreCase("update")) {

                    setAdapter();
                }


            }
        }.execute();

    }


}
