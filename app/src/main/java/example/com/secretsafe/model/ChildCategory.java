package example.com.secretsafe.model;

import java.io.Serializable;

public class ChildCategory implements Serializable{

    int id;
    String childCategoryText;
    String kullaniciMail;
    String kullaniciAdi;
    String sifre;
    int parentId;

    public String getKullaniciMail() {
        return kullaniciMail;
    }

    public void setKullaniciMail(String kullaniciMail) {
        this.kullaniciMail = kullaniciMail;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChildCategoryText() {
        return childCategoryText;
    }

    public void setChildCategoryText(String childCategoryText) {
        this.childCategoryText = childCategoryText;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
