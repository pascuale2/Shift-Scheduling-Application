package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

public class employeeDetails extends AppCompatActivity {

    /*
     Employee Details done by Alex Creencia

     CHANGE LOG:
     Oct 30        - Page created

     MEMBERS

     */
    // TODO: Change Full Name and add Availability
    private TextView employeeEmail, employeeAge, employeeSex, employeeEmployed;
    private Bundle extras;
    private String emailString;
    private SharedPreferences storage;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        storage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        extras = getIntent().getExtras();

        // point TextViews to corresponding TextView in XML file
        employeeEmail = (TextView)findViewById(R.id.EDEmail);
        employeeAge = (TextView)findViewById(R.id.EDAge);
        employeeSex = (TextView)findViewById(R.id.EDsex);
        employeeEmployed = (TextView)findViewById(R.id.EDdateEmployed);
        Log.d("Inside EmployeeDetails", "Made it inside employeeDetails");
        if (extras != null) {
            Log.d("TextView edit", "right before setting edit text Views");
            Log.d("getStringEmail", "This is getString email: " + extras.getString("email"));
            employeeEmail.setText(extras.getString("email"));
            this.emailString = extras.getString("email");
            String json = storage.getString(this.emailString, "");
            Gson gson = new Gson();
            this.employee = gson.fromJson(json, Employee.class);
            Log.d("Employee Details: ", this.employee.toString());
            employeeAge.setText(Integer.toString(this.employee.getAge()));
            employeeSex.setText(employee.getSex());
            employeeEmployed.setText(this.employee.getDateEmployed());
        }


    }
}