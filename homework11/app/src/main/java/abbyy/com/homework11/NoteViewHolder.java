package abbyy.com.homework11;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private CardView startNote;
    private long id;
    private NoteAdapter.Listener listener;
    private ImageView imageView;
    private TextView textDateView;
    private TextView textView;

    public NoteViewHolder(final View itemView, final NoteAdapter.Listener listener) {
        super(itemView);
        startNote = itemView.findViewById(R.id.cardView);
        startNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(listener != null) {
                    listener.onNoteClick(id);
                }
            }
        });
        imageView = itemView.findViewById(R.id.mainImage);
        textDateView = itemView.findViewById(R.id.mainDate);
        textView = itemView.findViewById(R.id.mainText);
    }

    public void bind(final Note note) {
        id = note.getId();
        imageView.setImageDrawable(itemView.getResources().getDrawable(note.getDrawableId()));
        textDateView.setText(new SimpleDateFormat("yyyy.mm.dd").format(note.getDate()));
        textView.setText(note.getText());
    }
}