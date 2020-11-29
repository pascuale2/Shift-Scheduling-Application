package com.example.shiftschedule.employee.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiftschedule.R;
import com.example.shiftschedule.employee.Employee;
import com.google.gson.Gson;

public class employeeDetails extends AppCompatActivity {

    /*
     Employee Details done by Alex Creencia

     CHANGE LOG:
     Oct 30        - Page created

     MEMBERS

     */
    private TextView employeeEmail, employeeAge, employeeSex, employeeEmployed;
    private Bundle extras;
    private String emailString;
    private SharedPreferences storage;
    private Employee employee;
    private Switch trainedClosing, trainedOpening;
    private EditText fullName;
    private Button availabilityButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Window window = employeeDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(employeeDetails.this, R.color.hot_pink));

        storage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        extras = getIntent().getExtras();

        // point TextViews to corresponding TextView in XML file
        employeeEmail = (TextView)findViewById(R.id.EDEmail);
        employeeAge = (TextView)findViewById(R.id.EDAge);
        employeeSex = (TextView)findViewById(R.id.EDsex);
        employeeEmployed = (TextView)findViewById(R.id.EDdateEmployed);
        trainedClosing = (Switch)findViewById(R.id.EDClosing);
        trainedOpening = (Switch)findViewById(R.id.EDOpening);
        fullName = (EditText)findViewById(R.id.EDFullName);
        availabilityButton = (Button)findViewById(R.id.EDAvailability);
        Log.d("Inside EmployeeDetails", "Made it inside employeeDetails");

        // following if block fills the TextView with the information we already have
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

            fillUsername(fullName, employee.getFullName());


            // changing switches to match whether they are trained or not
            if (this.employee.isTrainedClosing()) {
                trainedClosing.setChecked(true);
            }
            if (this.employee.isTrainedOpening()) {
                trainedOpening.setChecked(true);
        }
        }

    }


    protected void fillUsername(EditText nameBox, String employeeName) {
        nameBox.setText(employeeName);
        if (nameBox.getText().toString().equals("")) {
            nameBox.setText("Full Name");
        }
    }

    public void EDonAvailableClick(View view) {
        // go to new activity
        Intent intent = new Intent(employeeDetails.this, changeAvailability.class);
        intent.putExtra("email", emailString);
        startActivity(intent);
    }

    public void EDsubmitChanges(View view) {
        String checkName = fullName.getText().toString();
        if (checkName.equals("Full Name")){
            this.employee.setFullName("");
        } else {
            this.employee.setFullName(checkName);
        }

        this.employee.setTrainedClosing(trainedClosing.isChecked());
        this.employee.setTrainedOpening(trainedOpening.isChecked());
        Log.d("setting Training: ", "This is Closing " + this.employee.isTrainedClosing() + " And this is Opening: " + this.employee.isTrainedOpening());
        SharedPreferences.Editor editor = storage.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.employee);
        editor.putString(this.employee.getEmail(), json);
        Log.d("submitChanges", "Managed to submit all changes. About to commit");
        editor.commit();
        Toast.makeText(employeeDetails.this, "Changes Saved.", Toast.LENGTH_LONG).show();
    }

}