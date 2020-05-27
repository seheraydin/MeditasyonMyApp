package com.example.meditasyonmyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "meditasyonlar";
    private static final String TABLE_NAME = "veriler";

    private static final String KEY_ID = "id";
    private static final String KEY_BASLIK = "baslik";
    private static final String KEY_ACIKLAMA = "aciklama";
    private static final String KEY_RESIM = "resim";
    private static final String KEY_SES = "ses";
    private static final String KEY_FAVORI = "favori";
    private static final String KEY_TARIH = "tarih";
    private static final String KEY_KATEGORI = "kategori";
    private static final String KEY_ANAHTAR = "anahtar";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_BASLIK + " TEXT," + KEY_ACIKLAMA + " TEXT, "
                + KEY_RESIM + " TEXT UNIQUE, " + KEY_SES + " TEXT UNIQUE, "
                + KEY_FAVORI + " INTEGER DEFAULT 0," + KEY_TARIH + " TEXT,"
                + KEY_KATEGORI + " INTEGER DEFAULT 0, " + KEY_ANAHTAR + " INTEGER DEFAULT 0)";

        sqLiteDatabase.execSQL(CREATE_TABLE);

        Log.d("Veritabanı kuruldu", "Tablo oluşturuldu");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    public void veriEkle(String baslik, String aciklama, String resim, String ses, String tarih, String kategori, String anahtar){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(KEY_BASLIK, baslik);
        values.put(KEY_ACIKLAMA, aciklama);
        values.put(KEY_RESIM, resim);
        values.put(KEY_SES, ses);
        values.put(KEY_TARIH, tarih);
        values.put(KEY_KATEGORI, kategori);
        values.put(KEY_ANAHTAR, anahtar);

        long id =db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d("Tabloya satır eklendi: ", String.valueOf(id));


    }
    public void favoriDurumu(String id, String durum)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAVORI, durum);

        db.update(TABLE_NAME, values, "id= "+ id, null);
        db.close();

        Log.d(TAG, "Favori durumu güncellendi");
    }
    public void verileriSil(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
        Log.d(TAG, "Bütün veriler silindi");
    }

}
