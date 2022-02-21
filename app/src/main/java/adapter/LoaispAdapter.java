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

import modell.Loaisp;

public class LoaispAdapter extends BaseAdapter {
    private ArrayList<Loaisp> Listloaisp;
    Context context;


    public LoaispAdapter(final ArrayList<Loaisp> listloaisp, final Context context) {
        this.Listloaisp = listloaisp;
        this.context = context;
    }
//    public LoaispAdapter(MainActivity mainActivity, int dong_listview_loaisp, List<Loaisp> arrayList) {
//    }

    @Override
    public int getCount() {
        return Listloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView txttenloaisp;
        ImageView imgloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp, null);
            viewHolder.txttenloaisp = view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Loaisp loaisp = Listloaisp.get(i);
        //Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.Tenloaisp);
        Picasso.get().load(loaisp.Hinhanhloaisp)
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgloaisp);
        return view;
    }
}