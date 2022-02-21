package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.doan.R;

import java.util.ArrayList;

import modell.Thongke;

public class ThongkeAdapter extends BaseAdapter implements Filterable {
        Context context;
        ArrayList<Thongke> arrDonhang;
        ArrayList<Thongke> arrDonhang1;


        public ThongkeAdapter(Context context, ArrayList<Thongke> arrDonhang) {
            this.context = context;
            this.arrDonhang = arrDonhang;
            this.arrDonhang1 = arrDonhang1;
        }
    @Override
    public int getCount() {
        return arrDonhang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrDonhang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
public class ViewHoder{
    public TextView Madonhang,Masanpham,Tensanpham,Giasanpham,Soluongsanpham,Ngaydathang;
}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThongkeAdapter.ViewHoder viewHolder = null;
        if (view == null) {
            viewHolder = new ThongkeAdapter.ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_thongke, null);
            viewHolder.Madonhang = view.findViewById(R.id.txtmadonhang);
            viewHolder.Masanpham= view.findViewById(R.id.txtmasp1);
            viewHolder.Tensanpham = view.findViewById(R.id.txtten);
            viewHolder.Giasanpham = view.findViewById(R.id.txtgia);
            viewHolder.Soluongsanpham= view.findViewById(R.id.txtsoluong);
            viewHolder.Ngaydathang= view.findViewById(R.id.txtngaydathang);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ThongkeAdapter.ViewHoder) view.getTag();
        }
        Thongke thongke = arrDonhang.get(i);
        viewHolder.Madonhang.setText(thongke.getMadonhang());
        viewHolder.Masanpham.setText(Integer.toString( thongke.getMasanpham()));
        viewHolder.Tensanpham.setText(thongke.getTensanpham());
        viewHolder.Giasanpham.setText(Integer.toString(thongke.getGiasanpham()));
      viewHolder.Soluongsanpham.setText(Integer.toString(thongke.getSoluongsanpham()));
        viewHolder.Ngaydathang.setText(thongke.getNgaydathang());
        return view;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()){
                    arrDonhang = arrDonhang1;
                }else {
                    ArrayList<Thongke> list = new ArrayList<>();
                    for (Thongke thongke:arrDonhang1){
                        if (thongke.getNgaydathang().toLowerCase().contains(strsearch.toLowerCase())) {
                            list.add(thongke);
                        }
                    }
                    arrDonhang =list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =arrDonhang;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrDonhang = (ArrayList<Thongke>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
