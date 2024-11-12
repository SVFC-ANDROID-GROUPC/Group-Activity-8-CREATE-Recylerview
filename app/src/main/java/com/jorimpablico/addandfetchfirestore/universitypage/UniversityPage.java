package com.jorimpablico.addandfetchfirestore.universitypage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;
import com.jorimpablico.addandfetchfirestore.adapter.UniversityAdapter;

import java.util.ArrayList;
import java.util.List;

public class UniversityPage extends AppCompatActivity {

    RecyclerView rvUniversity;
    List<University> universityList = new ArrayList<>();
    List<University> universityList1 = new ArrayList<>();
    UniversityAdapter universityAdapter;
    EditText etName, etType;
    Button btnAddItem, btnGetItem;

    University universityObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_page);

        universityObject = new University();
        firestore = FirebaseFirestore.getInstance();
        universityAdapter = new UniversityAdapter(universityList);

        rvUniversity = findViewById(R.id.rv_university);
        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        universityObject.setName(etName.getText().toString());
        universityObject.setType(etType.getText().toString());

        Log.d("MAIN", "name: " + universityObject.getName() + ", type: " + universityObject.getType());

        firestore.collection("university")
                .add(universityObject).addOnSuccessListener(documentReference -> {
                    universityList.add(new University(universityObject.getName(), universityObject.getType()));

                    rvUniversity.setAdapter(universityAdapter);
                    rvUniversity.setLayoutManager(new LinearLayoutManager(this));
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        universityList1.clear();
        UniversityAdapter universityAdapter1 = new UniversityAdapter(universityList1);

        firestore.collection("university")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            University university = document.toObject(University.class);
                            universityList1.add(new University(university.getName(), university.getType()));
                        }

                        rvUniversity.setAdapter(universityAdapter1);
                        rvUniversity.setLayoutManager(new LinearLayoutManager(this));
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}