package com.jorimpablico.addandfetchfirestore.productpage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;


public class ProductPage extends AppCompatActivity {

    EditText etName, etPrice;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    Product productObject;

    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        productObject = new Product();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        productObject.setName(etName.getText().toString());
        productObject.setPrice(Integer.parseInt(etPrice.getText().toString()));

        Log.d("MAIN", "name: " + productObject.getName() + ", prices: " + productObject.getPrice());

        firestore.collection("product")
                .add(productObject).addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Name: ").append(productObject.getName());
                    stringBuilder.append("\n Price: ").append(productObject.getPrice());

                    tvResults.setText(stringBuilder.toString());
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("product")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Product product = document.toObject(Product.class);
                            stringBuilder1.append("\n\n Name: ").append(product.getName());
                            stringBuilder1.append("\n Price: ").append(product.getPrice());
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