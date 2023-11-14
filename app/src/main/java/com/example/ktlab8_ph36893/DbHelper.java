package com.example.ktlab8_ph36893;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String Db_name = "DSMH";
    public DbHelper(Context context) {
        super(context, Db_name, null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String tb_monhoc = "CREATE TABLE MONHOC(mamh text primary key, tenmh text, sotiet interger)";
            db.execSQL(tb_monhoc);

            String data = "insert into monhoc values('mh01','Toán','20')," + "('mh02','Văn','50')";
            db.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!= newVersion){
            db.execSQL("drop table if exists monhoc");
            onCreate(db);
        }

    }
}
