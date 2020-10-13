package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    Simple login landing page created by Alex Creencia

    Members:
    logOutStatus - used for easy/clean logging out of the app
    username    - The editText box that will contain the username input
    password    - the editText box that will contain the password input
    loginButton - the button clicked to login
    info        - displays remaining number of login attempts
    counter     - number of tries user gets to login before being locked out
    storage     - the storage file used to hold account information.
                  SharedPreference files store data by using (key,value) pairs.
                  You CAN have multiple SharedPreference files used (one for account storage, one
                  for holding employee information etc)
     */

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView Info;
    private int counter;
    private SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // need to initialize/point our members to our actual boxes in the layout
        username = (EditText)findViewById(R.id.usernameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        Info = (TextView)findViewById(R.id.attemptsText);
        counter = 3;
        // If the sharedPreferences file already exists, grab it, otherwise this will create it
        // make SURE you put it in Context.MODE_PRIVATE, as this means ONLY this app can access this information
        storage = getApplicationContext().getSharedPreferences("accountStorage", Context.MODE_PRIVATE);

    }

    // onClick function for loginAccount to take you to the landing Page/Activity
    public void loginAccount(View view) {
        // grab key from our preferences
        validate(username.getText().toString(), password.getText().toString());
    }

    // onClick function for registerButton to take you to register Page/Activity
    public void goToRegister(View view) {
        Intent intent = new Intent(MainActivity.this, registerpage.class);
        startActivity(intent);
        finish();
    }

    // this function sets limits on how many times you can attempt to log in.
    public void numAttempts() {
        counter--;
        Info.setText("Login attempts remaining: " + String.valueOf(counter));
        if(counter == 0){
            loginButton.setEnabled(false);
        }
    }
    public void validate(String userName, String userPassword){
        //userName = userName.toLowerCase();
        if (storage.contains(userName)) {
            String passwordCheck = storage.getString(userName, "");

            // one more check to see if they got the password right.
            if (userPassword.equals(passwordCheck)) {
                Intent intent = new Intent(MainActivity.this, landingPage.class);

                // this is how we pass a single variable WITHOUT using sharedPreferences to next screen
                // You can comment out the following line (intent.putExtra....) but NOT startActivity
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
                finish();
            }
            else {
                //this is if the username is correct, but password is wrong
                Log.d("Contains", "found username within storage. userPassword does not match:\n" + userPassword + " != " + passwordCheck);
                String error = "ERROR: Incorrect password. Please try again.";
                Toast toast = Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG);
                toast.show();
                numAttempts();
            }
        }
        else{

            // this will decrement every time they fail to login`
            String test = "ERROR: email is wrong. Please try again";
            Log.d("storage", test);
            //String test = "did storage match? " + storage.contains(userName) + "username: " + userName + "\nPassword: " + storage.getString(userName,"");
            Toast toast = Toast.makeText(MainActivity.this, test, Toast.LENGTH_LONG);
            toast.show();
            numAttempts();

        }
    }
}