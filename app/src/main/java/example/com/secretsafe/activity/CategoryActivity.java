package example.com.secretsafe.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.secretsafe.R;
import example.com.secretsafe.adapters.CategoryAdapter;
import example.com.secretsafe.sql.SQLiteHelper;
import example.com.secretsafe.model.kategori;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    FloatingActionButton fab;
    List<kategori> categoryModelList;
    EditText categoryEditText;
    SQLiteHelper sqlHelper;
    SharedPreferences preferences;
    EditText securityPassword,securityAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isSaved = preferences.getBoolean("isSaved", false);
        if (!isSaved) {
            showSecurityPassword();
        }
        getComponents();
        bindEvents();
        init();

    }


    private void getComponents() {
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        categoryModelList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter(this);
        recyclerView.setAdapter(categoryAdapter);
    }

    private void init() {
        sqlHelper = new SQLiteHelper(getApplicationContext());
        categoryModelList = sqlHelper.KategoriGetir();
        //sayfa ilk açıldığında verinin dolduğu yer
        categoryAdapter.setCategoryList(categoryModelList);


    }

    private void bindEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogWindow();
            }
        });



    }

    public void showSecurityPassword() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(CategoryActivity.this);
        adBuilder.setMessage("Lütfen bir güvenlik şifresi giriniz ");
        final Context context = adBuilder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.security_layout, null, false);
        adBuilder.setView(view);
        adBuilder.setCancelable(false);
        adBuilder.setPositiveButton(
                "Kaydet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        securityPassword = view.findViewById(R.id.securityPassword);
                        securityAnswer = view.findViewById(R.id.securityAnswer);

                        if (securityPassword.getText().toString().equals("") || securityAnswer.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Lütfen boş alanları doldurunuz...", Toast.LENGTH_SHORT).show();
                            showSecurityPassword();
                        } else {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("securityPassword", securityPassword.getText().toString());
                            editor.putString("securityAnswer", securityAnswer.getText().toString());
                            editor.putBoolean("isSaved", true);

                            editor.apply();
                        }
                    }
                });

        AlertDialog alert11 = adBuilder.create();
        alert11.show();
    }


    public void showDialogWindow() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(CategoryActivity.this);
        adBuilder.setMessage("Lütfen bir uygulama adı giriniz... ");
        final Context context = adBuilder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_task, null, false);
        adBuilder.setView(view);
        adBuilder.setPositiveButton(
                "Ekle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        categoryEditText = view.findViewById(R.id.categoryTitle);


                        if (categoryEditText.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Lütfen uygulama adını giriniz...", Toast.LENGTH_SHORT).show();
                        } else {
                            kategori categoryModel = new kategori();


                            categoryModel.setKategoriAdi(String.valueOf(categoryEditText.getText()));
                            //db eklendiği kısım
                            int categoryId = sqlHelper.KategoriKayit(categoryModel);
                            categoryModel.setKategoriId(categoryId);
                            categoryModelList.add(categoryModel);
                            categoryAdapter.setCategoryList(categoryModelList);
                            dialog.dismiss();
                        }
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
    }


}
