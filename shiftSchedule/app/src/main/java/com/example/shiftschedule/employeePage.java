package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class employeePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
    }

    // onClick function for registerButton to take you to register Page/Activity
    public void goToAddEmployee(View view) {
        Log.d("openattempt", "before opening");
        Intent intent = new Intent(employeePage.this, eregisterpage.class);
        startActivity(intent);
        Log.d("open attempt2", "after opening");
    }

    public void LPgoBack (View view){
        finish();
    }
}