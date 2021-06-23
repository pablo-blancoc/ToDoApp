package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

    // Define keys for passing data
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate variables from references from the views
        addButton = findViewById(R.id.addButton);
        newItem = findViewById(R.id.newItem);
        rvItems = findViewById(R.id.rvItems);

        // Populate our items list
        this.loadItems();

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

                // Save changes
                saveItems();
            }
        };

        // Create an instance for the ClickListener to edit an object
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                // Create the new activity using an intent
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                // Pass data in our intent
                intent.putExtra(KEY_ITEM_TEXT, items.get(position));
                intent.putExtra(KEY_ITEM_POSITION, position);

                // Display the activity
                startActivityForResult(intent, EDIT_TEXT_CODE);
            }
        };

        // Construct our adapter
        this.itemsAdapter = new ItemsAdapter(this.items, onLongClickListener, onClickListener);

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

                // Do not add empty items
                if(item.equals("")) {
                    Toast.makeText(getApplicationContext(), "Cannot add empty item", Toast.LENGTH_LONG).show();
                    return;
                }

                // Add item to the model
                items.add(item);

                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);

                // Clear the input text
                newItem.setText("");

                // Let the user know an item was added by using a toast
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_LONG).show();

                // Save changes
                saveItems();
            }
        });
    }

    // Handle the result from the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // Retrieved new name of object
            String newItemName = data.getStringExtra(KEY_ITEM_TEXT);

            // Retrieve original position on list of the object
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // Update the model at the right position
            items.set(position, newItemName);

            // Notify the adapter that there has been a change
            itemsAdapter.notifyItemChanged(position);

            // Let the user know an item was updated
            Toast.makeText(getApplicationContext(), "Item updated successfully!", Toast.LENGTH_LONG).show();

            // Save changes
            saveItems();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        // Return the file with the data from the app
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        // Load items by reading every line of the data file
        try {
            this.items = new ArrayList<>(FileUtils.readLines(this.getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items from data file", e);
            this.items = new ArrayList<>();
        }
    }

    private void saveItems() {
        // Save items by writing them into a file
        try {
            FileUtils.writeLines(this.getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items to data file", e);
        }
    }
}