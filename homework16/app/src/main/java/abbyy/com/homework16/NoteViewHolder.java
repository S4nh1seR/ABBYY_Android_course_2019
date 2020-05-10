package abbyy.com.homework16;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private CardView startNote;
    private long id;
    private NoteAdapter.Listener listener;

    private ImageView imageView;
    private ImageView popupView;
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
        popupView = itemView.findViewById(R.id.popupMenu);
    }

    public void bind(final Note note) {
        id = note.getId();
        Integer drawId = note.getDrawableId();
        if (drawId == null) {
            Picasso.get().load(new File(note.getPath())).fit().centerInside().into(imageView);
        } else {
            imageView.setImageDrawable(itemView.getResources().getDrawable(drawId));
        }
        textDateView.setText(new SimpleDateFormat("yyyy.mm.dd").format(note.getDate()));
        textView.setText(note.getText());

        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(App.getContext(), view);
                popup.inflate(R.menu.popup_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popupShare:
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textView.getText());
                                ((MainActivity)view.getContext()).startActivity(sharingIntent, null);
                                return true;
                            case R.id.popupDelete:
                                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                        .setTitle(R.string.delete_title)
                                        .setMessage(R.string.delete_accept)
                                        .setPositiveButton(R.string.pos_answer, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                App.getNoteRepository().deleteNote(id);
                                                ((MainActivity)view.getContext()).updateNoteList();
                                                Toast.makeText(view.getContext(), R.string.pos_textlog, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton(R.string.neg_answer, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(view.getContext(), R.string.neg_textlog, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .create();
                                dialog.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }
}