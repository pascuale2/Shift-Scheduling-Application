package com.example.shiftschedule.employee.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shiftschedule.R;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class eregisterpage extends AppCompatActivity {

    /*
    Employee Register page created by Jaxon Strembitsky
    Employee register Page activity that holds all required information for creating an employee
    Employee Register Page Code Last Edited By Alex Creencia
    MEMBERS


    CHANGELOG:
    Oct 29, 2020 -  Added better date check functionality. Attempted to use Try-Catch instead of doing 3 sub functions.
                    Did not work. Crashed the whole application for some reason and couldn't figure out why in time.
                 -  Also testing Employee Storage implementation.
     */
    // TODO: Can initialize their availability to default values here. That way can call check availability functions straight from this class.
    // If I do this, then I need to pass the context everytime to helper function, (I am NOT passing context as a hard pass to create an Employee class).
    private final int maxYear = 2020;
    private EditText email;
    private EditText emailConfirm;
    private String date;
    private EditText dateYear;
    private EditText dateMonth;
    private EditText dateDay;
    private EditText age;
    private EditText sex;
    private Button submitButton;
    private SharedPreferences storage;
    private SharedPreferences employeeAvailabilityStorage;                     // variable holding the sharedPreferences storage file
    private EmployeeAvailability availability;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eregisterpage);

        Window window = eregisterpage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(eregisterpage.this, R.color.hot_pink));

        // point members to respective design elements
        email = (EditText)findViewById(R.id.ERPemailTextView);
        emailConfirm = (EditText)findViewById(R.id.ERPconfirmEmail);
        dateYear = (EditText)findViewById(R.id.ERPDateYear);
        dateDay = (EditText)findViewById(R.id.ERPDay);
        age = (EditText)findViewById(R.id.ERPAge);
        dateMonth = (EditText) findViewById(R.id.ERPMonth);
        sex = (EditText)findViewById(R.id.ERPSex);
        submitButton = (Button)findViewById(R.id.ERPsubmitButton);

        // addTextChangedListener changes the focus of the textbox based on the length
        dateYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (dateYear.getText().toString().length() == 4) { dateMonth.requestFocus(); }
            }
        });
        dateMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (dateMonth.getText().toString().length() == 2) { dateDay.requestFocus(); }
            }
        });
        dateDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dateDay.getText().toString().length() == 2) { age.requestFocus();}
            }
        });

        // grab preferences file to hold information
        storage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        employeeAvailabilityStorage = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);
        // the following 3 lines is to remove any test register's that you guys have done. (Since it will stay in memory forever)
        // uncomment these 3 lines and make sure before you exit app you go to register page to wipe
        //SharedPreferences.Editor editor = storage.edit();
        //editor.clear();
        //editor.commit();
    }

    // if email exists within the storage already return true
    protected boolean emailExist(String email) {
        return storage.contains(email);
    }
    // checks if email in textField is an ACTUAL email and not just some random string
    protected boolean isEmailValid(String emailCheck) {
        return Patterns.EMAIL_ADDRESS.matcher(emailCheck).matches();
    }

    // checks if email in both text fields match
    protected boolean confirmEmail(String emailText, String emailTextConfirm) {
        return emailText.toLowerCase().equals(emailTextConfirm);
    }

    protected boolean validYear(String yearText){

        if ((dateYear.getText().toString().contains("."))){
            return false;
        }

        int checkValue = Integer.parseInt(yearText);
        return (checkValue <= maxYear);
    }

    // checks if the month number is less than or equal to 12 (12 total months)
    protected boolean validMonth(String monthText) {
        if ((dateMonth.getText().toString().contains("."))){
            return false;
        }

        int checkValue = Integer.parseInt(monthText);
        return (checkValue <= 12);
    }

    // checks if the day of the month is less than 31.
    protected boolean validDay(String dayText) {
        if ((dateDay.getText().toString().contains("."))){
            return false;
        }

        int checkValue = Integer.parseInt(dayText);
        return (checkValue <= 31);
    }



    //checks if date is formatted properly, returns true if formatted yyyy/MM/d
    protected boolean confirmDateFormat(String dateText) {
        return dateText.matches("\\d{4}[/]\\d{2}[/]\\d{2}");
    }
    protected String prepareDate(String dayText, String monthText, String yearText) {



        if ((!validMonth(dateMonth.getText().toString())) || (!validDay(dateDay.getText().toString()
        )) || (!validYear(dateYear.getText().toString()))) {
            Log.d("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH", "this is valid month " +  validMonth(dateMonth.getText().toString()) + "this is valid day " + validDay(dateDay.getText().toString()) + "this is valid year " + validYear(dateYear.getText().toString()));
            Toast.makeText(eregisterpage.this, "ERROR: Date not formatted; must be of format YYYY/MM/DD", Toast.LENGTH_LONG).show();
            return ""   ;
        }
        else {
            String formattedDate = yearText + "/" + monthText + "/" + dayText;
            return formattedDate;
        }


        /*
        else if (!validDay(dateDay.getText().toString()) ) {
            Toast.makeText(eregisterpage.this, "ERROR: Day number is higher than 31. Please try again", Toast.LENGTH_LONG).show();
            return "";
        }
        else {
            String formattedDate = yearText + "/" + monthText + "/" + dayText;
            return formattedDate;
        }
        */
    }

    protected LocalDate convertToDate(String dateText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        LocalDate localDate = LocalDate.parse(dateText,formatter);
        Log.d("localDate", "this is localDate: " + localDate);
        return localDate;
    }
    public void ERPsubmitAction(View view){
        // need to create LocalDate string first
        if (dateDay.getText().toString().equals("") || dateMonth.getText().toString().equals("") || dateYear.getText().toString().equals("")) {
            Toast.makeText(eregisterpage.this, "ERROR: Empty fields, please make sure date fields are filled of the form YYYY MM DD", Toast.LENGTH_SHORT).show();
            return;
        }
        String formattedDate = prepareDate(dateDay.getText().toString(), dateMonth.getText().toString(), dateYear.getText().toString());
        if (formattedDate.equals("")) {
            return;
        }
        this.date = formattedDate;
        Log.d("Formatted Date Beyond", "created the formatted date : " + formattedDate);
        if (confirmEmail(email.getText().toString().toLowerCase(), emailConfirm.getText().toString().toLowerCase()) &&
                isEmailValid(emailConfirm.getText().toString()) &&
            confirmDateFormat(date)) {

            if (emailExist(emailConfirm.getText().toString())) {
                String existMessage = "ERROR: Email already exists. Please try again";
                Toast.makeText(eregisterpage.this, existMessage, Toast.LENGTH_LONG).show();
                return;
            }

            // cleared All checks, need to create Employee object
            // to get an integer value, java can't do it directly, we need to put it into a string, THEN parse it for an int. Default value is 0
            String ageString = age.getText().toString();
            if (ageString.length() == 0) {
                ageString = "0";
            }

            //boolean test = confirmDateFormat(date);
            //Log.d("booleanDate", "value of date: " + test);

            //LocalDate EmployedDate = convertToDate(this.date);
            //Log.d("afterDateCheck", "this is the formattedDate: " + EmployedDate);
            Employee newEmployee = new Employee(emailConfirm.getText().toString(), "", Integer.parseInt(ageString), this.date, false, false);
            newEmployee.setSex(sex.getText().toString());
            Log.d("storing Date: ","This is the stored date : " + newEmployee.getDateString());
            SharedPreferences.Editor editor = storage.edit();

            // Storing an object to the sharedPreferences File.
            // Need to use Gson to store objects to sharedPreferences
            // Gson is an easy way of converting an object to a JSON (which is inherently a string, just with members attached)
            // Email will be the key to access the object related to the email (in this case Employee information)
            Gson gson = new Gson();
            String employeeString = gson.toJson(newEmployee);
            Log.d("Gson Employee: ", "This is the Gson representation of Employee: " + employeeString);
            editor.putString(emailConfirm.getText().toString(), employeeString);
            editor.commit();
            // showing success popup notification

            // Create the employees basic availability upon employee creation -->
            if (!employeeAvailabilityStorage.contains(newEmployee.getEmail())) {
                availability = new EmployeeAvailability(newEmployee.getEmail());
                // save changes to Availability storage
                SharedPreferences.Editor availabilityEditor = employeeAvailabilityStorage.edit();
                String availabilityString = gson.toJson(this.availability);
                availabilityEditor.putString(newEmployee.getEmail(), availabilityString);
                editor.commit();
            }
            String success = "Register was successful";
            Toast toast = Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(eregisterpage.this, employeePage.class);
            startActivity(intent);
            finish();

        } else {
            Log.d("notMatch", "before toast");
            String errorMessage;
            if (confirmEmail(email.getText().toString(), emailConfirm.getText().toString()) != true){
                errorMessage = "Emails does not match. Please try again";
            }
            else if (!confirmDateFormat(date)){
                errorMessage = "Date not formatted properly. Must be in format yyyy/MM/d";
            }
            else {
                errorMessage = "ERROR: Invalid Email input. Please make sure email is a valid email";
            }
            Toast.makeText(eregisterpage.this, errorMessage, Toast.LENGTH_LONG).show();
            Log.d("notMatch1", "afterToast");

        }
    }


}