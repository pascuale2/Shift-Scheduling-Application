package com.example.shiftschedule.employee.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shiftschedule.R;
import com.example.shiftschedule.adapters.employeeAdapter;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.listItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class employeePage extends AppCompatActivity {

    //TODO: Display message if employee list is empty

    // members for displaying the employee list
    private RecyclerView employeeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<listItem> items;

    private SharedPreferences storage;
    private List<Employee> employees = new ArrayList<Employee>();
    private List<String> displayEmployees = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
        storage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        employeeList = (RecyclerView) findViewById(R.id.EPemployeeView);
        // setting the Display format of the employeeList
        layoutManager = new LinearLayoutManager(this);
        employeeList.setLayoutManager(layoutManager);
        employeeList.setHasFixedSize(true);
        items = new ArrayList<listItem>();
        // uncomment following line to clear employee cache
        //clearEmployeeList();
        // grabbing elements from the storage
        fillList(employeeList);
    }

    public void fillList(RecyclerView employees){
        Employee viewEmployee;
        SharedPreferences.Editor editor = storage.edit();
        Gson gson = new Gson();
        Map<String, ?> allEmployees = storage.getAll();
        if (allEmployees.isEmpty()) {
            Toast.makeText(employeePage.this, "No Employees to Display", Toast.LENGTH_SHORT).show();
        }
        for (Map.Entry<String, ?> entry: allEmployees.entrySet()) {

            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String json = storage.getString(entry.getKey(), "");
            viewEmployee = gson.fromJson(json, Employee.class);
            listItem item = new listItem (entry.getKey(), "Full Name: " + viewEmployee.getFullName() + "\n"  +
                    "Trained Opening: " + viewEmployee.isTrainedOpening() +
                    "\nTrained Closing: " + viewEmployee.isTrainedClosing(), viewEmployee.isTrainedClosing(), viewEmployee.isTrainedOpening(), "");
            items.add(item);
            //this.employees.add(viewEmployee);
            //this.displayEmployees.add(viewEmployee.toString());
        }
        this.adapter = new employeeAdapter(this, items);
        employees.setAdapter(adapter);
    }

    // onClick function for registerButton to take you to register Page/Activity
    public void goToAddEmployee(View view) {
        Log.d("openattempt", "before opening");
        Intent intent = new Intent(employeePage.this, eregisterpage.class);
        startActivity(intent);
        Log.d("open attempt2", "after opening");
        finish();
    }

    public void clearEmployeeList() {
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
        editor.commit();
    }
    public void LPgoBack (View view){
        finish();
    }

}