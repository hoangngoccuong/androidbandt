package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import modell.Sanpham;

public class DienthoaiAdapter extends BaseAdapter {

    Context context;
     ArrayList<Sanpham> arrayListdt;

    public DienthoaiAdapter(final Context context, final ArrayList<Sanpham> arrayListdt) {
        this.context = context;
        this.arrayListdt = arrayListdt;
    }

    @Override
    public int getCount() {
        return arrayListdt.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListdt.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
     public class ViewHoder{
         public TextView txttendienthoai,txtgia,txtmota;
         public ImageView imagedt;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.txttendienthoai = view.findViewById(R.id.textviewten);
            viewHolder.txtgia = view.findViewById(R.id.textviewgia);
            viewHolder.txtmota = view.findViewById(R.id.textviewmota);
            viewHolder.imagedt = view.findViewById(R.id.imgview);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoder) view.getTag();
        }
        Sanpham sanpham = arrayListdt.get(i);
        //Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());
       // DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("Gia :"+sanpham.getGiasp()+"Đ");
        viewHolder.txtmota.setText(sanpham.getMotasp());
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imagedt);
        return view;
    }
}
