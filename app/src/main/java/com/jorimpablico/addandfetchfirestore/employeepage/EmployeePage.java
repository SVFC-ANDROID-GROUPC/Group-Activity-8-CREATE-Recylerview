package com.jorimpablico.addandfetchfirestore.employeepage;

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
import com.jorimpablico.addandfetchfirestore.adapter.EmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class EmployeePage extends AppCompatActivity {

    RecyclerView rvEmployee;
    List<Employee> employeeList = new ArrayList<>();
    List<Employee> employeeList1 = new ArrayList<>();
    EmployeeAdapter employeeAdapter;
    EditText etName, etDepartment;
    Button btnAddItem, btnGetItem;

    Employee employeeObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        employeeObject = new Employee();
        firestore = FirebaseFirestore.getInstance();
        employeeAdapter = new EmployeeAdapter(employeeList);

        rvEmployee = findViewById(R.id.rv_employee);
        etName = findViewById(R.id.et_name);
        etDepartment = findViewById(R.id.et_department);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction(){
        employeeObject.setName(etName.getText().toString());
        employeeObject.setDepartment(etDepartment.getText().toString());

        Log.d("MAIN", "name: " + employeeObject.getName() + ", department: " + employeeObject.getDepartment());

        firestore.collection("employee")
                .add(employeeObject).addOnSuccessListener(documentReference -> {
                    employeeList.add(new Employee(employeeObject.getName(), employeeObject.getDepartment()));

                    rvEmployee.setAdapter(employeeAdapter);
                    rvEmployee.setLayoutManager(new LinearLayoutManager(this));
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }

    private void getFunction(){
        employeeList1.clear();
        EmployeeAdapter employeeAdapter1 = new EmployeeAdapter(employeeList1);

        firestore.collection("employee")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Employee employee = document.toObject(Employee.class);
                            employeeList1.add(new Employee(employee.getName(), employee.getDepartment()));
                        }
                        rvEmployee.setAdapter(employeeAdapter1);
                        rvEmployee.setLayoutManager(new LinearLayoutManager(this));
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}