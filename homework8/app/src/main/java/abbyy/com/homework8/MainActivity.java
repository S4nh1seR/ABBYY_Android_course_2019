package abbyy.com.homework8;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NoteAdapter.Listener {

    @Override
    public void onPersonClick(final long id) {
        startActivity(DetailedNoteActivity.getIntent(this, id));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        this.setTitle(R.string.app_name);

        final RecyclerView recyclerView = findViewById(R.id.NotesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 5);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setPersonList(NoteRepository.getNotes());
        adapter.setListener(this);
    }
}