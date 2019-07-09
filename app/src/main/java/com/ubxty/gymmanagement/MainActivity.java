package com.ubxty.gymmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ubxty.gymmanagement.Activities.HomeActivity;
import com.ubxty.gymmanagement.DB.SessionManager;

public class MainActivity extends AppCompatActivity {

     TextView mobile , password , forgot , signup;

     Button login;

     SessionManager manager  ;
     boolean loginFlag = true ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        manager = new SessionManager(getApplicationContext()) ;

        if (!manager.getString("USER").equalsIgnoreCase("")){

            startActivity(new Intent(getApplicationContext() , HomeActivity.class));

        }


        findViewById();

        setListener();
    }

    private void findViewById() {


          mobile = findViewById(R.id.mobile);
          password = findViewById(R.id.password);
          forgot = findViewById(R.id.forgot);
          signup = findViewById(R.id.signup);
          login = findViewById(R.id.login);



    }

    private void setListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginFlag){

                    startActivity(new Intent(getApplicationContext() , HomeActivity.class));

                }else {


                    if (mobile.getText().toString().equalsIgnoreCase("6280445086") && password.getText().toString().equalsIgnoreCase("qwerty")) {

                        manager.putStrin("USER", "LOGIN");
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));


                    } else {

                        Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }
}
