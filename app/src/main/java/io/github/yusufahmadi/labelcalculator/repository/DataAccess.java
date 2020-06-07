package io.github.yusufahmadi.labelcalculator.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.yusufahmadi.labelcalculator.helper.DataHelper;
import io.github.yusufahmadi.labelcalculator.model.Bahan;

public class DataAccess {
    public static List<Bahan> getListBahan(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Bahan> iList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM bahan";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE nama like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY no ";
            if (page>=1 && limit>=1) {
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit) + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                Bahan obj = new Bahan();
                obj.id = cursor.getInt(0);
                obj.nama = cursor.getString(1);
                obj.harga = cursor.getDouble(2);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListBahan", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }

    public static boolean cekValidasiBahan(Context ctx, String Nama, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM bahan WHERE nama='"+ Nama.replace("'", "''") +"' AND no<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListBahan", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveBahan(Context ctx, Bahan obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.id>=1) {
                SQL = "UPDATE bahan SET nama='"+ obj.nama.replace("'", "''") +"', harga=" + String.valueOf(obj.harga).replace(",", ".") + " WHERE no=" + obj.id;
            } else {
                SQL = "SELECT max(no) as nomax FROM bahan";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor.getCount()>=1) {
                    obj.id = cursor.getInt(0) + 1;
                } else {
                    obj.id = 1;
                }
                cursor.close();

                SQL = "INSERT INTO bahan (no, nama, harga) values ("+ obj.id +", '"+ obj.nama.replace("'", "''") +"', "+ String.valueOf(obj.harga).replace("'", "''") +")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListBahan", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }
}
