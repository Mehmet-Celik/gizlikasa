package example.com.secretsafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import example.com.secretsafe.activity.CategoryActivity;
import example.com.secretsafe.activity.FingerPrintActivity;
import example.com.secretsafe.activity.KlasikKayitActivity;
import example.com.secretsafe.sql.Veritabani;

public class MainActivity extends AppCompatActivity {

    EditText et_ad, et_sifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_ad = (EditText) findViewById(R.id.et_ad);
        et_sifre = (EditText) findViewById(R.id.et_sifre);


   /*     final EditText et = (EditText) findViewById(R.id.et_ad);;
        String isim;
        ((Button) findViewById(R.id.btn_gir)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SecimActivity.class);
                i.putExtra("mesaj",et.getText().toString());
                startActivity(i);
            }
        });*/


    }


    public void butonaDokunuldu(View v) {

        //Kullanıcı adı ve parola alınıyor.
        String kullaniciadi = et_ad.getText().toString();
        String sifresi = et_sifre.getText().toString();

        //Buton olayları tanımlanıyor.
        switch (v.getId()) {
            case R.id.btn_gir:

                if (sifresi.isEmpty() || kullaniciadi.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Alanlar boş geçilemez!!!", Toast.LENGTH_SHORT).show();
                }

                Veritabani db = new Veritabani(this);
//Sadece kullanıcı adını alıyor ve bu kullanıcının şifresini kontrol ediyor.Sonuca göre mesaj verdiriyor.

                String kontrol = db.KaydiKontrolEt(kullaniciadi);


                if (sifresi.equals(kontrol)) {
                    Toast.makeText(MainActivity.this, "Giriş yapıldı...", Toast.LENGTH_SHORT).show();

                    //Şifre doğruysa "Hoşgeldiniz" Intentine geçiliyor.
                    Intent girisekrani = new Intent(getApplicationContext(), FingerPrintActivity.class);
                    girisekrani.putExtra("mesaj", et_ad.getText().toString());
                    startActivity(girisekrani);
                } else {
                    Toast.makeText(MainActivity.this, "Hatalı kullanıcı adı veya şifre!!!", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.btn_kayit:

                //Kayıt işlemi için kayıt ol Intentine geçiş yapılıyor.
                Intent intent = new Intent(getApplicationContext(), KlasikKayitActivity.class);
                startActivity(intent);

            case R.id.btn_temizle:

                //Temizleme butonu için tanımlama yapılıyor.
                et_ad.getText().clear();
                et_sifre.getText().clear();

                break;

        }
    }
}
