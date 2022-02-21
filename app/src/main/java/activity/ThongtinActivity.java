package activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan.R;

public class ThongtinActivity extends AppCompatActivity  {

    //private GoogleMap mMap;
    Toolbar toolbarinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        toolbarinfo = findViewById(R.id.toolbarinfo);
        ActionBar();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    private void ActionBar() {
        setSupportActionBar(toolbarinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarinfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




}
