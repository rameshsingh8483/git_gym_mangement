package com.ubxty.gymmanagement.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ubxty.gymmanagement.DB.SessionManager;
import com.ubxty.gymmanagement.Fragments.AddMemberFragment;
import com.ubxty.gymmanagement.Fragments.DashboardFragment;
import com.ubxty.gymmanagement.Fragments.GymFragment;
import com.ubxty.gymmanagement.Fragments.MemberFragment;
import com.ubxty.gymmanagement.MainActivity;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.PathUtil;

import java.io.File;
import java.net.URISyntaxException;

import static com.ubxty.gymmanagement.Fragments.AddMemberFragment.iv_add_image;

public class HomeActivity extends AppCompatActivity implements  DashboardFragment.OnFragmentInteractionListener{

    FrameLayout frame_container;
    BottomNavigationView navigation;
    TabLayout tablauout;
    TextView logout;

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById();

        initTab();
        setListeners();

    }

    private void findViewById() {

    frame_container = findViewById(R.id.frame_container);
   // navigation = findViewById(R.id.navigation);
        tablauout = findViewById(R.id.tablauout);
        logout = findViewById(R.id.logout);



    }

    private void setListeners() {

    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            manager = new SessionManager(getApplicationContext());

            manager.putStrin("USER" ,"");

            Intent intent = new Intent(getApplicationContext() , MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    });
    }

//    public void loadFragment(Fragment fragment) {
//        String backStateName = fragment.getClass().getName();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
//        if (!fragmentPopped) {
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.frame_container, fragment);
//            fragmentTransaction.addToBackStack(backStateName);
//            fragmentTransaction.commit();
//        }
//    }

    public void loadFragment(Fragment fragment,boolean isBackStack,String tag){

        Log.w("Fragment","FRAGMENT");
        if (tag.equalsIgnoreCase("Home")){
            isBackStack = false ;
        }

        if (isBackStack) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(tag).commit();
            int count = getSupportFragmentManager().getBackStackEntryCount();
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        } }


        public void  initTab(){

            tablauout.addTab(tablauout.newTab().setText("Members"));
            tablauout.addTab(tablauout.newTab().setText("Dashboard"));
            tablauout.addTab(tablauout.newTab().setText("Gym"));

            loadFragment(new MemberFragment() , true , "MemberFragment");



            tablauout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    int position = tab.getPosition();
                    switch (position){

                        case 0:{

                            loadFragment(new MemberFragment() , true , "MemberFragment");
                            break;
                        }
                        case 1:{

                            loadFragment(new DashboardFragment() , true , "DashboardFragment");
                            break;
                        }
                        case 2:{

                            loadFragment(new GymFragment() , true , "GymFragmen");
                            break;
                        }

                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.w("onActivityResult" ,"requestCode>>>>>>" + requestCode);



        if (requestCode == 102 && resultCode == RESULT_OK){



            try {
//                String path =  PathUtil.getPath(getApplicationContext(),data.getData());

//                uploadFile = new File(path) ;


//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                iv_product.setImageBitmap(photo);


                AddMemberFragment.iv_user.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(AddMemberFragment.uploadFile).into(AddMemberFragment.iv_user);


                AddMemberFragment.iv_add_image.setVisibility(View.GONE);
                AddMemberFragment.ll_add_image.setVisibility(View.GONE);


            } catch (Exception e) {
                e.printStackTrace();

//                Toast.makeText(getApplicationContext(),getString(R.string.someting_went_wromg),Toast.LENGTH_LONG).show();
            }


        }
        if (requestCode == 10 && resultCode == RESULT_OK){

            Log.w("DATA","URI >>> "+data.getDataString());

            try {
                String path =  PathUtil.getPath(getApplicationContext(),data.getData());

                AddMemberFragment.uploadFile = new File(path) ;

                AddMemberFragment.iv_user.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(path).into(AddMemberFragment.iv_user);


                AddMemberFragment.iv_add_image.setVisibility(View.GONE);
                AddMemberFragment.ll_add_image.setVisibility(View.GONE);


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
