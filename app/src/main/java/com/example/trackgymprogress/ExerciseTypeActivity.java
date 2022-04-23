package com.example.trackgymprogress;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExerciseTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private WorkoutDayClass workoutDay;
    private MuscleClass muscleType;
    private FirebaseDatabase FBDatabase;
    Query exerciseTypeDatabase;
    private ExerciseTypeAdapter adapter;
    private ArrayList<ExerciseTypeClass> exerciseTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getExerciseTypes();
                    }
                });
        setContentView(R.layout.activity_exercise_type);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.exerciseTypeRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass) intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        muscleType = (MuscleClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_MUSCLE_KEY));
        TextView title = findViewById(R.id.textView);
        String titleText = muscleType.getMuscleType() + " Equipment";
        title.setText(titleText);
        initFB();
        getExerciseTypes();

        fabAdd.setOnClickListener(view -> {
            Intent i = new Intent(ExerciseTypeActivity.this, AddExerciseTypeActivity.class);
            i.putExtra(getString(R.string.WORKOUT_DAY_KEY), workoutDay);
            i.putExtra(getString(R.string.WORKOUT_MUSCLE_KEY), muscleType);
            activityResultLauncher.launch(i);
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        exerciseTypeDatabase = FBDatabase.getReference(workoutDay.getDay())
                .child(getString(R.string.WORKOUT_MUSCLE_KEY))
                .child(muscleType.getUuid()).child(getString(R.string.EXERCISE_TYPE_KEY)).orderByValue();
    }

    public void getExerciseTypes() {
        exerciseTypeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseTypeList.clear();
                for (DataSnapshot exerciseType: snapshot.getChildren()) {
                    exerciseTypeList.add(exerciseType.getValue(ExerciseTypeClass.class));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void setAdapter() {
        adapter = new ExerciseTypeAdapter(exerciseTypeList, workoutDay, muscleType, activityResultLauncher);
        recyclerView.setAdapter(adapter);
    }
}