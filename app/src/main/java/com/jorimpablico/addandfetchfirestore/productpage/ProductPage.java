package com.jorimpablico.addandfetchfirestore.productpage;

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
import com.jorimpablico.addandfetchfirestore.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends AppCompatActivity {

    RecyclerView rvProduct;
    List<Product> productList = new ArrayList<>();
    List<Product> productList1 = new ArrayList<>();
    ProductAdapter productAdapter;
    EditText etName, etPrice;
    Button btnAddItem, btnGetItem;

    Product productObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        productObject = new Product();
        firestore = FirebaseFirestore.getInstance();
        productAdapter = new ProductAdapter(productList);

        rvProduct = findViewById(R.id.rv_product);
        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        productObject.setName(etName.getText().toString());
        productObject.setPrice(Integer.parseInt(etPrice.getText().toString()));

        Log.d("MAIN", "name: " + productObject.getName() + ", prices: " + productObject.getPrice());

        firestore.collection("product")
                .add(productObject).addOnSuccessListener(documentReference -> {
                    productList.add(new Product(productObject.getName(), productObject.getPrice()));

                    rvProduct.setAdapter(productAdapter);
                    rvProduct.setLayoutManager(new LinearLayoutManager(this));
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        productList1.clear();
        ProductAdapter productAdapter1 = new ProductAdapter(productList1);

        firestore.collection("product")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Product product = document.toObject(Product.class);
                            productList1.add(new Product(product.getName(), product.getPrice()));
                        }
                        rvProduct.setAdapter(productAdapter1);
                        rvProduct.setLayoutManager(new LinearLayoutManager(this));
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}