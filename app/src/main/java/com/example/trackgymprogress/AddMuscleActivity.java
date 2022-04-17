package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddMuscleActivity extends AppCompatActivity {
    private FirebaseDatabase FBDatabase;
    private DatabaseReference dayDatabase;
    private DatabaseReference muscleDatabase;
    private WorkoutDayClass workoutDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_muscle);
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        initFB();
        EditText muscleInput = findViewById(R.id.muscleInput);
        Button addBtn =  findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            String input = muscleInput.getText().toString();
            if (!input.equals("")) {
                addDataToFB(input);
                finish();
            } else {
                Toast.makeText(this, "Nothing Inputted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        dayDatabase = FBDatabase.getReference(workoutDay.getDay());
    }

    public void addDataToFB(String data) {
        UUID uuid = UUID.randomUUID();
        muscleDatabase = dayDatabase.child(getString(R.string.WORKOUT_MUSCLE_KEY));
        MuscleClass muscle = new MuscleClass(data, String.valueOf(uuid));
        muscleDatabase.child(String.valueOf(uuid)).setValue(muscle);
    }
}