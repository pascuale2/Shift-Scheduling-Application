package com.example.shiftschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.content.Intent;


public class view_schedule extends AppCompatActivity {

    public Button button;
    CalendarView calendar_view;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        button = (Button) findViewById(R.id.select_date_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_schedule.this, time_block.class);
                startActivity(intent);
            }
        });

        calendar_view = (CalendarView) findViewById(R.id.calendar_view);
        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
            }
        });
    }

    public void goBack1 (View view){
        finish();
    }

}