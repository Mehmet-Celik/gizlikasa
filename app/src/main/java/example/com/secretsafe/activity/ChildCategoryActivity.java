package example.com.secretsafe.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import example.com.secretsafe.R;
import example.com.secretsafe.adapters.CategoryAdapter;
import example.com.secretsafe.adapters.CategoryChildAdapter;
import example.com.secretsafe.model.ChildCategory;
import example.com.secretsafe.model.kategori;
import example.com.secretsafe.sql.SQLiteHelper;
import example.com.secretsafe.util.Aes;

import static example.com.secretsafe.model.Constant.AES_KEY;

public class ChildCategoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<ChildCategory> childList;
    kategori categoryModel;
    RecyclerView recyclerView;
    CategoryChildAdapter categoryChildAdapter;
    EditText childDialogText, kullaniciMail, kullaniciAdi, sifre;
    SQLiteHelper sqlHelper;
    Aes aes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_category);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aes = new Aes();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogWindow();
            }
        });


        recyclerView = findViewById(R.id.recyclerView_child);
        categoryChildAdapter = new CategoryChildAdapter(this);

        init();


    }

    private void init() {
        sqlHelper = new SQLiteHelper(getApplicationContext());


        if (getIntent().hasExtra("categoryModel")) {
            categoryModel = (kategori) getIntent().getSerializableExtra("categoryModel");
            toolbar.setTitle(categoryModel.getKategoriAdi());

            childList = sqlHelper.SifreGetir(categoryModel.getKategoriId());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        categoryChildAdapter = new CategoryChildAdapter(this);
        recyclerView.setAdapter(categoryChildAdapter);
        categoryChildAdapter.setCategoryChildList(childList);

    }


    public void showDialogWindow() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(ChildCategoryActivity.this);
        adBuilder.setMessage("Lütfen uygulama adı giriniz... ");
        final Context context = adBuilder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_child, null, false);
        adBuilder.setView(view);
        adBuilder.setPositiveButton(
                "Ekle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        childDialogText = view.findViewById(R.id.childCategoryTitle);
                        kullaniciMail = view.findViewById(R.id.kullaniciMail);
                        kullaniciAdi = view.findViewById(R.id.kullaniciAdi);
                        sifre = view.findViewById(R.id.sifre);


                        if (childDialogText.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Lütfen uygulama adını giriniz...", Toast.LENGTH_SHORT).show();
                        } else {
                            ChildCategory childCategory = new ChildCategory();
                            childCategory.setParentId(categoryModel.getKategoriId());


                            //şifreyi Aes li encrypt et şifrele öyle yolla
                            byte[] key = AES_KEY.getBytes();
                            String sifreText = String.valueOf(sifre.getText());
                            byte[] value = sifreText.getBytes();
                            byte[] encrypted;
                            String str = "hello";
                            //byte[] decrypted ;

                            try {
                                encrypted = aes.encrypt(key,value);
                                str = new String(encrypted, "UTF-8");
                                //decrypted = aes.decrypt(key,encrypted);
                                //String str1 = new String(decrypted, "UTF-8");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            childCategory.setSifre(String.valueOf(sifre.getText()));
                            childCategory.setKullaniciAdi(String.valueOf(kullaniciAdi.getText()));
                            childCategory.setKullaniciMail(String.valueOf(kullaniciMail.getText()));
                            childCategory.setChildCategoryText(String.valueOf(childDialogText.getText()));


                            int childId = sqlHelper.childKaydet(childCategory);
                            childCategory.setId(childId);
                            childList.add(childCategory);
                            categoryChildAdapter.setCategoryChildList(childList);
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
