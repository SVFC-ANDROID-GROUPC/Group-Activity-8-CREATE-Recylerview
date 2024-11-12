package com.jorimpablico.addandfetchfirestore.bookspage;

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
import com.jorimpablico.addandfetchfirestore.adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class BooksPage extends AppCompatActivity {

    RecyclerView rvBook;
    List<Book> bookList = new ArrayList<>();
    List<Book> bookList1 = new ArrayList<>();
    BookAdapter bookAdapter;
    EditText etTitle, etAuthor;
    Button btnAddItem, btnGetItem;

    Book bookObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_page);

        bookObject = new Book();
        firestore = FirebaseFirestore.getInstance();
        bookAdapter = new BookAdapter(bookList);

        rvBook = findViewById(R.id.rv_books);
        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());

    }

    private void addFunction(){
        bookObject.setTitle(etTitle.getText().toString());
        bookObject.setAuthor(etAuthor.getText().toString());

        Log.d("MAIN", "title: " + bookObject.getTitle() + ", author: " + bookObject.getAuthor());

        firestore.collection("books")
                .add(bookObject).addOnSuccessListener(documentReference -> {
                    bookList.add(new Book(bookObject.getTitle(), bookObject.getAuthor()));

                    rvBook.setAdapter(bookAdapter);
                    rvBook.setLayoutManager(new LinearLayoutManager(this));
                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });


    }

    private void getFunction(){
        bookList1.clear();
        BookAdapter bookAdapter1 = new BookAdapter(bookList1);

        firestore.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        for(QueryDocumentSnapshot document: task.getResult()){
                            Book book = document.toObject(Book.class);
                            bookList1.add(new Book(book.getTitle(), book.getAuthor()));
                        }

                        rvBook.setAdapter(bookAdapter1);
                        rvBook.setLayoutManager(new LinearLayoutManager(this));
                    }
                    else {
                        Log.e("MAIN", task.getException().getMessage());
                    }

                }).addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}