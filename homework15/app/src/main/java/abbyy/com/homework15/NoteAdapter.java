package abbyy.com.homework15;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    public interface Listener {

        void onNoteClick(long id);
    }

    private List<Note> notesList = new ArrayList<>();
    private Listener listener;

    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    public void setNotesList(final List<Note> notesList) {
        this.notesList = notesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        final View view = layoutInflater.inflate(R.layout.note, parent,false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
        holder.bind(notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}