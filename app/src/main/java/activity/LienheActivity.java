package activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.R;

public class LienheActivity extends AppCompatActivity {
    Toolbar toolbarcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienhe);
        toolbarcontact = findViewById(R.id.toolbarcontact);
        ActionBar();
    }

    private void ActionBar() {
        setSupportActionBar(toolbarcontact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcontact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
