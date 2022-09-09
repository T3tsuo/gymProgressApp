package com.example.trackgymprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private WorkoutDayClass workoutDay;
    private FirebaseDatabase FBDatabase;
    private DatabaseReference allDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        workoutDay = new WorkoutDayClass();
        Button pushBtn = findViewById(R.id.pushBtn);
        Button pullBtn = findViewById(R.id.pullBtn);
        Button legsBtn = findViewById(R.id.legsBtn);
        Button backupBtn = findViewById(R.id.backupBtn);
        Button restoreBtn = findViewById(R.id.restoreBtn);

        pushBtn.setOnClickListener(view -> {
            workoutDay.setPush();
            switchActivity();
        });
        pullBtn.setOnClickListener(view -> {
            workoutDay.setPull();
            switchActivity();
        });
        legsBtn.setOnClickListener(view -> {
            workoutDay.setLegs();
            switchActivity();
        });
        backupBtn.setOnClickListener(view -> getAllWorkouts());
        restoreBtn.setOnClickListener(view -> restoreAllWorkouts());
    }

    public void initDB() {
        FBDatabase = FirebaseDatabase.getInstance();
        FBDatabase.setPersistenceEnabled(true);
        allDB = FBDatabase.getReference();
        allDB.keepSynced(true);
    }

    public void restoreAllWorkouts() {
        String jsonString = readFromFile("workout_data_backup.txt");
        if (!jsonString.equals("")) {
            Map<String, Object> jsonMap = new Gson().fromJson(jsonString,
                    new TypeToken<HashMap<String, Object>>() {}.getType());
            allDB.updateChildren(jsonMap);
        }
    }

    public String readFromFile(String fileName) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];
        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            stream.close();
            Toast.makeText(this, "Restored", Toast.LENGTH_SHORT).show();
            return new String(content);
        } catch (IOException e) {
            Toast.makeText(this, "Error: Could not find file", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "";
        }
    }

    public void getAllWorkouts() {
        allDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object object = snapshot.getValue(Object.class);
                String json = new Gson().toJson(object);
                writeToFile("workout_data_backup.txt", json);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void writeToFile(String fileName, String content) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes());
            writer.close();
            Toast.makeText(this, "Saved to Downloads Folder", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Allow all files management in Permissions", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void switchActivity() {
        Intent intent = new Intent(MainActivity.this, WorkoutMuscleActivity.class);
        intent.putExtra(getString(R.string.WORKOUT_DAY_KEY), workoutDay);
        startActivity(intent);
    }
}