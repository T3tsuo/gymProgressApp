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

public class EditMuscleActivity extends AppCompatActivity {
    private FirebaseDatabase FBDatabase;
    private DatabaseReference dayDatabase;
    private DatabaseReference muscleDatabase;
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_muscle);
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        initFB();
        EditText muscleInput = findViewById(R.id.muscleInput);
        muscleInput.setText(muscleType.getMuscleType());
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button setBtn =  findViewById(R.id.setBtn);

        setBtn.setOnClickListener(view -> {
            String input = muscleInput.getText().toString();
            if (!input.equals("")) {
                updateDataToFB(input);
                finish();
            } else {
                Toast.makeText(this, "Nothing Inputted", Toast.LENGTH_SHORT).show();
            }
        });
        deleteBtn.setOnClickListener(view -> {
            removeFromDB();
            finish();
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        dayDatabase = FBDatabase.getReference(workoutDay.getDay());
    }

    public void updateDataToFB(String data) {
        muscleDatabase = dayDatabase.child(getString(R.string.WORKOUT_MUSCLE_KEY));
        MuscleClass muscle = new MuscleClass(data, muscleType.getUuid());
        muscleDatabase.child(muscleType.getUuid()).setValue(muscle);
    }

    public void removeFromDB() {
        muscleDatabase = dayDatabase.child(getString(R.string.WORKOUT_MUSCLE_KEY));
        muscleDatabase.child(muscleType.getUuid()).removeValue();
    }
}