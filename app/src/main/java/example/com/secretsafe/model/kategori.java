package example.com.secretsafe.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kubra on 28.4.2018.
 */

public class kategori implements Serializable {
    int kategoriId;
    String kategoriAdi;

    public kategori() {
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public kategori(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public String toString() {
        return "" + kategoriId + "-" + kategoriAdi;
    }
}
