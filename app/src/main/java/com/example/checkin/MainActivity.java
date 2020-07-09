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

    public CheckBox checkBox;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        checkBox = findViewById(R.id.checkBox);
        loginEditText = findViewById(R.id.edit_user);
        passwordEditText = findViewById(R.id.edit_password);
        findViewById(R.id.button_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    register(getFilesDir());
                } else {
                    register(getExternalFilesDir(null));
                }
            }
        });

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkLogin(getFilesDir());
                } else {
                    checkLogin(getExternalFilesDir(null));
                }
            }
        });
    }

    private void checkLogin(File root) {
        final String nLogin = loginEditText.getText().toString();
        final String nPassword = passwordEditText.getText().toString();
        if (nLogin.isEmpty() || nPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Incorrect types", LENGTH_SHORT).show();
            return;
        }
        File file = new File(root, "lol.txt");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "Login and password is correct", LENGTH_SHORT).show();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] split = reader.readLine().split(":");
            for (int i = 0; i < split.length; i++) {
                String log = split[i];
                String pass = split[++i];
                if (nLogin.equals(log) && nPassword.equals(pass)) {
                    Toast.makeText(MainActivity.this, "Login and password is correct", LENGTH_SHORT).show();
                    return;
                } else if (i == split.length - 1) {
                    Toast.makeText(MainActivity.this, "Incorrect types", LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void register(File root) {
        final String nLogin = loginEditText.getText().toString();
        final String nPassword = passwordEditText.getText().toString();
        if (nLogin.isEmpty() || nPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Enter login and password", LENGTH_SHORT).show();
            return;
        }
        File file = new File(root, "lol.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            saveItemExternal(writer, nLogin, nPassword);
            Toast.makeText(MainActivity.this, "Correct", LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveItemExternal(FileWriter writer, String nLogin, String nPassword) throws IOException {
        writer.append(nLogin);
        writer.append(":");
        writer.append(nPassword);
        writer.append(":");
    }
}

