package com.ubxty.gymmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ubxty.gymmanagement.Activities.HomeActivity;

public class MainActivity extends AppCompatActivity {

     TextView mobile , password , forgot , signup;
     Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                startActivity(new Intent(getApplicationContext() , HomeActivity.class));
            }
        });
    }
}
