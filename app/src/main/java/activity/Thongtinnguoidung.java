package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ultil.sever;

public class Thongtinnguoidung extends AppCompatActivity {

    EditText edtCustomerName,edtEmail,edtPhoneNumber;
    Button btnConfirm,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinnguoidung);
        Anhxa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
               // finish();
            }
        });

            EventButton();
    }

    private void EventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = edtCustomerName.getText().toString().trim();
                final String Phone = edtPhoneNumber.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(Name.length()>0&& Phone.length()>0&&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.billlink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
 //                           if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, sever.orderdetaillink, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String success = jsonObject.getString("success");
                                            if (success.equals("1")) {
                                            MainActivity.arrCart.clear();
                                            Toast.makeText(getApplicationContext(), "Ban da dat hang thanh cong", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Đon hàng của bạn đã được đặt,Moi ban tiep tuc mua sam", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Du lieu gio hang da bi loi", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "loi" +e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                        Log.d("ngay", date.toString());
                                        for(int i=0;i<MainActivity.arrCart.size();i++)
                                        {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.arrCart.get(i).getProductId());
                                                jsonObject.put("tensanpham",MainActivity.arrCart.get(i).getProductName());
                                                jsonObject.put("giasanpham",MainActivity.arrCart.get(i).getPrice());
                                                jsonObject.put("soluongsanpham",MainActivity.arrCart.get(i).getProductNumber());
                                                jsonObject.put("ngaydathang",date.toString());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
//                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap= new HashMap<String,String>();
                            hashMap.put("tenkhachhang",Name);
                            hashMap.put("sodienthoai",Phone);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                   // CheckInternetConnection.ShowToast_Short(getApplicationContext(),"Kiem Tra Lai Du Lieu");
                }
            }
        });
    }

    private void Anhxa() {
        edtCustomerName = findViewById(R.id.edittextcustomername);
        edtEmail  =findViewById(R.id.edittextemail);
        edtPhoneNumber = findViewById(R.id.edittextphonenumber);
        btnConfirm = findViewById(R.id.buttonvertify);
        btnBack = findViewById(R.id.buttonback);
    }
}
