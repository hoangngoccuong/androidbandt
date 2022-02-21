package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan.R;

import java.text.DecimalFormat;

import adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {
    ListView lvcart;
    TextView txtNotify;
    static TextView txtTotalCash;
    Button btnPay,btnContinueBuy;
    Toolbar toolbarcart;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        ActionToolbar();
        CheckData();
        EvenUtils();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnContinueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrCart.size() > 0) {
                    if (MainActivity.islogin) {
                        Intent intent = new Intent(getApplicationContext(), Thongtinnguoidung.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Xin hãy đăng nhập để được trải ngiệm tốt nhất", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Thongtinnguoidung.class);
                        startActivity(intent);
                    }
               } else {
                    Toast.makeText(getApplicationContext(), "Gio hàng trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Ban co muon xoa san pham nay");
                builder.setMessage("Ban co chac muon xoa san pham nay");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.arrCart.size()<=0)
                        {
                            txtNotify.setVisibility(View.VISIBLE);
                        }
                        else {
                            MainActivity.arrCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EvenUtils();
                            if(MainActivity.arrCart.size()<=0){
                                txtNotify.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtNotify.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EvenUtils();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EvenUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUtils(){
        long totalcash = 0;
        for(int i=0;i<MainActivity.arrCart.size();i++)
        {
            totalcash += MainActivity.arrCart.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalCash.setText(decimalFormat.format(totalcash)+"D");
    }
    private void CheckData() {
        if(MainActivity.arrCart.size()<=0)
        {
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.VISIBLE);
            lvcart.setVisibility(View.INVISIBLE);
        }else {
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.INVISIBLE);
            lvcart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvcart = (ListView) findViewById(R.id.listviewcart);
        txtNotify = findViewById(R.id.textviewnotify);
        txtTotalCash = findViewById(R.id.textviewtotalcash);
        btnPay = findViewById(R.id.buttonpaycart);
        btnContinueBuy = findViewById(R.id.buttoncontinueshopping);
        toolbarcart = findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(CartActivity.this,MainActivity.arrCart);
        lvcart.setAdapter(cartAdapter);
    }
}
