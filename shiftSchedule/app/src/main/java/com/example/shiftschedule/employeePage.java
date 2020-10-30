package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class employeePage extends AppCompatActivity {

    private ScrollView employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
        employeeList = (ScrollView)findViewById(R.id.EPScrollView);
    }

    public void fillList(ScrollView employees){

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