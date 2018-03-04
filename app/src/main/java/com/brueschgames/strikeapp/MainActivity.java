package com.brueschgames.strikeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int INITIAL_STRIKE_COUNT = 4;
    public static final String DATA_FILE = "strikeCount";
    private Integer strikes;
    private Button strikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strikeButton = findViewById(R.id.strikeButton);
        strikes = loadSavedDataAndApply();
        updateScreenAndSaveData();
    }


    public void decreaseStrikes(View view) {
        if (strikes > 0) {
            strikes--;
            updateScreenAndSaveData();
        }
    }

    private void updateScreenAndSaveData() {
        strikeButton.setText(strikes.toString());
        saveStrikes();
    }

    private int loadSavedDataAndApply() {
        try {
            return tryLoadSaveDataAndApply();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return INITIAL_STRIKE_COUNT;
    }

    private int tryLoadSaveDataAndApply() throws IOException {
        return applyData(loadSaveData());
    }

    private int applyData(StringBuilder temp) {
        return ((temp.length() == 0) ? INITIAL_STRIKE_COUNT : Integer.parseInt(temp.toString()));
    }

    @NonNull
    private StringBuilder loadSaveData() throws IOException {
        FileInputStream fin = openFileInput(DATA_FILE);
        StringBuilder temp = new StringBuilder();
        int c;

        while ((c = fin.read()) != -1) {
            temp.append(Character.toString((char) c));
        }
        fin.close();
        return temp;
    }

    private void saveStrikes() {
        try {
            trySaveStrikes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void trySaveStrikes() throws IOException {
        FileOutputStream fOut = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
        fOut.write(strikeButton.getText().toString().getBytes());
        fOut.close();
    }
}
