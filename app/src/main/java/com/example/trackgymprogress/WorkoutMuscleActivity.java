package com.example.trackgymprogress;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutMuscleActivity extends AppCompatActivity {

    private FirebaseDatabase FBDatabase;
    Query muscleDatabase;
    private WorkoutDayClass workoutDay;
    private MuscleTypeAdapter adapter;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    RecyclerView recyclerView;
    private ArrayList<MuscleClass> muscleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getMuscleGroups();
                    }
                });
        setContentView(R.layout.activity_workout_muscle);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.muscleRecycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        workoutDay = (WorkoutDayClass)
                intent.getSerializableExtra(getString(R.string.WORKOUT_DAY_KEY));
        TextView title = findViewById(R.id.textView);
        String titleText = workoutDay.getDay() + " Types";
        title.setText(titleText);

        initFB();
        getMuscleGroups();

        fabAdd.setOnClickListener(view -> {
            Intent i = new Intent(WorkoutMuscleActivity.this, AddMuscleActivity.class);
            i.putExtra(getString(R.string.WORKOUT_DAY_KEY), workoutDay);
            activityResultLauncher.launch(i);
        });
    }

    public void initFB() {
        FBDatabase = FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL));
        muscleDatabase = FBDatabase.getReference(workoutDay.getDay()).child(getString(R.string.WORKOUT_MUSCLE_KEY)).orderByValue();
    }

    public void getMuscleGroups() {
        muscleDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                muscleList.clear();
                for (DataSnapshot muscleType: snapshot.getChildren()) {
                    muscleList.add(muscleType.getValue(MuscleClass.class));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void setAdapter() {
        adapter = new MuscleTypeAdapter(muscleList, workoutDay, activityResultLauncher);
        recyclerView.setAdapter(adapter);
    }
}