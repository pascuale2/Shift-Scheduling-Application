package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class landingPage extends AppCompatActivity {

    /*
    Landing Page code created by Alex Creencia, edited by:
    .
    .
    .
    MEMBERS:
    SharedPreferences storage      - Shared Preferences files that references accountStorage Prefs file
    Bundle extras                  - Bundle passed to this activity to "remember" the username throughout app usage
    String usernameKey             - The actual username string that holds the username used to login
     */
    private SharedPreferences storage;
    private Bundle extras;
    private String usernameKey;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // grabbing the right sharedPreferences file
        storage = getApplicationContext().getSharedPreferences("accountStorage", Context.MODE_PRIVATE);

        // grabbing the username we passed into this activity through extras Bundle

        extras = getIntent().getExtras();
        if (extras != null) {
            usernameKey = extras.getString("username");
        }

        password = storage.getString(usernameKey, "");
        // making a small pop-up notification appear to show the details of the username and password
        // you can remove comments on the following 3 lines as this is simply showing you guys how to fetch data and display
        //String output = "Username is: " + usernameKey + "\nPassword is: " + password + ".";
        //Toast toast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG);
        //toast.show();

    }

    public void seeEmployees (View view){
        Intent intent = new Intent(landingPage.this, employeePage.class);
        startActivity(intent);
    }

    public void goToSchedule(View view) {
        Intent intent = new Intent(landingPage.this, schedule_month_view.class);
        startActivity(intent);
    }


    public void LGgoBack (View view){
        finish();
    }
}