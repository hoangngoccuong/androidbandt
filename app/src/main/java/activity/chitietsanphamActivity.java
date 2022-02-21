package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import modell.Cart;
import modell.Sanpham;

public class chitietsanphamActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;

    int id = 0;
    String Tenchitiet = "";
    int Giachitiet=0;
    String Hinhanhchitiet = "";
    String Motachitiet = "";
    int Idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        AnhXa();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.arrCart.size()>0){
                    int sl= Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits = false;
                    for(int i = 0; i < MainActivity.arrCart.size(); i++){
                        if(MainActivity.arrCart.get(i).getProductId() == id){
                            MainActivity.arrCart.get(i).setProductNumber(MainActivity.arrCart.get(i).getProductNumber() + sl);
                            if(MainActivity.arrCart.get(i).getProductNumber() >= 10){
                                MainActivity.arrCart.get(i).setProductNumber(10);
                            }
                            MainActivity.arrCart.get(i).setPrice(Giachitiet * MainActivity.arrCart.get(i).getProductNumber());
                            exits = true;
                        }
                    }
                    if(exits == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi=soluong*Giachitiet;
                        MainActivity.arrCart.add(new Cart(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi=soluong*Giachitiet;
                    MainActivity.arrCart.add(new Cart(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] number = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,number);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham product = (Sanpham) getIntent().getSerializableExtra("productData");
        id = product.getID();
        Tenchitiet = product.getTensanpham();
        Giachitiet = product.getGiasp();
        Hinhanhchitiet = product.getHinhanhsp();
        Motachitiet = product.getMotasp();
        Idsanpham = product.getIDSanpham();

        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : "+decimalFormat.format(Giachitiet)+" Đ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(imgChitiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChitiet= (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitiet= (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten= (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia= (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota= (TextView) findViewById(R.id.textviewmotachitietsanpham);
        spinner= (Spinner) findViewById(R.id.spinner);
        btndatmua= (Button) findViewById(R.id.buttondatmua);
    }
}
