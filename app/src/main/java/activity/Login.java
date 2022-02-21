package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.R;

import org.json.JSONException;
import org.json.JSONObject;

import ultil.sever;

public class Login extends AppCompatActivity {

    EditText name;
    EditText password;
    TextView signin;
    Button login;
    static String Email;
    static String Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), password.getText().toString());
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Dangki.class);
                startActivity(intent);
            }
        });
    }

    private void validate(final String usernames, String userpassword) {
        Log.d("user", usernames);
        Log.d("pass", userpassword);
        //final String Email = email.getText().toString().trim();
        if (usernames.length()>0 && userpassword.length()>0) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String link = sever.login + "?username=" + usernames + "&&password=" + userpassword;
            Log.d("s", link);
            StringRequest request = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Chào bạn đến với shop chúng tôi", Toast.LENGTH_LONG).show();
                            MainActivity.islogin = true;
                            MainActivity.username = usernames.toString();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bạn hãy đăng kí", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "loi" +e.toString(), Toast.LENGTH_SHORT).show();
                    }
//
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
        }
    }
}
