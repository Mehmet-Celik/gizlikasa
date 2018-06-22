package example.com.secretsafe.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import example.com.secretsafe.R;
import example.com.secretsafe.activity.CategoryActivity;
import example.com.secretsafe.activity.ChildCategoryActivity;
import example.com.secretsafe.activity.ChildDetailActivity;
import example.com.secretsafe.model.ChildCategory;
import example.com.secretsafe.model.kategori;

public class CategoryChildAdapter extends RecyclerView.Adapter<CategoryChildAdapter.MyViewHolder> {
    Activity activity;
    List<ChildCategory> childList;

    public CategoryChildAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setCategoryChildList(List<ChildCategory> childList) {
        this.childList = childList;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_child_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ChildCategory childCategory = childList.get(position);
        holder.categoryName.setText(childCategory.getChildCategoryText());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //guvenlik kontrolden boolean çekilip true yerine yaılcak
                // boolean guvenlikControl = showDialogWindow();
                if (true) {
                    Intent intent = new Intent(activity, ChildDetailActivity.class);
                    intent.putExtra("childCategory",  childCategory);
                    activity.startActivity(intent);
                    return;
                }

                Toast.makeText(activity.getApplicationContext(), "Yanlış cevapladınız", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return (childList != null && !childList.isEmpty() ? childList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    public boolean showDialogWindow() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(activity);
        adBuilder.setMessage("Lütfen bir uygulama adı giriniz... ");
        final Context context = adBuilder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_task, null, false);
        adBuilder.setView(view);
        adBuilder.setPositiveButton(
                "Ekle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        adBuilder.setNegativeButton(
                "Vazgeç",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = adBuilder.create();
        alert11.show();
        return true;
    }
}
