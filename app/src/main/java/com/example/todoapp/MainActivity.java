package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Define the list of the items to-do (strings)
    List<String> items;
    ItemsAdapter itemsAdapter;

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

        // Create a new instance of the LongClickListener and override what it does when an item is long pressed
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                // Delete an item from the list
                items.remove(position);

                // Notify the adapter that an item has been removed
                itemsAdapter.notifyItemRemoved(position);

                // Let the user know an item was removed by using a toast
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
            }
        };

        // Construct our adapter
        this.itemsAdapter = new ItemsAdapter(this.items, onLongClickListener);

        // Set our adapter in our RecyclerView
        rvItems.setAdapter(itemsAdapter);

        // Set LayoutManager (how items are displayed) -> default: vertical
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        // Add an EventListener to our addButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When button is clicked get the input text and convert it to string
                String item = newItem.getText().toString();

                // Add item to the model
                items.add(item);

                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);

                // Clear the input text
                newItem.setText("");

                // Let the user know an item was added by using a toast
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_LONG).show();
            }
        });
    }
}