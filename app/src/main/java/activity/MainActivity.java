package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.LoaispAdapter;
import adapter.SanphamAdapter;
import modell.Cart;
import modell.Loaisp;
import modell.Sanpham;
import ultil.Server;
import ultil.sever;

public class MainActivity extends AppCompatActivity {
    EditText edtsearch;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    ImageButton cart,btnuser;
    ImageView imggiohang;
    int id = 0;
    String tenloaisp ="";
    String hinhanhloaisp = "";
        Server server;
        ArrayList<Sanpham>arrayList;
        SanphamAdapter adapter;
    public static ArrayList<Cart> arrCart;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    static boolean islogin=false;
    static String username;
    //mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();

            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
           Getdulieusp();
           Itemlistview();
    }

    private void Itemlistview() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent= new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intent1= new Intent(MainActivity.this,DienthoaiActivity.class);
                        intent1.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intent2= new Intent(MainActivity.this,LaptopActivity.class);
                        intent2.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intent3= new Intent(MainActivity.this,LienheActivity.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        Intent intent4= new Intent(MainActivity.this,ThongtinActivity.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        Intent intent5= new Intent(MainActivity.this,ThongkeActivity.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                }

            }
        });
    }

    private void GetDuLieuLoaisp() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.producttypelink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Lien He","https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"));
                    mangloaisp.add(4,new Loaisp(0,"Thong Tin","https://cdn4.iconfinder.com/data/icons/pictograms-2/512/512-05-512.png"));
                    mangloaisp.add(5,new Loaisp(0,"Thống Kê","https://midesk.vn/public/images/feature/1544581179_1541121934_icon-04%20(1).png"));
                    //arrProductType.add(4,new ProductType(0,"Thong Tin", "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"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void CheckLogin()
    {
        if(islogin)
            Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
    }
    private void Getdulieusp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.newproduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String productname = "";
                    Integer productprice = 0;
                    String image = "";
                    String productdetail = "";
                    int productid = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            productname = jsonObject.getString("tensanpham");
                            productprice = jsonObject.getInt("giasanpham");
                            image = jsonObject.getString("hinhanhsanpham");
                            productdetail = jsonObject.getString("mota");
                            productid = jsonObject.getInt("idsanpham");
                            arrayList.add(new Sanpham(ID, productname, productprice, image, productdetail, productid));
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
    private void ActionViewFlipper() {
       // ArrayList<String> mangquangcao = new ArrayList<>();
       // mangquangcao.add("https://24hstore.vn/upload_images/images/dien%20thoai%20dau(3).png");
      //  mangquangcao.add("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/05/13/b18-hinh-nen-nokia-1280-cho-samsung-oppo-tren-iphone-anh-nen-nokia-1280-danh-cho-smartphone-dien-thoai-cam-ung.jpg");
       // mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1FeEbHnEJnztYpK-WMuKa5VlD2OTK_Nzj_A&usqp=CAU");
      //  for (int i = 0; i < mangquangcao.size(); i++) {
        //    ImageView imageView = new ImageView(getApplicationContext());
       //     Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
      //      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
       //     viewFlipper.addView(imageView);
      //  }
        //viewFlipper.setFlipInterval(5000);
       // viewFlipper.setAutoStart(true);
        //Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        //Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
       // viewFlipper.setInAnimation(animation_slide_in);
       // viewFlipper.setOutAnimation(animation_slide_out);
        int[] arrayHinh  ={R.drawable.nen1,R.drawable.nen2};
        for (int i = 0; i < arrayHinh.length; i++) {
            ImageView imageView  = new ImageView(this);
            imageView.setImageResource(arrayHinh[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }
    private void Searchsp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.search, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String productname = "";
                    Integer productprice = 0;
                    String image = "";
                    String productdetail = "";
                    int productid = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            productname = jsonObject.getString("tensanpham");
                            productprice = jsonObject.getInt("giasanpham");
                            image = jsonObject.getString("hinhanhsanpham");
                            productdetail = jsonObject.getString("mota");
                            productid = jsonObject.getInt("idsanpham");
                            arrayList.add(new Sanpham(ID, productname, productprice, image, productdetail, productid));
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
        toolbar =  findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper =findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chủ","https://vietadsgroup.vn/Uploads/files/trangchu-la-gi.png"));

        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        arrayList = new ArrayList<>();
        adapter = new SanphamAdapter(getApplicationContext(),arrayList);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(adapter);
        arrCart=new ArrayList<>();
        btnuser =(ImageButton)findViewById(R.id.user);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        if(arrCart != null){

        }else{
            arrCart=new ArrayList<>();
        }
        imggiohang= findViewById(R.id.imggiohang);
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        edtsearch = findViewById(R.id.edtsearch);
        edtsearch.addTextChangedListener(new TextWatcher() {
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
    private void validate()
    {
        Intent intent;
        if(islogin==false) {
            intent = new Intent(MainActivity.this, Login.class);
        }
        else
        {
            intent = new Intent(MainActivity.this, UserInformation.class);
        }
        startActivity(intent);
    }
}