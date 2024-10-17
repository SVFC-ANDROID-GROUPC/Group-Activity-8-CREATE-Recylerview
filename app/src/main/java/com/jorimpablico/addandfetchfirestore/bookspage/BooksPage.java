package com.jorimpablico.addandfetchfirestore.bookspage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jorimpablico.addandfetchfirestore.R;

public class BooksPage extends AppCompatActivity {

    EditText etTitle, etAuthor;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    Book bookObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_page);

        bookObject = new Book();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance();

        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        bookObject.setTitle(etTitle.getText().toString());
        bookObject.setAuthor(etAuthor.getText().toString());

        Log.d("MAIN", "title: " + bookObject.getTitle() + ", author: " + bookObject.getAuthor());

        firestore.collection("books")
                .add(bookObject).addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Title: ").append(bookObject.getTitle());
                    stringBuilder.append("\n Author: ").append(bookObject.getAuthor());

                    tvResults.setText(stringBuilder.toString());
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Book book = document.toObject(Book.class);
                            stringBuilder1.append("\n\n Title: ").append(book.getTitle());
                            stringBuilder1.append("\n Author: ").append(book.getAuthor());
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