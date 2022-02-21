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

import java.util.HashMap;
import java.util.Map;

import ultil.sever;

public class Dangki extends AppCompatActivity {

    EditText email, password, repassword, phonenumber;
    Button signup;

    static boolean isexist=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        Anhxa();

            EventButton();
    }

    private void EventButton() {
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String Password = password.getText().toString().trim();
                final String Repassword = repassword.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String Phone = phonenumber.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Log.d("email",Email);
                Log.d("password",Password);
                Log.d("re",Repassword);
                if (Email.matches(emailPattern))
                {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String link = sever.checkuser+"?username="+Email;
                    //Log.d("link",link);
                    //Log.d("s",link);
                    StringRequest request = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("res",response.toString());
                            if(response.equals("exist"))
                            {
                                Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                Log.d("exist","true");
                                isexist=true;
                                return;
                            }
                            else{
                                SignUp();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(request);

                }else{
                    Toast.makeText(getApplicationContext(), "Kiểm tra dữ liệu nhập", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void SignUp(){
        final String Password = password.getText().toString().trim();
        final String Repassword = repassword.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Phone = phonenumber.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (Email.matches(emailPattern) && Repassword.length() > 0 && Repassword.equals(Password)) {
            Log.d("in", "in");
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest request = new StringRequest(Request.Method.POST, sever.signup, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       String success = jsonObject.getString("success");
                       if (success.equals("1")) {
                           Toast.makeText(getApplicationContext(), "Bạn đã đăng kí thành công", Toast.LENGTH_SHORT).show();
                           Login.Email = Email.toString();
                           Login.Pass = Password.toString();
                           Intent intent = new Intent(getApplicationContext(), Login.class);
                           startActivity(intent);
                       } else {
                           Toast.makeText(getApplicationContext(), "Lỗi khi đăng kí", Toast.LENGTH_SHORT).show();
                       }
                   }catch (JSONException e) {
                       e.printStackTrace();
                       Toast.makeText(getApplicationContext(), "loi" +e.toString(), Toast.LENGTH_SHORT).show();
                   }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    try {
                        jsonObject.put("email", Email);
                        jsonObject.put("password", Password);
                        jsonObject.put("phonenumber", Phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            queue.add(request);
        }
        else{
            Toast.makeText(getApplicationContext(), "Kiem tra lai du lieu nhap", Toast.LENGTH_SHORT).show();
        }
    }
    private void Anhxa() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        phonenumber = findViewById(R.id.phonenumber);
        signup = findViewById(R.id.signup);
    }
}
