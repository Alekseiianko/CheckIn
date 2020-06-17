package com.example.checkin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    public final static String loginFileName = "login";
    public final static String passwordFileName = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        final EditText mLoginEdTxt = findViewById(R.id.edit_user);
        final EditText mPasswordEdTxt = findViewById(R.id.edit_password);
        Button mLogin = findViewById(R.id.button_login);
        Button mRegistration = findViewById(R.id.button_sign_up);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isLoginWritten = writeToFile(nLogin, loginFileName);
                boolean isPasswordWritten = writeToFile(nPassword, passwordFileName);
                if (isLoginWritten && isPasswordWritten) {
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                String savedLogin = readFromFile(loginFileName);
                String savedPassword = readFromFile(passwordFileName);
                if (nLogin.equals(savedLogin) && nPassword.equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Login and password is correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect types", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean writeToFile(String str, String fileName) {
        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String readFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);
        ) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
