package com.example.mvvmtaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class EdditAnddAddNotesActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.mvvmtaskapp.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.mvvmtaskapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.mvvmtaskapp.EXTRA_DESCRIPTION ";
    public static final String EXTRA_PERIORITY = "com.example.mvvmtaskapp.EXTRA_PERIORITY";


    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPeriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPeriority = findViewById(R.id.number_picker_preriority);
        numberPickerPeriority.setMinValue(1);
        numberPickerPeriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
         Intent intent = getIntent();
         if (intent.hasExtra(EXTRA_ID)){
             setTitle("edit notes");
             editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
             editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
             numberPickerPeriority.setValue(intent.getIntExtra(EXTRA_PERIORITY,1));
         }else {
             setTitle("add notes");

         }
    }

    private void saveNotes(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int periority = numberPickerPeriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Pleas Insert Title And Decription",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PERIORITY,periority);
        int id =getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID ,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.add_notes_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_Notes:
                saveNotes();
                return  true;
                default:
        return super.onOptionsItemSelected(item);
        }
    }
}
