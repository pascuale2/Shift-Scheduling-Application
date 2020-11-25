package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.shiftschedule.bottomLayouts.availabilityBottomSheetDialog;

public class changeAvailability extends AppCompatActivity implements availabilityBottomSheetDialog.BottomSheetListener {

    /*
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
    private Button monday;
    private Button tuesday;
    private Button wednesday;
    private Button thursday;
    private Button friday;
    private Button saturday;
    private Button sunday;
    private Button holiday;
    private Bundle extras;
    private SharedPreferences employeeAvailability;
    private String email;

    // TODO: Implement Availability changes for Tuesday - Friday, and save it to employee information
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_availability);
        employeeAvailability = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);
        extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
        }

        // Point buttons to button objects in activity
        monday = (Button)findViewById(R.id.CA_mondayButton);
        tuesday = (Button)findViewById(R.id.CA_tuesdayButton);
        wednesday = (Button)findViewById(R.id.CA_wednesdayButton);
        thursday = (Button)findViewById(R.id.CA_thursdayButton);
        friday = (Button)findViewById(R.id.CA_fridayButton);
        saturday = (Button)findViewById(R.id.CA_saturdayButton);
        sunday = (Button)findViewById(R.id.CA_SundayButton);
        holiday = (Button)findViewById(R.id.CA_holidayButton);


    }
    public void changeMondayAvailability(View view) {
        Bundle args = new Bundle();
        args.putString("day", "monday");
        availabilityBottomSheetDialog bottomSheet = new availabilityBottomSheetDialog();
        bottomSheet.setArguments(args);
        bottomSheet.show(getSupportFragmentManager(), "availabilityMonday");
        /*
        if (monday.isChecked()) {
            Toast.makeText(changeAvailability.this, "can now open", Toast.LENGTH_LONG).show();
        }
         */

    }

    public void changeTuesdayAvailability(View view) {
        availabilityBottomSheetDialog bottomSheet = new availabilityBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "availabilityTuesday");
    }
    @Override
    public void onRadioButtonClicked(String text) {
        Toast.makeText(changeAvailability.this, text, Toast.LENGTH_LONG).show();
    }
}