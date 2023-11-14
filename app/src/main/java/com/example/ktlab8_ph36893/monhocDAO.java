package com.example.ktlab8_ph36893;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class monhocDAO {
    private final DbHelper dbHelper;

    public monhocDAO(Context context) {
        dbHelper = new DbHelper(context);
    }



    public ArrayList<monhoc> selectAll() {
        ArrayList<monhoc> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from monhoc", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    monhoc mh = new monhoc();
                    mh.setMamh(cursor.getString(0));
                    mh.setTenmh(cursor.getString(1));
                    mh.setSotiet(cursor.getInt(2));
                    list.add(mh);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi", e);

        }
        return list;
    }

    public boolean insert(monhoc mh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mamh", mh.getMamh());
        values.put("tenmh", mh.getTenmh());
        values.put("sotiet", mh.getSotiet());
        long row = db.insert("monhoc", null, values);
        return (row > 0);

    }

    public boolean update(monhoc mh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mamh", mh.getMamh());
        values.put("tenmh", mh.getTenmh());
        values.put("sotiet", mh.getSotiet());
        long row = db.update("monhoc", values, "mamh=?", new String[]{String.valueOf(mh.getMamh())});
        return (row > 0);
    }

    //xóa
    public boolean delete(String mamh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("monhoc", "mamh=?", new String[]{String.valueOf(mamh)});
        return (row > 0);
    }
}
