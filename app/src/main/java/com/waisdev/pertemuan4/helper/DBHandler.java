package com.waisdev.pertemuan4.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waisdev.pertemuan4.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_siswa";
    public static final String TABLE_SISWA = "table_SISWA";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_TEMPATLAHIR = "tempat_lahir";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SISWA_TABLE =
                "CREATE TABLE " + TABLE_SISWA + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_NAMA + " TEXT,"
                        + COLUMN_TEMPATLAHIR + " TEXT"
                        + ")";
        db.execSQL(CREATE_SISWA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SISWA);
        onCreate(db);
    }

    public long insertSiswa(String name, String tempat_lahir){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, name);
        values.put(COLUMN_TEMPATLAHIR, tempat_lahir);
        long id = db.insert(TABLE_SISWA, null, values);

        db.close();

        return id;
    }

    public List<Siswa> getSemuaSiswa(){
        List<Siswa> siswaList = new ArrayList<>();
        String selectQuery = " SELECT * FROM "+TABLE_SISWA;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Siswa siswa = new Siswa(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TEMPATLAHIR))
                );
                siswaList.add(siswa);
            }while (cursor.moveToNext());
        }
        return siswaList;
    }
    //hapus 1 siswa
    public void hapusDataSiswa(Siswa siswa){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_SISWA, COLUMN_ID + " = ? ", new String[]{String.valueOf(siswa.getId())});
        database.close();
    }
    //get 1 siswa
    public Siswa getSiswa(int id_siswa){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_SISWA, new String[]{COLUMN_ID, COLUMN_NAMA, COLUMN_TEMPATLAHIR}, COLUMN_ID + "=?" ,
                new String[]{String.valueOf(id_siswa)}, null, null,null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Siswa siswa = new Siswa(
          cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
          cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)),
          cursor.getString(cursor.getColumnIndex(COLUMN_TEMPATLAHIR))
        );
        return siswa;
    }

    public int updateSiswa(Siswa siswa){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, siswa.getNama());
        values.put(COLUMN_TEMPATLAHIR, siswa.getTempat_lahir());
        return database.update(TABLE_SISWA, values, COLUMN_ID + "=?", new String[]{String.valueOf(siswa.getId())});
    }
}
