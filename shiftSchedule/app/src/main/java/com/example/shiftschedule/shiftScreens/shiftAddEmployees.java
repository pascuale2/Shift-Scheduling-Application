package com.example.shiftschedule.shiftScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.R;
import com.example.shiftschedule.adapters.employeeAdapter;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;
import com.example.shiftschedule.listItem;
import com.example.shiftschedule.shifts.Shift;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.example.shiftschedule.shifts.WeekendShifts;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class shiftAddEmployees extends AppCompatActivity {

    /*
    shiftAddEmployees Screen created by Alex Creencia
     */
    protected TextView numEmployees;
    protected TextView trainedEmployees;
    protected TextView timeOfShift;
    protected Button saveChanges;
    protected Shift shift;
    protected int shiftFlag;
    private SharedPreferences shiftStorage;
    private SharedPreferences employeeAvailabilityStorage;
    private SharedPreferences employeeStorage;

    private RecyclerView employeeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<listItem> items;

    protected String shift_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_add_employees);

        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        this.employeeStorage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        this.employeeAvailabilityStorage = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);

        numEmployees = (TextView) findViewById(R.id.SAE_numEmployees);
        trainedEmployees = (TextView) findViewById(R.id.SAE_employeeNum);
        timeOfShift = (TextView) findViewById(R.id.SAE_shiftType);
        employeeList = (RecyclerView) findViewById(R.id.SAE_recyclerView);
        this.saveChanges = (Button) findViewById(R.id.SAE_saveShiftChanges);
        layoutManager = new LinearLayoutManager(this);
        employeeList.setLayoutManager(layoutManager);
        employeeList.setHasFixedSize(true);
        items = new ArrayList<listItem>();

        Gson gson = new Gson();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.shift_id = bundle.getString("shift_id");
            // grab shift from shiftSharedPreferences
            SharedPreferences.Editor shiftEditor = shiftStorage.edit();
            String shiftJson = shiftStorage.getString(this.shift_id, "");
            this.shiftFlag = checkShiftType(shiftJson);
            if (this.shiftFlag == 2) {
                this.shift = gson.fromJson(shiftJson, WeekdayShifts.class);
            }
            else {
                this.shift = gson.fromJson(shiftJson, WeekendShifts.class);
            }
            // Toast.makeText(shiftAddEmployees.this, "This is the flag: " + this.shiftFlag, Toast.LENGTH_SHORT).show();
            replaceText();
        }
        fillList(employeeList);
    }

    protected void replaceText() {
        numEmployees.setText(String.valueOf(this.shift.getEmployeeList().size()));
        timeOfShift.setText(this.shift.getTime().toString());
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
    // this doesn't work because All day works for this.
    public boolean checkAvailability(EmployeeAvailability availability) {
        Log.i("day", "this is shift day: " + this.shift.getDay());
        switch (this.shift.getDay()) {
            case "Mon":
                if (availability.getMonday() == Available.ALLDAY)
                    return true;
                else
                    return (this.shift.getTime() == availability.getMonday());
            case "Tues":
                if (availability.getTuesday() == Available.ALLDAY)
                    return true;
                else
                    return (this.shift.getTime() == availability.getTuesday());
            case "Wed":
                if (availability.getWednesday() == Available.ALLDAY)
                    return true;
                else
                    return (this.shift.getTime() == availability.getWednesday());
            case "Thu":
                if (availability.getThursday() == Available.ALLDAY)
                    return true;
                else
                    return (this.shift.getTime() == availability.getThursday());
            case "Fri":
                if (availability.getFriday() == Available.ALLDAY)
                    return true;
                else
                    return (this.shift.getTime() == availability.getFriday());
            case "Sat":
                return (this.shift.getTime() == availability.getSaturday());
            case "Sun":
                return (this.shift.getTime() == availability.getSunday());
        }
        return false;
    }

    protected listItem prepareListItem(Employee employee) {

        StringBuilder description = new StringBuilder("Full name: ");
        description.append(employee.getFullName() + "\nTrained Opening: ");
        description.append(employee.isTrainedOpening() + "\nTrained Closing: ");
        description.append(employee.isTrainedClosing());
        listItem item = new listItem(employee.getEmail(), description.toString(), employee.isTrainedClosing(), employee.isTrainedOpening(), this.shift_id);
        return item;
    }

    public void fillList(RecyclerView employees) {
        Employee viewEmployee;
        SharedPreferences.Editor editor = employeeStorage.edit();
        Gson gson = new Gson();
        Map<String, ?> allEmployees = employeeStorage.getAll();
        for (Map.Entry<String, ?> entry: allEmployees.entrySet()) {
            String json = employeeStorage.getString(entry.getKey(), "");
            viewEmployee = gson.fromJson(json, Employee.class);
            // Now need to grab availability from email
            String availabilityJson = employeeAvailabilityStorage.getString(viewEmployee.getEmail(), "");
            EmployeeAvailability available = gson.fromJson(availabilityJson, EmployeeAvailability.class);
            if (available == null) {
                //Toast.makeText(getApplicationContext(), "Failed to get availability for: " + viewEmployee.getEmail(), Toast.LENGTH_SHORT).show();
                continue;
            }
            // if Employee is available for this day, add them to the list.
            Log.i("before Checks", "before checking if employee already works");
            if (!this.shift.checkIfEmployeeAlreadyWorks(viewEmployee)) {
                // if this employee doesn't already work this shift, display them as available
                Log.i("before availability check", "availability check");
                if (checkAvailability(available)) {
                    String shift_time_block = this.shift_id.split("-")[1];

                    // last check if they are currently working today already
                    Log.i("adding to list", "adding to list");
                    if (shift_time_block.matches("OPENING") && viewEmployee.isTrainedOpening()){
                        listItem item = prepareListItem(viewEmployee);
                        items.add(item);
                    }
                    else if (shift_time_block.matches("CLOSING") && viewEmployee.isTrainedClosing()){
                        listItem item = prepareListItem(viewEmployee);
                        items.add(item);
                    }
                }
            }
        }
        Log.i("after", "after iterating through employees");
        this.adapter = new employeeAdapter(this, items);
        employees.setAdapter(this.adapter);
    }
    public void SAEOnSaveChangesClick(View view) {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}