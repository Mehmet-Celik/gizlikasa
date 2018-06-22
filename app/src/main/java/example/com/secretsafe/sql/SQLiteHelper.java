package example.com.secretsafe.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import example.com.secretsafe.model.ChildCategory;
import example.com.secretsafe.model.kategori;
import example.com.secretsafe.util.sifre;

/**
 * Created by kubra on 29.4.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String database_name = "secretDB";
    private static final String table_kategori = "kategoriler";
    private static final String kategori_id = "kategoriId";
    private static final String kategori_adi = "kategoriAdi";
    private static final int database_version = 1;
    private static final String[] Columns = {kategori_id, kategori_adi};


    private static final String create_kategori_table = "CREATE TABLE "
            + table_kategori + "("
            + kategori_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + kategori_adi + " TEXT )";


    private static final String table_sifre = "Sifreler";
    private static final String sifre_id = "sifreId";
    private static final String site_adi = "siteAdi";
    private static final String kullanici_adi = "skullaniciAdi";
    private static final String kayitli_mail = "smail";
    private static final String sifresi = "ssifre";
    private static final String sifre_kategori_id = "skategoriId";

    private static final String[] Columnssifre = {sifre_id, site_adi, kullanici_adi, kayitli_mail, sifresi, sifre_kategori_id};

    private static final String create_sifre_table = "CREATE TABLE "
            + table_sifre + "("
            + sifre_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + site_adi + " TEXT,"
            + kayitli_mail + " TEXT,"
            + kullanici_adi + " TEXT,"
            + sifresi + " TEXT,"
            + sifre_kategori_id + " INTEGER )";


    public SQLiteHelper(Context context) {
        //version 1 olursa oncreate çalışıyor 2 olursa upgrade
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_kategori_table);
        db.execSQL(create_sifre_table);
    }

    public int childKaydet(ChildCategory s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Content value cv'nin değerleri kategori.java kısmındanki propertie kullanımı ile ilgili kısımlara aktarılıyor.
        ContentValues cv = new ContentValues();

        cv.put(site_adi, s1.getChildCategoryText());
        cv.put(kayitli_mail, s1.getKullaniciMail());
        cv.put(kullanici_adi, s1.getKullaniciAdi());
        cv.put(sifresi, s1.getSifre());
        cv.put(sifre_kategori_id, s1.getParentId());

        //insert() geri dönüş tipi long tur.Geriye döndürdüğü değer o satırın id değeridir.
        //eğer hata oluşuyorsa id değeri -1 döner.hata oluşup oluşmadığını bir değişken belirleyek değerinden anlayabiliriz.

        int kontrol = (int) db.insert(table_sifre, null, cv);

        //Son olarak veritabanını kapatmalıyız.
        db.close();

        return kontrol;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_kategori);
        db.execSQL("DROP TABLE IF EXISTS " + table_sifre);
        this.onCreate(db);
    }

    public List<ChildCategory> SifreGetir(int parentId) {
        List<ChildCategory> sifreler = new ArrayList<>();
        String query = "SELECT * FROM " + table_sifre + " where " + sifre_kategori_id + " = " + parentId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ChildCategory child = null;
        if (cursor.moveToFirst()) {
            do {
                child = new ChildCategory();
                child.setId(Integer.parseInt(cursor.getString(0)));
                child.setChildCategoryText(cursor.getString(1));
                child.setKullaniciMail(cursor.getString(2));
                child.setKullaniciAdi(cursor.getString(3));
                child.setSifre(cursor.getString(4));
                child.setParentId(cursor.getInt(5));
                sifreler.add(child);
            } while (cursor.moveToNext());
        }
        return sifreler;
    }

    /*public void kategoriEkle(kategori kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(kategori_adi,kategori.getKategoriAdi());
        db.insert(table_kategori,null,degerler);
        db.close();
    }*/

    public int KategoriKayit(kategori k1) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Content value cv'nin değerleri kategori.java kısmındanki propertie kullanımı ile ilgili kısımlara aktarılıyor.
        ContentValues cv = new ContentValues();
        cv.put(kategori_adi, k1.getKategoriAdi());

        //insert() geri dönüş tipi long tur.Geriye döndürdüğü değer o satırın id değeridir.
        //eğer hata oluşuyorsa id değeri -1 döner.hata oluşup oluşmadığını bir değişken belirleyek değerinden anlayabiliriz.

        long kontrol = db.insert(table_kategori, null, cv);

        //long kontrol=db.insertOrThrow(TABLE_NAME,"",cv); olabilirdi...

        //Son olarak veritabanını kapatmalıyız.
        db.close();

        return (int) kontrol;

    }


    public List<kategori> KategoriGetir() {
        List<kategori> kategoriler = new ArrayList<>();
        String query = "SELECT * FROM " + table_kategori;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        kategori kategori = null;
        if (cursor.moveToFirst()) {
            do {
                kategori = new kategori();
                kategori.setKategoriId(Integer.parseInt(cursor.getString(0)));
                kategori.setKategoriAdi(cursor.getString(1));
                kategoriler.add(kategori);
            } while (cursor.moveToNext());
        }
        return kategoriler;
    }

    public List<sifre> SifreGetir() {
        List<sifre> sifreler = new ArrayList<>();
        String query = "SELECT * FROM " + table_sifre;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        sifre sifre = null;
        if (cursor.moveToFirst()) {
            do {
                sifre = new sifre();
                sifre.setSifreId(Integer.parseInt(cursor.getString(0)));
                sifre.setSiteAdi(cursor.getString(1));
                sifre.setSmail(cursor.getString(2));
                sifre.setSkullaniciAdi(cursor.getString(3));
                sifre.setSsifre(cursor.getString(4));
                sifre.setSkategori(cursor.getString(5));
                sifreler.add(sifre);
            } while (cursor.moveToNext());
        }
        return sifreler;
    }

    public ChildCategory sifreDetayGetir(int childId) {
        String query = "SELECT * FROM " + table_sifre + " WHERE " + sifre_id + " = " + childId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ChildCategory childCategory = null;
        if (cursor.moveToFirst()) {
            childCategory = new ChildCategory();
            childCategory.setId(Integer.parseInt(cursor.getString(0)));
            childCategory.setChildCategoryText(cursor.getString(1));
            childCategory.setKullaniciMail(cursor.getString(2));
            childCategory.setKullaniciAdi(cursor.getString(3));
            childCategory.setSifre(cursor.getString(4));
            childCategory.setParentId(cursor.getInt(5));
        }
        return childCategory;
    }

    public boolean sifreSil(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(table_sifre, sifre_id + "=" + id, null) > 0;
    }


    public kategori kategorioku(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_kategori, Columns, " kategoriId= ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        kategori kategori = new kategori();
        kategori.setKategoriId(Integer.parseInt(cursor.getString(0)));
        kategori.setKategoriAdi(cursor.getString(1));

        return kategori;
    }


    public void kategoriSil(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean a = db.delete(table_kategori, kategori_id + "=" + id, null) > 0;
    }
}
