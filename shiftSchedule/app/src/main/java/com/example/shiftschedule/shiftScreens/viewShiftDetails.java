package com.example.shiftschedule.shiftScreens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiftschedule.R;
import com.example.shiftschedule.adapters.employeeAdapter;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.listItem;
import com.example.shiftschedule.shifts.Shift;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.example.shiftschedule.shifts.WeekendShifts;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class viewShiftDetails extends AppCompatActivity {

    /*
    viewShiftDetails java and xml file created by Alex Creencia
    Code created by Alex Creencia
    MEMBER VARIABLES
    shift_id                                                                    The id of the selected shift. This is also used to grab the shift from the sharedPreferences file.

     */


    protected String shift_id;
    protected Shift viewShift;
    private SharedPreferences shiftStorage;
    private SharedPreferences employeeAvailabilityStorage;
    private SharedPreferences employeeStorage;
    private int shiftType;
    protected Button confirmChanges;
    // Member variables that point to elements in xml file

    protected TextView dateText;
    protected TextView dayOfWeekText;
    protected TextView timeOfShift;
    private RecyclerView employeeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<listItem> items;
    protected List<Employee> currentShiftEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shift_details);
        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        this.employeeStorage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        this.employeeAvailabilityStorage = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);
        this.confirmChanges = (Button) findViewById(R.id.VS_confirmChanges);
        this.dateText = (TextView) findViewById(R.id.VS_Date);
        this.dayOfWeekText = (TextView) findViewById(R.id.VS_dayText);
        this.timeOfShift = (TextView) findViewById(R.id.VS_TimeShift);

        employeeList = (RecyclerView) findViewById(R.id.VS_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        employeeList.setLayoutManager(layoutManager);
        employeeList.setHasFixedSize(true);
        items = new ArrayList<listItem>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.shift_id = bundle.getString("shift_id");
            this.dateText.setText(bundle.getString("date"));
            this.dayOfWeekText.setText(bundle.getString("dayOfWeek"));
            // grabbing shift information from saved file
            String json = shiftStorage.getString(shift_id, "");

            // determining which shiftType it is
            int shiftFlag = checkShiftType(json);
            this.shiftType = shiftFlag;
            Gson gson = new Gson();
            if (shiftFlag == 1) {
                // Weekend shift
                this.viewShift = gson.fromJson(json, WeekendShifts.class);
            }
            else if (shiftFlag == 2) {
                // Weekday shift
                this.viewShift = gson.fromJson(json, WeekdayShifts.class);
                // some methods are locked out of the abstract shift class, thus need to create a duplicate of child Data type.
                WeekdayShifts detailed = gson.fromJson(json, WeekdayShifts.class);

            }
            else {
                // Holiday shift
            }
            this.timeOfShift.setText(this.viewShift.getTime().toString());
            TextView displayAsHolidayShift = (TextView) findViewById(R.id.VSD_title);
            if (this.viewShift.getSpecial())
                displayAsHolidayShift.setText("Shift Details: Holiday");
        }
        fillList(employeeList);
        if (this.viewShift.getEmployeeList().isEmpty()) {
            Toast.makeText(viewShiftDetails.this, "WARNING: No employees working this shift", Toast.LENGTH_SHORT).show();
        }
        else if (this.shiftType == 2 && (this.viewShift.getEmployeeList().size() < 2) ) {
            Toast.makeText(viewShiftDetails.this, "WARNING: Not enough employees working this weekday shift", Toast.LENGTH_SHORT).show();
        }
        else if ( (this.shiftType == 1 || this.shiftType == 0) && (this.viewShift.getEmployeeList().size() < 3)) {
            Toast.makeText(viewShiftDetails.this, "WARNING: Not enough employees working weekend/or holiday shift", Toast.LENGTH_SHORT).show();
        }

    }

    protected int checkShiftType(String json) {
        String[] types = {"Holiday", "Weekend", "Weekday"};
        int i;
        for (i = 0; i < types.length; i++) {
            if (json.contains(types[i])) break;
        }
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 2;
        }
    }
    public void OnAddEmployeeShiftClick(View view) {
        // Start Intent for adding employees.
        Intent intent = new Intent(viewShiftDetails.this, shiftAddEmployees.class);
        Bundle bundle = new Bundle();
        bundle.putString("shift_id", this.shift_id);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);

    }
    public void VSOnBackClick(View view) {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }
    public void VSOnDeleteShiftClick(View view) {
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        editor.remove(this.shift_id);
        editor.apply();
        finish();
        //TODO: Jaxon: I need you to update the calendar so that the event icon is removed from the day the shift used to be on.
    }
    public void VSOnHolidayClick(View view) {
        this.viewShift.setSpecial();
        // now that we set the shift to holiday/special, we need to save the changes to sharedPreferences file.
        SharedPreferences.Editor editor = this.shiftStorage.edit();
        Gson gson = new Gson();
        String shiftJson = gson.toJson(this.viewShift);
        editor.putString(this.viewShift.getShiftID(), shiftJson);
        Toast.makeText(viewShiftDetails.this, "Designating this shift as special/holiday shift", Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    public void fillList(RecyclerView employees) {
        this.currentShiftEmployees = this.viewShift.getEmployeeList();
        for (Employee employee : this.currentShiftEmployees) {
            StringBuilder description = new StringBuilder("Full name: ");
            description.append(employee.getFullName() + "\nTrained Opening: ");
            description.append(employee.isTrainedOpening() + "\nTrained Closing: ");
            description.append(employee.isTrainedClosing());
            listItem item = new listItem(employee.getEmail(), description.toString(), employee.isTrainedClosing(), employee.isTrainedOpening(), this.shift_id);
            items.add(item);
        }
        this.adapter = new employeeAdapter(this, items);
        employees.setAdapter(this.adapter);
    }

    public void VSOnConfirmChanges(View view) {
        //TODO: This is ideally where we would check whether the shift is good. We need to check if at least one employee is trained to work that shift (If allday, need 1 person to be trained opening, and another needs to be trained closing
        Intent resultIntent = new Intent();
        setResult(2, resultIntent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                fillList(employeeList);
                Toast.makeText(viewShiftDetails.this, "SUCCESS", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(viewShiftDetails.this, "Cancelled adding Employees to shift", Toast.LENGTH_SHORT).show();
            }
        }
    }
}