package com.example.shiftschedule.shifts;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;

import java.util.Calendar;

public class WeekendShifts extends Shift {
    //protected String type = "Weekend";

    public WeekendShifts(String dateOfShift, Available timeOfShift, String dayOfWeek, Calendar calendarDate) {
        super(dateOfShift, timeOfShift, dayOfWeek, calendarDate);
        this.type = "Weekend";
    }

    public boolean checkIfAvailabilitySet(Employee employee, Context context) {
        SharedPreferences storage = context.getSharedPreferences("availability", Context.MODE_PRIVATE);
        if (storage.contains(employee.getEmail())) {
            return true;
        }
        return false;
    }

    @Override
    public void addEmployee(Employee employee, Context context) {
        if (this.employeeList.contains(employee)) {
            Toast.makeText(context, "WORKING ERROR: Employee already working this shift. Employee cannot be added.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (this.employeeList.size() >= numOfEmployees) {
            Toast.makeText(context, "MAXIMUM EMPLOYEE ERROR: cannot add employee: Maximum Number of employees already reached", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!checkIfAvailabilitySet(employee, context)) {
            Toast.makeText(context, "NO AVAILABILITY ERROR: This employees availability is not set. Please set this employees availability", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            this.employeeList.add(employee);
            Toast.makeText(context, "Need " + (numOfEmployees - this.employeeList.size()) + " more employees to fill shift", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean checkAvailability(EmployeeAvailability availability) {
        switch(day) {
            case "monday":
                return (this.time == availability.getMonday());
            case "tuesday":
                return (this.time == availability.getTuesday());
            case "wednesday":
                return (this.time == availability.getWednesday());
            case "thursday":
                return (this.time == availability.getThursday());
            case "friday":
                return (this.time == availability.getFriday());
        }
        return false;
    }

    @Override
    public boolean checkIfShiftCovered() {
        return false;
    }


}
