package abbyy.com.homework16;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListFragment extends Fragment implements NoteAdapter.Listener {

    public static final String TAG = "ListFragment";

    public static Fragment newInstance() {
        return new ListFragment();
    }

    private AsyncTask asyncTask;
    private NoteAdapter adapter;
    private static final int undefinedNoteId = -1;
    private int noteToShow = undefinedNoteId;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list, container, false);
    }

    @SuppressLint( "StaticFieldLeak" )
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton cameraNoteButton = view.findViewById(R.id.cameraNoteButton);
        cameraNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(CameraActivity.getIntent(App.getContext()), 1);
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.notesRecyclerView);

        final Context context = ((MainActivity) getActivity()).getApplicationContext();
        final boolean dualSpan = getResources().getBoolean(R.bool.dualSpan);
        final RecyclerView.LayoutManager layoutManager = dualSpan
                ? new GridLayoutManager(context, 2) : new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 5);

        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        processNotes();
    }

    @SuppressLint( "StaticFieldLeak" )
    public void processNotes() {
        asyncTask = new AsyncTask<Void, Void, List<Note>>() {
            @WorkerThread
            @Override
            protected List<Note> doInBackground(final Void... voids) {
                return App.getNoteRepository().getNotes();
            }

            @MainThread
            @Override
            protected void onPostExecute(final List<Note> list) {
                super.onPostExecute(list);
                adapter.setNotesList(list);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onNoteClick(final long id) {
        ((MainActivity)getActivity()).showDetailFragment(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        processNotes();
        if (noteToShow != undefinedNoteId) {
            onNoteClick(noteToShow);
            noteToShow = undefinedNoteId;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        noteToShow = data.getIntExtra("noteToShow", undefinedNoteId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if( asyncTask != null ) {
            asyncTask.cancel( true );
            asyncTask = null;
        }
    }
}