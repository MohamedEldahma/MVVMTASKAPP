package com.example.mvvmtaskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final  int ADD_NOTES_EXTRA = 1;
    public static final  int EDDIT_NOTES_EXTRA = 1;

    private List<NotesTable> notesTables ;

    NotesTableViewModel notesTableViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNotes = findViewById(R.id.button_add_Notes);
        buttonAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , EdditAnddAddNotesActivity.class);
                startActivityForResult(intent,1);
            }
        });

         recyclerView = findViewById(R.id.notes_ecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NotesTableItemAdapter adapter = new NotesTableItemAdapter();
        recyclerView.setAdapter(adapter);
        notesTableViewModel = ViewModelProviders.of(this).get(NotesTableViewModel.class);
        notesTableViewModel.getAllNotesTable().observe(this, new Observer<List<NotesTable>>() {
            @Override
            public void onChanged(List<NotesTable> notesTable) {
              adapter.submitList(notesTable);
//                recyclerView.setAdapter(adapter);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notesTableViewModel.delete(adapter.getNotesTableAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"Notes Deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NotesTableItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(NotesTable notesTable) {
                Intent intent = new Intent(MainActivity.this , EdditAnddAddNotesActivity.class);
                intent.putExtra(EdditAnddAddNotesActivity.EXTRA_ID,notesTable.getId());
                intent.putExtra(EdditAnddAddNotesActivity.EXTRA_TITLE ,notesTable.getPriority());
                intent.putExtra(EdditAnddAddNotesActivity.EXTRA_DESCRIPTION,notesTable.getDescription());
                intent.putExtra(EdditAnddAddNotesActivity.EXTRA_PERIORITY,notesTable.getPriority());
                startActivityForResult(intent,EDDIT_NOTES_EXTRA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTES_EXTRA && resultCode == RESULT_OK){
            String title = data.getStringExtra(EdditAnddAddNotesActivity.EXTRA_TITLE);
            String decription = data.getStringExtra(EdditAnddAddNotesActivity.EXTRA_DESCRIPTION);
            int periority = data.getIntExtra(EdditAnddAddNotesActivity.EXTRA_PERIORITY,1);
            NotesTable notes = new NotesTable(title,decription,periority);
            notesTableViewModel.insert(notes);
            Toast.makeText(this,"Notes Saved",Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDDIT_NOTES_EXTRA&& resultCode == RESULT_OK){
            int id = data.getIntExtra(EdditAnddAddNotesActivity.EXTRA_ID,-1);
           if (id == -1){
               Toast.makeText(this,"Notes Can not be Updated",Toast.LENGTH_SHORT).show();
                return; }
            String title = data.getStringExtra(EdditAnddAddNotesActivity.EXTRA_TITLE);
            String decription = data.getStringExtra(EdditAnddAddNotesActivity.EXTRA_DESCRIPTION);
            int periority = data.getIntExtra(EdditAnddAddNotesActivity.EXTRA_PERIORITY,1);
            NotesTable notesTable = new NotesTable(title,decription,periority);
            notesTable.setId(id);
            notesTableViewModel.update(notesTable);
            Toast.makeText(this,"Notes Updated",Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(this,"Notes Not Saved",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.min_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.all_notes_deleted:
                notesTableViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this,"All NotesTable Deleted",Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
