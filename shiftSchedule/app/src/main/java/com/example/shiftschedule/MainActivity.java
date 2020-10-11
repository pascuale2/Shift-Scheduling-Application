package com.example.shiftschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*
    Simple login landing page created by Alex Creencia

    Members:
    username    - The editText box that will contain the username input
    password    - the editText box that will contain the password input
    loginButton - the button clicked to login
    info        - displays remaining number of login attempts
    counter     - number of tries user gets to login before being locked out
     */
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView Info;
    private int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // need to initialize/point our members to our actual boxes in the layout
        username = (EditText)findViewById(R.id.usernameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        Info = (TextView)findViewById(R.id.attemptsText);
    }

    public void loginAccount(View view) {
        validate(username.getText().toString(), password.getText().toString());
    }

    public void validate(String userName, String userPassword){
        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            // we are hardcoding the login here, either myself or someone else will need to find a
            // way to store them
            Intent intent = new Intent(MainActivity.this, landingPage.class);
            startActivity(intent);
        }else{
            // this will decrement every time they fail to login.
            counter--;
            Info.setText("No of attempts remaining: " + String.valueOf(counter));
            if(counter == 0){
                loginButton.setEnabled(false);
            }
        }
    }
}