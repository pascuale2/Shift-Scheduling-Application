package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerpage extends AppCompatActivity {

    /*
    Register page created by Alex Creencia
    Last Edited: October 12th, 2020 by Alex Creencia
    register Page activity that holds all required information for creating an account

    MEMBERS

     */
    private EditText email;
    private EditText emailConfirm;
    private EditText password;
    private EditText passwordConfirm;
    private Button submitButton;
    private SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        // point members to respective design elements
        email = (EditText)findViewById(R.id.RPemailTextView);
        emailConfirm = (EditText)findViewById(R.id.RPconfirmEmail);
        password = (EditText)findViewById(R.id.RPpasswordInitial);
        passwordConfirm = (EditText)findViewById(R.id.RPpasswordConfirm);
        submitButton = (Button)findViewById(R.id.RPsubmitButton);

        // grab preferences file to hold information
        storage = getApplicationContext().getSharedPreferences("accountStorage", Context.MODE_PRIVATE);

        // the following 3 lines is to remove any test register's that you guys have done. (Since it will stay in memory forever)
        // uncomment these 3 lines and make sure before you exit app you go to register page to wipe 
        //SharedPreferences.Editor editor = storage.edit();
        //editor.clear();
        //editor.commit();
    }


    protected boolean confirmPassword(String initialPasswordText, String confirmPasswordText) {
        return (initialPasswordText.equals(confirmPasswordText));
    }
    // if email exists within the storage already return true
    protected boolean emailExist(String email) {
        return storage.contains(email);
    }
    // checks if email in textField is an ACTUAL email and not just some random string
    protected boolean isEmailValid(String emailCheck) {
        return Patterns.EMAIL_ADDRESS.matcher(emailCheck).matches();
    }

    // checks if email in both text fields match
    protected boolean confirmEmail(String emailText, String emailTextConfirm) {
        return emailText.equals(emailTextConfirm);
    }

    public void submitAction(View view){
        // checks if passwords match, if emails match, if email input is valid, AND lastly
        // if email doesn't already exist

        if (confirmPassword(password.getText().toString(), passwordConfirm.getText().toString()) &&
                confirmEmail(email.getText().toString(), emailConfirm.getText().toString()) &&
        isEmailValid(emailConfirm.getText().toString()) ) {
            // if it passes both checks, store information

            // need to create an editor in order to edit storage file
            if (emailExist(emailConfirm.getText().toString())) {
                String existMessage = "ERROR: Email already exists. Please try again";
                Toast.makeText(registerpage.this, existMessage, Toast.LENGTH_LONG).show();
                return;
            }
            SharedPreferences.Editor editor = storage.edit();
            //editor.clear();      // this line clears all the preferences within the sharedPreferences file it points to. LEAVE COMMENTED OUT
            // key --> value
            editor.putString(emailConfirm.getText().toString(), passwordConfirm.getText().toString());
            // after putting in email --> password, need to commit like git commit
            editor.commit();

            // showing success popup notification
            String success = "Register was successful";
            Toast toast = Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG);
            toast.show();

            // changing screens back to the login page
            Intent intent = new Intent(registerpage.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Log.d("notMatch", "before toast");
            String errorMessage;
            if (confirmPassword(password.getText().toString(), passwordConfirm.getText().toString()) != true) {
                errorMessage = "Passwords does not match. Please try again";
            }
            else if (confirmEmail(email.getText().toString(), emailConfirm.getText().toString()) != true){
                errorMessage = "Emails does not match. Please try again";
            }
            else {
                errorMessage = "ERROR: Invalid Email input. Please make sure email is a valid email";
            }
            Toast.makeText(registerpage.this, errorMessage, Toast.LENGTH_LONG).show();
            Log.d("notMatch1", "afterToast");

        }
    }


}