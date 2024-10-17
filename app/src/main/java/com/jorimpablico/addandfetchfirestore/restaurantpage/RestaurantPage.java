package com.jorimpablico.addandfetchfirestore.restaurantpage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;

public class RestaurantPage extends AppCompatActivity {

    EditText etName, etType;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    Restaurant restaurantObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        restaurantObject = new Restaurant();
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
        restaurantObject.setName(etName.getText().toString());
        restaurantObject.setType(etType.getText().toString());

        Log.d("MAIN", "name: " + restaurantObject.getName() + ", type: " + restaurantObject.getType());

        firestore.collection("restaurant")
                .add(restaurantObject).addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Restaurant Name: ").append(restaurantObject.getName());
                    stringBuilder.append("\n Type: ").append(restaurantObject.getType());

                    tvResults.setText(stringBuilder.toString());
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            stringBuilder1.append("\n\n Restaurant Name: ").append(restaurant.getName());
                            stringBuilder1.append("\n Type: ").append(restaurant.getType());
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