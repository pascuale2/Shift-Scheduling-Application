package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private int counter = 3;
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

        // If the sharedPreferences file already exists, grab it, otherwise this will create it
        // make SURE you put it in Context.MODE_PRIVATE, as this means ONLY this app can access this information
        storage = getApplicationContext().getSharedPreferences("accountStorage", Context.MODE_PRIVATE);

    }

    public void loginAccount(View view) {
        validate(username.getText().toString(), password.getText().toString());
    }

    public void validate(String userName, String userPassword){
        userName = userName.toLowerCase();
        if ((userName.equals(("admin")) && (userPassword.equals("1234")))) {
            // we are hardcoding the login here, either myself or someone else will need to find a
            // way to store them

            //test to store information here, this code will be commented out once we have a register account page functional

            // In order to store our information, we need to create an editor to edit shared preferences
            SharedPreferences.Editor editor = storage.edit();

            // Ideally this would be done in register client page, and we simply retrieve it in login page
            // key: username --> value: password. To access the password, need correct username
            editor.putString( username.getText().toString(), password.getText().toString());

            // now the last thing you need to do is "commit" the changes to the file, like git commit
            editor.commit();


            // intent is what allows us to switch screens
            Intent intent = new Intent(MainActivity.this, landingPage.class);

            // in order to pass just a single value (such as the username to access password value), we can do this method:
            intent.putExtra("username", username.getText().toString());

            startActivity(intent);


        }else{
            // this will decrement every time they fail to login.
            counter--;
            Info.setText("Login attempts remaining: " + String.valueOf(counter));
            if(counter == 0){
                loginButton.setEnabled(false);
            }
        }
    }
}