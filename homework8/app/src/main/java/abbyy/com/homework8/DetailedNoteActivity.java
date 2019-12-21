package abbyy.com.homework8;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;

public class DetailedNoteActivity extends AppCompatActivity {

    private static final String ID_KEY = "ID_KEY";

    public static Intent getIntent(final Context context, final long id) {
        final Intent intent = new Intent(context, DetailedNoteActivity.class);
        intent.putExtra(ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);

        final long id = getIntent().getLongExtra(ID_KEY, -1);
        final Note note = NoteRepository.getNoteWithId(id);

        final ImageView secondPageImageView = findViewById(R.id.secondPageImage);
        secondPageImageView.setImageDrawable(getDrawable(note.getDrawableId()));

        final TextView secondPageTextView = findViewById(R.id.secondPageText);
        secondPageTextView.setText(note.getText());
    }
}