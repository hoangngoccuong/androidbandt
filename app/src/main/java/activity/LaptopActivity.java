package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.LaptopAdapter;
import modell.Sanpham;
import ultil.sever;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt=0;
    int page=1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    LaptopActivity.mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();

        GetIdloaisp();
        ActionToolbar();
        GetData(page);
        LoadMoreData();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.menucart:
//                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String link= sever.product+"?page="+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String phoneName="";
                int price=0;
                String image="";
                String detail="";
                int productId=0;
                if(response != null  ){
                    //lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            phoneName = jsonObject.getString("tensanpham");
                            price = jsonObject.getInt("giasanpham");
                            image = jsonObject.getString("hinhanhsanpham");
                            detail = jsonObject.getString("mota");
                            productId = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id,phoneName,price,image,detail,productId));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
//                    limitdata=true;
//                    lvdt.removeFooterView(footerview);
//                    Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                    // CheckInternetConnection.ShowToast_Short(getApplicationContext(),"No more data !");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        iddt=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",iddt+"");
    }
    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),chitietsanphamActivity.class);
                intent.putExtra("productData",mangdt.get(position));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false &&limitdata==false){
                    isLoading=true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void AnhXa() {
        toolbardt = (Toolbar) findViewById(R.id.toolbarlaptop);
        lvdt=(ListView) findViewById(R.id.lvlaptop);
        mangdt=new ArrayList<>();
        laptopAdapter=new LaptopAdapter(getApplicationContext(),mangdt);
        lvdt.setAdapter(laptopAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progessbar,null);
        mHandler = new LaptopActivity.mHandler();
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }


}
