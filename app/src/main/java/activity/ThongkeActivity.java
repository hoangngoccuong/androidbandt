package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.ThongkeAdapter;
import modell.Thongke;
import ultil.Server;
import ultil.sever;

public class ThongkeActivity extends AppCompatActivity {
    EditText edtsearchtk;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    Server server;
    ArrayList<Thongke> arrayList;
    ThongkeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        Anhxa();
        // ActionBar();
        if (MainActivity.islogin) {
            Getdulieusp();
        } else{
            Toast.makeText(getApplicationContext(), "Đề nghị bạn đăng nhập để sử dụng", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
    }
    private void Getdulieusp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.Thongke, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String madonhang = "";
                    int masanpham = 0;
                    String tensanpham = "";
                    int giasanpham = 0;
                    int soluongsanpham = 0;
                    String ngaydathang = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            madonhang = jsonObject.getString("madonhang");
                            masanpham = jsonObject.getInt("masanpham");
                            tensanpham = jsonObject.getString("tensanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            soluongsanpham = jsonObject.getInt("soluongsanpham");
                            ngaydathang = jsonObject.getString("ngaydathang");
                            arrayList.add(new Thongke(ID, madonhang, masanpham, tensanpham,giasanpham,soluongsanpham,ngaydathang));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionBar() {
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void Anhxa() {
        //toolbar = findViewById(R.id.toolbarmanhinhchinh);
        listView = findViewById(R.id.lv);
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        arrayList = new ArrayList<>();
        adapter = new ThongkeAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
        edtsearchtk = findViewById(R.id.edtsearchtk);
        edtsearchtk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable);

            }
        });
    }
}