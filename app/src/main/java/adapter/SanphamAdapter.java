package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import activity.chitietsanphamActivity;
import modell.Sanpham;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> implements Filterable {
    Context context;
    ArrayList<Sanpham> arrProduct;
    ArrayList<Sanpham> arrProductold;


    public SanphamAdapter(Context context, ArrayList<Sanpham> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        this.arrProductold = arrProduct;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Sanpham product = arrProduct.get(position);
        holder.ProductName.setText(product.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.ProductPrice.setText("Giá "+ decimalFormat.format(product.getGiasp())+"Đ");
        Picasso.get().load(product.getHinhanhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(holder.ProductImage);
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }



    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView ProductImage;
        public TextView ProductName,ProductPrice;

        public ItemHolder(View itemView) {
            super(itemView);
            ProductImage = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            ProductName = (TextView) itemView.findViewById(R.id.txttensanpham);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, chitietsanphamActivity.class);
                    intent.putExtra("productData",arrProduct.get(getPosition()));
                   //ShowToast_Short(context,arrProduct.get(getPosition()).getProductName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()){
                    arrProduct = arrProductold;
                }else {
                    ArrayList<Sanpham> list = new ArrayList<>();
                    for (Sanpham sanpham:arrProductold){
                        if (sanpham.getTensanpham().toLowerCase().contains(strsearch.toLowerCase())) {
                            list.add(sanpham);
                        }
                    }
                    arrProduct =list;
                }
                FilterResults filterResults = new FilterResults();
                    filterResults.values =arrProduct;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrProduct = (ArrayList<Sanpham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
