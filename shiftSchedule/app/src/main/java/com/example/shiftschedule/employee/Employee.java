package com.example.shiftschedule.employee;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shiftschedule.Available.Available;
import com.google.gson.Gson;

import java.util.Objects;

/*
  Employee class created by Alex Creencia
  October 16, 2020
 */
public class Employee {
    /*
     CLASS MEMBERS, data-type               DESCRIPTION
     email, string                      - The email of the Employee
     Full Name, string                  - The Full name of the Employee
     Age, age                           - The Age of the Employee
     Sex, enum                          - The Sex of the Employee
     dateEmployed, String            - The Year-Month-Date the Employee was employed
     trainedOpening, bool               - Flag whether the employee is trained for opening
     trainedClosing, bool               - Flag whether the employee is trained for closing
     */
    protected String email;
    protected String fullName;
    protected int Age;
    protected String Sex;
    protected String dateEmployed;
    protected boolean trainedOpening;
    protected boolean trainedClosing;
    // Create a new screen


    // Initializes an Employee object
    public Employee(String email, String fullName, int Age, String employedDate, boolean trainedClosing, boolean trainedOpening) {
        this.email = email;
        this.fullName = fullName;
        this.Age = Age;
        this.dateEmployed = employedDate;
        this.trainedClosing = trainedClosing;
        this.trainedOpening = trainedOpening;
    }

    // GETTER FUNCTIONS
    public int getAge() { return Age; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getSex() { return Sex; }
    public String getDateEmployed() { return dateEmployed; }
    public String getDateString() { return dateEmployed.toString(); }
    public boolean isTrainedClosing() {
        if (this.trainedClosing == false) {
            return false;
        }
        return true;
    }
    public boolean isTrainedOpening() {
        if (this.trainedOpening == false) {
            return false;
        }
        return true;
    }
    // SETTER FUNCTIONS

    public void setAge(int age) { Age = age; }

    public void setDateEmployed(String dateEmployed) { this.dateEmployed = dateEmployed; }

    public void setEmail(String email) { this.email = email; }

    public void setSex(String sex) { Sex = sex; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setTrainedClosing(boolean trainedClosing) { this.trainedClosing = trainedClosing; }

    public void setTrainedOpening(boolean trainedOpening) { this.trainedOpening = trainedOpening; }


    public EmployeeAvailability getEmployeeAvailability(Context context) {
        SharedPreferences availabilityStorage = context.getSharedPreferences("availability", Context.MODE_PRIVATE);
        String availabilityString = availabilityStorage.getString(getEmail(), "");
        Gson gson = new Gson();
        EmployeeAvailability availability = gson.fromJson(availabilityString, EmployeeAvailability.class);
        return availability;
    }

    public Available getAvailabilityMonday(Context context) {
        EmployeeAvailability availability = getEmployeeAvailability(context);
        return availability.getMonday();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return email.equals(employee.email) &&
                Objects.equals(dateEmployed, employee.dateEmployed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, dateEmployed);
    }

    @Override
    public String toString() {
        String employeeString = "Email: " + email + "\n" +
                "full name: " + fullName + "\n" +
                "Age: " + Age + "\n" +
                "Sex: " + Sex + "\n" +
                "Date Employed: " + dateEmployed.toString() + "\n" +
                "Trained Closing: " + trainedClosing + "\n" +
                "Trained Opening: " + trainedOpening;
        return employeeString;
        /*
        return "Employee{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Age=" + Age +
                ", Sex='" + Sex + '\'' +
                ", dateEmployed=" + dateEmployed +
                ", trainedOpening=" + trainedOpening +
                ", trainedClosing=" + trainedClosing +
                '}';

         */
    }

}
