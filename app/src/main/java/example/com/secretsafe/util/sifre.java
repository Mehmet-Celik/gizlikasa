package example.com.secretsafe.util;

/**
 * Created by kubra on 28.4.2018.
 */

public class sifre {
    int sifreId;
    String siteAdi;
    String smail;
    String skullaniciAdi;
    String ssifre;
    String skategori;


    public sifre() {
    }

    public sifre(String siteAdi, String smail, String skullaniciAdi, String ssifre, String skategori) {
        this.siteAdi = siteAdi;
        this.smail = smail;
        this.skullaniciAdi = skullaniciAdi;
        this.ssifre = ssifre;
        this.skategori = skategori;
    }

    public int getSifreId() {
        return sifreId;
    }

    public void setSifreId(int sifreId) {
        this.sifreId = sifreId;
    }

    public String getSiteAdi() {
        return siteAdi;
    }

    public void setSiteAdi(String siteAdi) {
        this.siteAdi = siteAdi;
    }

    public String getSmail() {
        return smail;
    }

    public void setSmail(String smail) {
        this.smail = smail;
    }

    public String getSkullaniciAdi() {
        return skullaniciAdi;
    }

    public void setSkullaniciAdi(String skullaniciAdi) {
        this.skullaniciAdi = skullaniciAdi;
    }

    public String getSsifre() {
        return ssifre;
    }

    public void setSsifre(String ssifre) {
        this.ssifre = ssifre;
    }

    public String getSkategori() {
        return skategori;
    }

    public void setSkategori(String skategori) {
        this.skategori = skategori;
    }


    public String toString() {
        return "" + sifreId + "-" + siteAdi + "-" + smail + "-" + skullaniciAdi + "-" + ssifre + "-" + skategori;
    }
}
