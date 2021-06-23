package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    // Declare attributes
    EditText editText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize attributes
        this.editText = findViewById(R.id.editText);
        this.saveButton = findViewById(R.id.saveButton);

        // Set page name
        getSupportActionBar().setTitle("Edit item");

        // Set name of object to edit
        editText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // Save button listener to return to MainActivity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent which will contain the new name
                Intent intent = new Intent();

                // Pass the data that is going to be returned
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // Set result of the intent using pre-defined result
                setResult(RESULT_OK, intent);

                // Close this screen and go back
                finish();
            }
        });

    }
}