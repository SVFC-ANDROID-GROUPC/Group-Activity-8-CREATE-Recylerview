package com.jorimpablico.addandfetchfirestore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jorimpablico.addandfetchfirestore.bookspage.BooksPage;
import com.jorimpablico.addandfetchfirestore.employeepage.EmployeePage;
import com.jorimpablico.addandfetchfirestore.productpage.ProductPage;
import com.jorimpablico.addandfetchfirestore.restaurantpage.RestaurantPage;
import com.jorimpablico.addandfetchfirestore.universitypage.UniversityPage;

public class MainActivity extends AppCompatActivity {

    Button btnProductPage, btnEmployeePage, btnBookPage, btnUniversityPage, btnRestaurantPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProductPage = findViewById(R.id.btn_product_page);
        btnEmployeePage = findViewById(R.id.btn_employee_page);
        btnBookPage = findViewById(R.id.btn_book_page);
        btnUniversityPage = findViewById(R.id.btn_university_page);
        btnRestaurantPage = findViewById(R.id.btn_restaurant_page);

        btnProductPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductPage.class);
            startActivity(intent);
        });

        btnEmployeePage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmployeePage.class);
            startActivity(intent);
        });

        btnBookPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BooksPage.class);
            startActivity(intent);
        });

        btnUniversityPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UniversityPage.class);
            startActivity(intent);
        });

        btnRestaurantPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RestaurantPage.class);
            startActivity(intent);
        });

    }
}