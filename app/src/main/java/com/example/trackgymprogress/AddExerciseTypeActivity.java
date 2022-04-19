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

public class AddExerciseTypeActivity extends AppCompatActivity {

    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private FirebaseDatabase FBDatabase;
    private DatabaseReference exerciseTypeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_type);

        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass) intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));

        initFB();
        EditText exerciseTypeInput = findViewById(R.id.exerciseTypeInput);
        Button addBtn =  findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            String input = exerciseTypeInput.getText().toString();
            if (!input.equals("")) {
                addDataToFB(input);
                finish();
            } else {
                Toast.makeText(this, "Nothing Inputted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseTypeDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY));
    }

    public void addDataToFB(String data) {
        UUID uuid = UUID.randomUUID();
        ExerciseTypeClass exerciseType = new ExerciseTypeClass(data, String.valueOf(uuid));
        exerciseTypeDatabase.child(String.valueOf(uuid)).setValue(exerciseType);
    }
}