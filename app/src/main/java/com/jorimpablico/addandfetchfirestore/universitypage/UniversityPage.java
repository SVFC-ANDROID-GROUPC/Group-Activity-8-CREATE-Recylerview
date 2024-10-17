package com.jorimpablico.addandfetchfirestore.universitypage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;

public class UniversityPage extends AppCompatActivity {

    EditText etName, etType;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    University universityObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_page);

        universityObject = new University();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        universityObject.setName(etName.getText().toString());
        universityObject.setType(etType.getText().toString());

        Log.d("MAIN", "name: " + universityObject.getName() + ", type: " + universityObject.getType());

        firestore.collection("university")
                .add(universityObject).addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n School Name: ").append(universityObject.getName());
                    stringBuilder.append("\n Type: ").append(universityObject.getType());

                    tvResults.setText(stringBuilder.toString());
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("university")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            University university = document.toObject(University.class);
                            stringBuilder1.append("\n\n School Name: ").append(university.getName());
                            stringBuilder1.append("\n Type: ").append(university.getType());
                        }

                        tvResults.setText(stringBuilder1.toString());
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}