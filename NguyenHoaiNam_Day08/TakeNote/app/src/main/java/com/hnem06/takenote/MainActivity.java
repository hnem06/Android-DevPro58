package com.hnem06.takenote;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;
    private RecyclerView rvNotes;
    private LinearLayout layoutEmpty;
    private EditText etSearch;

    private List<Note> allNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvNotes = findViewById(R.id.rvNotes);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        etSearch = findViewById(R.id.etSearch);
        TextView btnAddNote = findViewById(R.id.btnAddNote);

        // Setup RecyclerView
        noteAdapter = new NoteAdapter();
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);

        // Setup ViewModel & observe dữ liệu
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            allNotes = notes != null ? notes : new ArrayList<>();
            filterNotes(etSearch.getText().toString());
        });

        noteAdapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_ID, note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_TITLE, note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_DESCRIPTION, note.getDescription());
                startActivity(intent);
            }

            @Override
            public void onMoreClick(Note note, View anchor) {
                showNotePopupMenu(note, anchor);
            }
        });

        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterNotes(String query) {
        if (query == null || query.trim().isEmpty()) {
            noteAdapter.submitList(new ArrayList<>(allNotes));
            updateEmptyState(allNotes);
        } else {
            String lowerQuery = query.toLowerCase().trim();
            List<Note> filtered = new ArrayList<>();
            for (Note note : allNotes) {
                if (note.getTitle().toLowerCase().contains(lowerQuery)
                        || note.getDescription().toLowerCase().contains(lowerQuery)) {
                    filtered.add(note);
                }
            }
            noteAdapter.submitList(new ArrayList<>(filtered));
            updateEmptyState(filtered);
        }
    }

    private void updateEmptyState(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            rvNotes.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvNotes.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void showNotePopupMenu(Note note, View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add(0, 1, 0, note.isCompleted() ? "Mark Incomplete" : "Mark Complete");
        popup.getMenu().add(0, 2, 1, getString(R.string.delete));

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                    note.setCompleted(!note.isCompleted());
                    noteViewModel.update(note);
                    return true;
                case 2:
                    showDeleteConfirmDialog(note);
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    private void showDeleteConfirmDialog(Note note) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_confirm_title)
                .setMessage(R.string.delete_confirm_message)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    noteViewModel.delete(note);
                    Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}