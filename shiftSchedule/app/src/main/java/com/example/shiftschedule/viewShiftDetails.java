package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiftschedule.shifts.Shift;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.example.shiftschedule.shifts.WeekendShifts;
import com.google.gson.Gson;

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

    // Member variables that point to elements in xml file

    protected TextView dateText;
    protected TextView dayOfWeekText;
    private RecyclerView employeeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<listItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shift_details);
        this.shiftStorage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        this.employeeStorage = getApplicationContext().getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
        this.employeeAvailabilityStorage = getApplicationContext().getSharedPreferences("availability", Context.MODE_PRIVATE);
        this.dateText = (TextView) findViewById(R.id.VS_Date);
        this.dayOfWeekText = (TextView) findViewById(R.id.VS_dayText);
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
        }
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
}