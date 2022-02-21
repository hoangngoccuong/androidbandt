package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import activity.CartActivity;
import activity.MainActivity;
import modell.Cart;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrCart;

    public CartAdapter(Context context, ArrayList<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtcartname,txtcartcost;
        public ImageView imgcart;
        public Button btnminus,btnvalues,btnplus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_cart,null);
            viewHolder.txtcartname = (TextView) convertView.findViewById(R.id.textviewcartname);
            viewHolder.txtcartcost = (TextView) convertView.findViewById(R.id.textviewcartcost);
            viewHolder.imgcart = (ImageView) convertView.findViewById(R.id.imageviewcart);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button) convertView.findViewById(R.id.buttonvalue);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Cart cart= (Cart) getItem(position);
        viewHolder.txtcartname.setText(cart.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtcartcost.setText(decimalFormat.format(cart.getPrice()) + " Đ");
        Picasso.get().load(cart.getProductImage())
                .error(R.drawable.loi)
                .into(viewHolder.imgcart);
        viewHolder.btnvalues.setText(cart.getProductNumber()+"");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl>=10) {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }else if(sl<=1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        else if(sl>1) {
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newsl = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) +1;
                int cursl = (int) MainActivity.arrCart.get(position).getProductNumber();
                long giaht = MainActivity.arrCart.get(position).getPrice();
                MainActivity.arrCart.get(position).setProductNumber(newsl);
                long newcash = (giaht*newsl)/cursl;
                MainActivity.arrCart.get(position).setPrice(newcash);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartcost.setText(decimalFormat.format(newcash) + " Đ");
                CartActivity.EvenUtils();
                if(newsl>9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(newsl));
                }
                else
                {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(newsl));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newsl = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) -1;
                int cursl = (int) MainActivity.arrCart.get(position).getProductNumber();
                long giaht = MainActivity.arrCart.get(position).getPrice();
                MainActivity.arrCart.get(position).setProductNumber(newsl);
                long newcash = (giaht*newsl)/cursl;
                MainActivity.arrCart.get(position).setPrice(newcash);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartcost.setText(decimalFormat.format(newcash) + " Đ");
                CartActivity.EvenUtils();
                if(newsl<2){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(newsl));
                }
                else
                {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(newsl));
                }
            }
        });
        return convertView;
    }
}
