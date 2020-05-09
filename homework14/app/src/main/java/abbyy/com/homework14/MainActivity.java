package abbyy.com.homework14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.app_name);

        final boolean isPhone = getResources().getBoolean(R.bool.isPhone);
        if (savedInstanceState == null) {
            final int containerId = isPhone ? R.id.mainContainer : R.id.mainListContainer;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerId, ListFragment.newInstance(), ListFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        } else if (!isPhone) {
            // на планшете отдельно обрабатываем случаи поворота экрана
            // 1) при переходе портрет -> лендскейп с открытой заметкой нужно отобразить сам список
            // 2) при переходе лендскейп -> портрет нужно убрать из отображения список
            final Fragment listFragment = getSupportFragmentManager().findFragmentByTag(ListFragment.TAG);
            if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .show(listFragment)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(listFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    public void showDetailFragment(@NonNull final long id) {
        // Если телефон, заменяем основной контейнер и все
        if (getResources().getBoolean(R.bool.isPhone)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, DetailFragment.newInstance(id), DetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(getSupportFragmentManager().findFragmentByTag(ListFragment.TAG))
                    .replace(R.id.mainDetailContainer, DetailFragment.newInstance(id), DetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        } else {
            if (getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG) != null) {
                // Если на экране уже есть фрагмент с деталями, то надо его убрать перед показом нового
                getSupportFragmentManager().popBackStack();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainDetailContainer, DetailFragment.newInstance(id), DetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}