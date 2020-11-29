package com.example.shiftschedule.employee.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.R;
import com.example.shiftschedule.bottomLayouts.availabilityBottomSheetDialog;
import com.example.shiftschedule.employee.EmployeeAvailability;
import com.google.gson.Gson;

public class changeAvailability extends AppCompatActivity implements availabilityBottomSheetDialog.BottomSheetListener {

    /*
    changeAvailability and Related Activities created by: Alex Creencia
    CHANGE LOG
    Alex C: Nov 20 - Initialized and created changeAvailability Screen
    Alex C: Nov 22 - Adding more code to changeAvailability
                     Created Basic buttons and on click listeners
    Alex C: Nov 23 - Refactored code to instead use a BottomSheetDialog which looks much cleaner
    Alex C: Nov 24


    Member Variables:
    - Base Buttons -
    Monday
    Tuesday
    Wednesday
    Thursday
    Friday
    Saturday
    Sunday
    Holiday
    - SecondLayout Buttons -
    - Other -
    employeeAvailability                               SharedPreferences files containing specific employees availability
    email                                              Specific employee's email

     */
    private Button monday;                                              // Button(s) for [day]
    private Button tuesday;                                             //
    private Button wednesday;
    private Button thursday;
    private Button friday;
    private Button saturday;
    private Button sunday;
    private Button holiday;                                             // Button for holiday
    private Button saveChanges;
    private Bundle extras;                                              // Bundle holding information to send to second screen
    private SharedPreferences employeeAvailabilityStorage;                     // variable holding the sharedPreferences storage file
    private String email;                                               // the email of the employee
    private EmployeeAvailability availability;                          // The availability object that holds the availability of this specific employee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_availability);
        employeeAvailabilityStorage = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);

        // Point buttons to button objects in activity
        monday = (Button)findViewById(R.id.CA_mondayButton);
        tuesday = (Button)findViewById(R.id.CA_tuesdayButton);
        wednesday = (Button)findViewById(R.id.CA_wednesdayButton);
        thursday = (Button)findViewById(R.id.CA_thursdayButton);
        friday = (Button)findViewById(R.id.CA_fridayButton);
        saturday = (Button)findViewById(R.id.CA_saturdayButton);
        sunday = (Button)findViewById(R.id.CA_SundayButton);
        holiday = (Button)findViewById(R.id.CA_holidayButton);
        saveChanges = (Button)findViewById(R.id.CA_saveChanges);
        extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            Log.i("availability email: ", email);
            if (employeeAvailabilityStorage.contains(email)) {
                // grabbing email from storage file
                Gson gson = new Gson();
                String json = employeeAvailabilityStorage.getString(email, "");
                availability = gson.fromJson(json, EmployeeAvailability.class);

                // call this helper function which changes color of each button depending on the availability of that day if email exists in this availability file
                initializeButtonColor(availability);
            }
            else {
                // intializes a new availability object if employee doesn't exist/already have one. NOTE: All days are default set to CANNOT work on all days
                availability = new EmployeeAvailability(email);
            }
        }
    }

    public void initializeButtonColor(EmployeeAvailability availability) {
        buttonColorChange(monday, availability.getMonday());
        buttonColorChange(tuesday, availability.getTuesday());
        buttonColorChange(wednesday, availability.getWednesday());
        buttonColorChange(thursday, availability.getThursday());
        buttonColorChange(friday, availability.getFriday());
        buttonColorChange(saturday, availability.getSaturday());
        buttonColorChange(sunday, availability.getSunday());
        buttonColorChange(holiday, availability.getHoliday());
    }

    public void buttonColorChange(Button change, Available time) {
        switch (time)
        {
            case OPENING:
                change.setBackground(getDrawable(R.drawable.hot_pink_corner));
                change.setTextColor(Color.rgb(255,255,255));
                break;
            case CLOSING:
                change.setBackground(getDrawable(R.drawable.orange_corner));
                change.setTextColor(Color.rgb(255,255,255));
                break;
            case ALLDAY:
                change.setBackground(getDrawable(R.drawable.gray_corner));
                change.setTextColor(Color.rgb(255,255,255));
                break;
            default:
                change.setBackground(getDrawable(R.drawable.white_corner));
                change.setTextColor(Color.rgb(0,0,0));
        }
    }

    // This function is what calls the bottom screen to appear when button is clicked
    public void bottomSheetShow(Bundle args) {
        availabilityBottomSheetDialog bottomSheet = new availabilityBottomSheetDialog();
        bottomSheet.setArguments(args);
        bottomSheet.show(getSupportFragmentManager(), "availabilityDay");
    }

    // On monday button Click -- The bundle allows us to pass which button was clicked to the bottom screen
    public void onMondayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "monday");
        bottomSheetShow(args);
    }

    // On tuesday button click
    public void onTuesdayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "tuesday");
        bottomSheetShow(args);
    }

    public void onWednesdayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "wednesday");
        bottomSheetShow(args);
    }

    public void onThursdayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "thursday");
        bottomSheetShow(args);
    }

    public void onFridayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "friday");
        bottomSheetShow(args);
    }

    public void onSaturdayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "saturday");
        bottomSheetShow(args);
    }

    public void onSundayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "sunday");
        bottomSheetShow(args);
    }

    public void onHolidayClick(View view) {
        Bundle args = new Bundle();
        args.putString("day", "holiday");
        bottomSheetShow(args);
    }

    public void onSaveAvailabilityClick(View view) {
        // This is some debug statements show that things are saved properly. Can edit out the log statements if you want, they don't affect anything.
        Log.i("EmployeeAvailabilityMonday", "this is availability: " + availability.getMonday().toString());
        Log.i("EmployeeAvailabilityTuesday", "this is availability: " + availability.getTuesday().toString());
        Log.i("EmployeeAvailabilityWed", "this is availability: " + availability.getWednesday().toString());
        Log.i("EmployeeAvailabilityThurs", "this is availability: " + availability.getThursday().toString());
        Log.i("EmployeeAvailabilityFri", "this is availability: " + availability.getFriday().toString());
        Log.i("EmployeeAvailabilitySat", "this is availability: " + availability.getSaturday().toString());
        Log.i("EmployeeAvailabilitySun", "this is availability: " + availability.getSunday().toString());
        Log.i("EmployeeAvailabilityHoliday", "this is availability: " + availability.getHoliday().toString());

        // saving changes to file
        SharedPreferences.Editor editor = employeeAvailabilityStorage.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.availability);
        editor.putString(this.email, json);
        editor.commit();
        Toast.makeText(changeAvailability.this, "Changes Saved to File", Toast.LENGTH_LONG).show();
    }
    // This is the override function which allows us to control what happens when the bottom screen buttons are clicked
    @Override
    public void onRadioButtonClicked(Available available, String day) {
        //Toast.makeText(changeAvailability.this, available.toString(), Toast.LENGTH_LONG).show();
        // Switch statement to go to each one depending on day.
        // Erwin this is also where you can call button color change
        switch(day)
        {
            case "monday":
                Log.i("monday", "monday was entered");
                availability.setMonday(available);
                buttonColorChange(monday, available);
                break;
            case "tuesday":
                Log.i("tuesday", "tuesday was entered");
                availability.setTuesday(available);
                buttonColorChange(tuesday, available);
                break;
            case "wednesday":
                availability.setWednesday(available);
                buttonColorChange(wednesday, available);
                break;
            case "thursday":
                availability.setThursday(available);
                buttonColorChange(thursday, available);
                break;
            case "friday":
                availability.setFriday(available);
                buttonColorChange(friday, available);
                break;
            case "saturday":
                availability.setSaturday(available);
                buttonColorChange(saturday, available);
                break;
            case "sunday":
                availability.setSunday(available);
                buttonColorChange(sunday, available);
                break;
            default:
                // this is technically case for holiday
                Log.i("day", "this button was pressed: " + day);
                availability.setHoliday(available);
                buttonColorChange(holiday, available);
        }
    }
}