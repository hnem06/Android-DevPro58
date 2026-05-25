package com.hnem06.takenote;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "extra_note_id";
    public static final String EXTRA_NOTE_TITLE = "extra_note_title";
    public static final String EXTRA_NOTE_DESCRIPTION = "extra_note_description";

    private EditText etNoteTitle;
    private EditText etNoteDescription;
    private NoteViewModel noteViewModel;

    private int editNoteId = -1; // -1 = create new, >= 0 = edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addNoteRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteDescription = findViewById(R.id.etNoteDescription);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnSave = findViewById(R.id.btnSave);
        TextView tvToolbarTitle = findViewById(R.id.tvToolbarTitle);

        if (getIntent().hasExtra(EXTRA_NOTE_ID)) {
            editNoteId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
            String title = getIntent().getStringExtra(EXTRA_NOTE_TITLE);
            String description = getIntent().getStringExtra(EXTRA_NOTE_DESCRIPTION);

            etNoteTitle.setText(title);
            etNoteDescription.setText(description);
            tvToolbarTitle.setText(R.string.edit_note);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = etNoteTitle.getText().toString().trim();
        String description = etNoteDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etNoteTitle.setError(getString(R.string.empty_title_error));
            etNoteTitle.requestFocus();
            return;
        }

        if (editNoteId >= 0) {
            Note note = new Note(title, description, false, System.currentTimeMillis());
            note.setId(editNoteId);
            noteViewModel.update(note);
        } else {
            Note note = new Note(title, description, false, System.currentTimeMillis());
            noteViewModel.insert(note);
        }

        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}
