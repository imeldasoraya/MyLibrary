package com.example.mylibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText inputText;
    Button storeButton, getNameButton;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        inputText = findViewById(R.id.inputText);
        storeButton = findViewById(R.id.storeButton);
        getNameButton = findViewById(R.id.getNameButton);
        displayText = findViewById(R.id.displayText);

        storeButton.setOnClickListener(v -> saveName());
        getNameButton.setOnClickListener(v -> displayNames());
    }

    private void saveName() {
        String name = inputText.getText().toString();
        if (!name.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, name);
            db.insert(DatabaseHelper.TABLE_NAME, null, values);
            db.close();
            inputText.setText("");
        }
    }

    private void displayNames() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{DatabaseHelper.COLUMN_NAME}, null, null, null, null, null);
        StringBuilder names = new StringBuilder();
        while (cursor.moveToNext()) {
            names.append(cursor.getString(0)).append("\n");
        }
        cursor.close();
        db.close();
        displayText.setText(names.toString());
    }
}
