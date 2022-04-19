package com.example.trackgymprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class EditMuscleActivity extends AppCompatActivity {
    private FirebaseDatabase FBDatabase;
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
        muscleDatabase = FBDatabase.getReference(workoutDay.getDay()).child(getString(R.string.WORKOUT_MUSCLE_KEY));
    }

    public void updateDataToFB(String data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("muscleType", data);
        muscleDatabase.child(muscleType.getUuid()).updateChildren(map);
    }

    public void removeFromDB() {
        muscleDatabase.child(muscleType.getUuid()).removeValue();
    }
}