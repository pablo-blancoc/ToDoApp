package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Define the list of the items to-do (strings)
    List<String> items;

    // Define references from view page
    FloatingActionButton addButton;
    RecyclerView rvItems;
    EditText newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate variables from references from the views
        addButton = findViewById(R.id.addButton);
        newItem = findViewById(R.id.newItem);
        rvItems = findViewById(R.id.rvItems);

        // Instantiate our items list
        items = new ArrayList<>();
        items.add("Thing 1");
        items.add("Thing 2");
        items.add("Thing 3");

        // Construct our adapter
        ItemsAdapter itemsAdapter = new ItemsAdapter(this.items);

        // Set our adapter in our RecyclerView
        rvItems.setAdapter(itemsAdapter);

        // Set LayoutManager (how items are displayed) -> default: vertical
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }
}