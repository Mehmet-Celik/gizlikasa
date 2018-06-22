package example.com.secretsafe.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.com.secretsafe.R;
import example.com.secretsafe.model.ChildCategory;
import example.com.secretsafe.model.kategori;
import example.com.secretsafe.sql.SQLiteHelper;
import example.com.secretsafe.util.Aes;

import static example.com.secretsafe.model.Constant.AES_KEY;

public class ChildDetailActivity extends AppCompatActivity {
    TextView kullaniciMail, kullaniciAdi, kategorisi, sifre, txtsecuritySoru, kategori;
    ChildCategory childCategory;
    kategori kategory;
    Button kayıtSil;
    SQLiteHelper sqLiteHelper;
    Button viewSifre;
    EditText editSecurityCevap;
    String cevap,soru;
    Aes aes;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_detail);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        aes = new Aes();

        if (getIntent().hasExtra("childCategory")) {
            childCategory = (ChildCategory) getIntent().getSerializableExtra("childCategory");
            kategory = sqLiteHelper.kategorioku(childCategory.getParentId());
        }


        kullaniciAdi = findViewById(R.id.txtKullaniciAdi);
        kullaniciMail = findViewById(R.id.txtKullaniciMail);
        kategorisi = findViewById(R.id.txtKategorisi);
        kategori = findViewById(R.id.txtKategori);
        sifre = findViewById(R.id.txtSifre);
        kayıtSil = findViewById(R.id.silButton);
        viewSifre = findViewById(R.id.viewSifre);

        kullaniciMail.setText(childCategory.getKullaniciMail());
        kullaniciAdi.setText(childCategory.getKullaniciAdi());
        kategori.setText(kategory.getKategoriAdi());
        kategorisi.setText(childCategory.getChildCategoryText());




        kayıtSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isRemoved = sqLiteHelper.sifreSil(childCategory.getId());
                if (isRemoved) {
                    Toast.makeText(getApplicationContext(), "Başarı ile silme gerçekleştirildi.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ChildCategoryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });

        viewSifre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSecurityPassword();
            }
        });
    }

    public void showSecurityPassword() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(ChildDetailActivity.this);
        adBuilder.setMessage("Lütfen bir güvenlik şifresi giriniz ");
        final Context context = adBuilder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.security_cevap, null, false);
        adBuilder.setView(view);
         soru = preferences.getString("securityPassword","Güvenlik sorusu girilmedi!!");
         cevap = preferences.getString("securityAnswer","Cevap girilmedi!!");

        editSecurityCevap = view.findViewById(R.id.editSecurityCevap);
        txtsecuritySoru = view.findViewById(R.id.txtsecuritySoru);


        txtsecuritySoru.setText(soru);
        adBuilder.setPositiveButton(
                "Kaydet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                if(cevap.equals(String.valueOf(editSecurityCevap.getText()))){
                    //Fonksiyona gönder ve şifreyi çöz
                    //Buraya aes'i çözdükten sonra ki şifreyi bas
//9O-�5Y��Zz�3��	��
//9O-�5Y��Zz�3��	��
                    byte[] key = AES_KEY.getBytes();
                    String encrepytedText = childCategory.getSifre();
                    byte[] encreptyed = encrepytedText.getBytes();
                    byte[] decrypted ;
                    String str1 = "hello";

                    try {

                        decrypted = aes.decrypt(key,encreptyed);
                        str1 = new String(decrypted, "UTF-8");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    sifre.setText(childCategory.getSifre());
                }


                    if(editSecurityCevap.getText().toString().equals("") ){
                        Toast.makeText(getApplicationContext(), "Lütfen güvenlik sorusunu boş bırakmayınız...", Toast.LENGTH_SHORT).show();
                    }



                    }
                });

        AlertDialog alert11 = adBuilder.create();
        alert11.show();
    }



}
