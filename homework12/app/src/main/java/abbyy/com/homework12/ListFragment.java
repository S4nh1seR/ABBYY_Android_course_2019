package abbyy.com.homework12;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListFragment extends Fragment implements NoteAdapter.Listener {

    public static final String TAG = "ListFragment";

    public static Fragment newInstance() {
        return new ListFragment();
    }

    private AsyncTask asyncTask;
    private NoteAdapter adapter;
    private ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.progressBar);
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
                progressBar.setVisibility( View.INVISIBLE );
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onNoteClick(final long id) {
        ((MainActivity)getActivity()).showDetailFragment(id);
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