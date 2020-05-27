package com.example.meditasyonmyapp;

public class Meditasyon {
    private String id;
    private String baslik;
    private String aciklama;
    private String resim;
    private String ses;
    private String favori;
    private String tarih;
    private String kategori;

    //getter oluşturalım
    public String getId()
    {
        return id;
    }
    public String getBaslik()
    {
        return baslik;
    }
    public String getAciklama()
    {
        return aciklama;
    }
    public String getResim()
    {
        return resim;
    }
    public String getSes()
    {
        return ses;
    }
    public String getFavori()
    {
        return favori;
    }
    public String getTarih()
    {
        return tarih;
    }
    public String getKategori()
    {
        return kategori;
    }
    public Meditasyon (String m_id, String m_baslik, String m_aciklama, String m_resim, String m_Ses, String m_favori, String m_tarih, String m_kategori )
    {
        this.id=m_id;
        this.baslik=m_baslik;
        this.aciklama=m_aciklama;
        this.resim=m_resim;
        this.ses=m_Ses;
        this.favori=m_favori;
        this.tarih=m_tarih;
        this.kategori=m_kategori;
    }

}
