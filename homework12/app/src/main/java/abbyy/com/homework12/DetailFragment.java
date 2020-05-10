package abbyy.com.homework12;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;

public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    private static final String ID_KEY = "ID_KEY";

    private ProgressBar progressBar;
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
        progressBar = view.findViewById(R.id.progressBar);
        final long id = getArguments().getLong(ID_KEY);

        asyncTask = new AsyncTask<Long, Void, Note>() {
            @WorkerThread
            @Override
            protected Note doInBackground(final Long... id) {
                return App.getNoteRepository().getNoteWithId(id[0]);
            }

            @MainThread
            @Override
            protected void onPostExecute(final Note note) {
                super.onPostExecute(note);
                imageView.setImageDrawable(getResources().getDrawable(note.getDrawableId()));
                textView.setText(note.getText());
                progressBar.setVisibility( View.INVISIBLE );
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
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
