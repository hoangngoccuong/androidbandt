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

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arrayListlt;

    public LaptopAdapter(final Context context, final ArrayList<Sanpham> arrayListlt) {
        this.context = context;
        this.arrayListlt = arrayListlt;
    }

    @Override
    public int getCount() {
        return arrayListlt.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListlt.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoder{
        public TextView txttenlt,txtgia,txtmota;
        public ImageView imagedt;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LaptopAdapter.ViewHoder viewHolder = null;
        if (view == null) {
            viewHolder = new LaptopAdapter.ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.txttenlt = view.findViewById(R.id.textviewtenlt);
            viewHolder.txtgia = view.findViewById(R.id.textviewgialt);
            viewHolder.txtmota = view.findViewById(R.id.textviewmotalt);
            viewHolder.imagedt = view.findViewById(R.id.imgviewlt);

            view.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHoder) view.getTag();
        }
        Sanpham sanpham = arrayListlt.get(i);
        //Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlt.setText(sanpham.getTensanpham());
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
