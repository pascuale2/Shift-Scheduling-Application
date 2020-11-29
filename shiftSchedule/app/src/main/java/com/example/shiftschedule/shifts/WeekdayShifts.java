package com.example.shiftschedule.shifts;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.EmployeeAvailability;

import java.util.Calendar;

public class WeekdayShifts extends Shift {
    /*
    WeekdayShifts Class created by Alex Creencia
    The Weekday version of the basic Shift Class
    DIFFERENCES OF THIS WeekdayShift Class
    - Maximum Number of Employees is only 2.
    - There can be a maximum of 2 shifts per day of this shift type (an Opening shift and a Closing Shift)
     */
    public int numOfEmployees = 2;
    protected String type = "Weekday";
    public WeekdayShifts(String dateOfShift, Available timeOfShift,String dayOfWeek, Calendar calendarDate) {
        super(dateOfShift, timeOfShift, dayOfWeek, calendarDate);
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
            Toast.makeText(context, "WORKING ERROR: Employee already working this shift", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (this.employeeList.size() >= numOfEmployees) {
            Toast.makeText(context, "MAXIMUM EMPLOYEE ERROR: cannot add employee: Maximum Number of employees already reached", Toast.LENGTH_SHORT).show();
        }
        else if (!checkIfAvailabilitySet(employee, context)) {
            Toast.makeText(context, "NO AVAILABILITY ERROR: This employees availability is not set. Please set this employees availability", Toast.LENGTH_SHORT).show();
        }
        else {
            EmployeeAvailability availability = getAvailability(employee, context);
            if (checkAvailability(availability)) {
                this.employeeList.add(employee);
                Toast.makeText(context, "Need " + (numOfEmployees - this.employeeList.size()) + " more employees to fill shift", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "AVAILABILITY ERROR: This employee cannot work on " + this.day + "at this specific time", Toast.LENGTH_SHORT).show();
            }
        }
    }


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
        int shiftOkay = 0; //counter variable which increments if someone is trained to work the time of the shift.
        for (Employee employee : this.employeeList) {
            // get the employees availability from the file
            //EmployeeAvailability availability = getAvailability(employee);
            switch(this.time)
            {
                case OPENING:
                    if (employee.isTrainedOpening())
                        shiftOkay++;
                    break;
                default:
                    if (employee.isTrainedClosing())
                        shiftOkay++;
            }
        }
        if (shiftOkay >= 1)
            return true;
        else
            return false;
    }

}
