package com.jorimpablico.addandfetchfirestore.employeepage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;

public class EmployeePage extends AppCompatActivity {

    EditText etName, etDepartment;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    Employee employeeObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        employeeObject = new Employee();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.et_name);
        etDepartment = findViewById(R.id.et_department);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction(){
        employeeObject.setName(etName.getText().toString());
        employeeObject.setDepartment(etDepartment.getText().toString());

        Log.d("MAIN", "name: " + employeeObject.getName() + ", department: " + employeeObject.getDepartment());

        firestore.collection("employee")
                .add(employeeObject).addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Name: ").append(employeeObject.getName());
                    stringBuilder.append("\n Department: ").append(employeeObject.getDepartment());

                    tvResults.setText(stringBuilder.toString());
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }

    private void getFunction(){
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("employee")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Employee employee = document.toObject(Employee.class);
                            stringBuilder1.append("\n\n Name: ").append(employee.getName());
                            stringBuilder1.append("\n Department: ").append(employee.getDepartment());
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