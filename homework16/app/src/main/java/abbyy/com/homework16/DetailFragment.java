package abbyy.com.homework16;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import java.io.File;

public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    private static final String ID_KEY = "ID_KEY";

    private ImageView imageView;
    private TextView textView;
    private AsyncTask asyncTask;

    public static Fragment newInstance(@NonNull final long id) {
        final Fragment fragment = new DetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putLong(ID_KEY, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detailed_note, container, false);
    }

    @SuppressLint( "StaticFieldLeak" )
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.detailedPageImg);
        textView = view.findViewById(R.id.detailedPageTxt);
        final long id = getArguments().getLong(ID_KEY);

        App.getNoteRepository().getNoteById(asyncTask, id, new NoteRepository.OnGetNoteByIdCallback() {
            @Override
            public void onGetNote(Note note) {
                textView.setText(note.getText());
                Integer drawId = note.getDrawableId();
                if (drawId == null) {
                    Picasso.get().load(new File(note.getPath())).fit().centerInside().into(imageView);
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(drawId));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(asyncTask != null) {
            asyncTask.cancel( true );
            asyncTask = null;
        }
    }
}
