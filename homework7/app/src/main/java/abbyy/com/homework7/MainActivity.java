package abbyy.com.homework7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.app_name);

        View card = findViewById(R.id.cardView);

        card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailedNoteActivity = new Intent(MainActivity.this, DetailedNoteActivity.class);
                startActivity(detailedNoteActivity);
            }
        });
    }
}