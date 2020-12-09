package com.example.shiftschedule.shiftScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shiftschedule.R;
import com.example.shiftschedule.adapters.shiftAdapter;
import com.example.shiftschedule.listItem;

import java.util.ArrayList;
import java.util.List;

public class listShiftOnDay extends AppCompatActivity {

    /*
    listShiftOnDay Class created By Alex Creencia
    Intermediary activity screen with code to display all shifts running on current day.
    This utilizes shiftAdapter which is a modified version of my employeeAdapter to display shifts instead of employees
     */
    protected RecyclerView shiftList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<listItem> items;
    protected TextView dateLabel;
    private SharedPreferences storage;
    protected String dateOfShift;
    protected String dayOfWeek;
    protected List<String> shift_ids = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shift_on_day);
        storage = getApplicationContext().getSharedPreferences("shifts", Context.MODE_PRIVATE);
        shiftList = (RecyclerView) findViewById(R.id.listShiftRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        shiftList.setLayoutManager(layoutManager);
        shiftList.setHasFixedSize(true);
        dateLabel = (TextView) findViewById(R.id.listShiftTitle);
        items = new ArrayList<listItem>();
        // get passed bundle.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.dateOfShift = bundle.getString("date");
            this.dayOfWeek = bundle.getString("dayOfWeek");
            dateLabel.setText("LISTING SHIFTS FOR\n" + this.dateOfShift);
            String openingID = bundle.getString("opening");
            String closingID = bundle.getString("closing");
            shift_ids.add(openingID);
            shift_ids.add(closingID);
        }
        fillList(shiftList);
    }
    public void fillList(RecyclerView shifts){
        for (String elem: shift_ids) {
            listItem item = new listItem(elem, this.dayOfWeek, true, true, "");
            items.add(item);
        }
        this.adapter = new shiftAdapter(this, items);
        shifts.setAdapter(adapter);

    }

}