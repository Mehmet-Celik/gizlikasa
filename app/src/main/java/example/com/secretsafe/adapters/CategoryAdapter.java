package example.com.secretsafe.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import example.com.secretsafe.R;
import example.com.secretsafe.activity.ChildCategoryActivity;
import example.com.secretsafe.model.kategori;
import example.com.secretsafe.sql.SQLiteHelper;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<kategori> categoryList;
    Activity activity;
    SQLiteHelper sqLiteHelper;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new MyViewHolder(view);
    }

    public CategoryAdapter(Activity activity) {
        this.activity = activity;
        sqLiteHelper = new SQLiteHelper(activity);

    }

    public void setCategoryList(List<kategori> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final kategori categoryModel = categoryList.get(position);
        holder.categoryName.setText(categoryModel.getKategoriAdi());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChildCategoryActivity.class);
                intent.putExtra("categoryModel", categoryModel);
                activity.startActivity(intent);
            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.kategoriSil(categoryModel.getKategoriId());
                categoryList.remove(categoryModel);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (categoryList != null && !categoryList.isEmpty()) ? categoryList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CardView cardView;
        ImageView deleteImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
        }
    }
}
