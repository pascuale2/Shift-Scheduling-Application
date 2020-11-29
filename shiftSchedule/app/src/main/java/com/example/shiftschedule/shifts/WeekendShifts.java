package com.example.shiftschedule.shifts;

import android.content.Context;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;

import java.util.Calendar;

public class WeekendShifts extends Shift {
    protected int numOfEmployees = 2;
    protected String type = "Weekend";

    public WeekendShifts(String dateOfShift, Available timeOfShift, String dayOfWeek, Calendar calendarDate) {
        super(dateOfShift, timeOfShift, dayOfWeek, calendarDate);
    }

    @Override
    public void addEmployee(Employee employee, Context context) {

    }

    @Override
    public boolean checkAvailability(EmployeeAvailability availability) {
        return false;
    }

    @Override
    public boolean checkIfShiftCovered() {
        return false;
    }
}
