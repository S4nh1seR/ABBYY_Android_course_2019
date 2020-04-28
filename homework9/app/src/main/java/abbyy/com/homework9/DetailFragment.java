package abbyy.com.homework9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    private static final String ID_KEY = "ID_KEY";

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

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long id = getArguments().getLong(ID_KEY);
        final Note note = NoteRepository.getNoteWithId(id);

        final ImageView detailedPageImgView = view.findViewById(R.id.detailedPageImg);
        detailedPageImgView.setImageDrawable(getResources().getDrawable(note.getDrawableId()));

        final TextView detailedPageTxtView = view.findViewById(R.id.detailedPageTxt);
        detailedPageTxtView.setText(note.getText());
    }
}
