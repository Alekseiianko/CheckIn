package com.example.checkin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    public final static String loginFileName = "login";
    public final static String passwordFileName = "password";
    public CheckBox checkBox;
    public File logFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        checkBox = findViewById(R.id.checkBox);
        logFile = new File(getApplicationContext().getExternalFilesDir(null), "lol.txt");
        final EditText mLoginEdTxt = findViewById(R.id.edit_user);
        final EditText mPasswordEdTxt = findViewById(R.id.edit_password);
        Button mLogin = findViewById(R.id.button_login);
        Button mRegistration = findViewById(R.id.button_sign_up);
        if(checkBox.isChecked()){
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter login and password", LENGTH_SHORT).show();
                    return;
                }
                boolean isLoginWritten = writeToFile(nLogin, loginFileName);
                boolean isPasswordWritten = writeToFile(nPassword, passwordFileName);
                if (isLoginWritten && isPasswordWritten) {
                    Toast.makeText(MainActivity.this, "Correct", LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", LENGTH_SHORT).show();
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nLogin = mLoginEdTxt.getText().toString();
                final String nPassword = mPasswordEdTxt.getText().toString();
                if (nLogin.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter login and password", LENGTH_SHORT).show();
                    return;
                }
                String savedLogin = readFromFile(loginFileName);
                String savedPassword = readFromFile(passwordFileName);
                if (nLogin.equals(savedLogin) && nPassword.equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Login and password is correct", LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect types", LENGTH_SHORT).show();
                }
            }
        });
        } else {
            mRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nLogin = mLoginEdTxt.getText().toString();
                    final String nPassword = mPasswordEdTxt.getText().toString();
                    if (nLogin.isEmpty() || nPassword.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Enter login and password", LENGTH_SHORT).show();
                        return;
                    }
                    try (FileWriter writer = new FileWriter(logFile, true)) {
                        saveItemExternal(writer, nLogin, nPassword);
                        Toast.makeText(MainActivity.this, "Correct", LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nLogin = mLoginEdTxt.getText().toString();
                    final String nPassword = mPasswordEdTxt.getText().toString();
                    if (nLogin.isEmpty() || nPassword.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Enter login and password", LENGTH_SHORT).show();
                        return;
                    }
                    try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                        String[] split = reader.readLine().split(":");
                        for (int i = 0 ; i < split.length; i ++) {
                            String log = split[i];
                            String pass = split[i + 1];
                            if (nLogin.equals(log) && nPassword.equals(pass)) {
                                Toast.makeText(MainActivity.this, "Login and password is correct", LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Incorrect types", LENGTH_SHORT).show();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void saveItemExternal(FileWriter writer, String nLogin, String nPassword) {
        try {
            writer.append(nLogin);
            writer.append(":");
            writer.append(nPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
