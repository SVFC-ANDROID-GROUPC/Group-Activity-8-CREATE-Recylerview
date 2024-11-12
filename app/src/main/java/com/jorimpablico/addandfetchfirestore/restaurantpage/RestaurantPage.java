package com.jorimpablico.addandfetchfirestore.restaurantpage;

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
import com.jorimpablico.addandfetchfirestore.adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPage extends AppCompatActivity {

    RecyclerView rvRestaurant;
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Restaurant> restaurantList1 = new ArrayList<>();
    RestaurantAdapter restaurantAdapter;
    EditText etName, etType;
    Button btnAddItem, btnGetItem;

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
        restaurantAdapter = new RestaurantAdapter(restaurantList);

        rvRestaurant = findViewById(R.id.rv_restaurant);
        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        restaurantObject.setName(etName.getText().toString());
        restaurantObject.setType(etType.getText().toString());

        Log.d("MAIN", "name: " + restaurantObject.getName() + ", type: " + restaurantObject.getType());

        firestore.collection("restaurant")
                .add(restaurantObject).addOnSuccessListener(documentReference -> {
                    restaurantList.add(new Restaurant(restaurantObject.getName(), restaurantObject.getType()));

                    rvRestaurant.setAdapter(restaurantAdapter);
                    rvRestaurant.setLayoutManager(new LinearLayoutManager(this));
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        restaurantList1.clear();
        RestaurantAdapter restaurantAdapter1 = new RestaurantAdapter(restaurantList);

        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            restaurantList1.add(new Restaurant(restaurant.getName(), restaurant.getType()));
                        }

                        rvRestaurant.setAdapter(restaurantAdapter1);
                        rvRestaurant.setLayoutManager(new LinearLayoutManager(this));
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}