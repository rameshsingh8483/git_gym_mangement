package com.ubxty.gymmanagement.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ubxty.gymmanagement.DB.SessionManager;
import com.ubxty.gymmanagement.MainActivity;
import com.ubxty.gymmanagement.R;

public class SplashActivity extends AppCompatActivity {

    SessionManager manager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        manager = new SessionManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;


                if(manager.getString("USER").equalsIgnoreCase("LOGIN")){


                    intent = new Intent(getApplicationContext() , HomeActivity.class);


                }else {

                   intent = new Intent(getApplicationContext() , MainActivity.class);
                }


                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent);

            }
        } , 3500);
    }
}
