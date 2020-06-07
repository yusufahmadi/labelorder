package io.github.yusufahmadi.labelcalculator.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.helper.DataHelper;
import io.github.yusufahmadi.labelcalculator.model.Bahan;
import io.github.yusufahmadi.labelcalculator.model.Label;

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
            selectQuery = selectQuery + " ORDER BY [no] ";
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
            String selectQuery = "SELECT * FROM bahan WHERE nama='"+ Nama.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiBahan", e.getMessage(), e);
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
                SQL = "UPDATE bahan SET " +
                        "nama='"+ obj.nama.replace("'", "''") +"', " +
                        "harga=" + String.valueOf(obj.harga).replace(",", ".") +
                        " WHERE [no]=" + obj.id;
            } else {
                SQL = "SELECT max([no]) as nomax FROM bahan";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor.getCount()>=1) {
                    obj.id = cursor.getInt(0) + 1;
                } else {
                    obj.id = 1;
                }
                cursor.close();

                SQL = "INSERT INTO bahan ([no], nama, harga) values ("+ obj.id +", " +
                        "'"+ obj.nama.replace("'", "''") +"', "+
                        String.valueOf(obj.harga).replace(",", ".") +")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("saveBahan", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static List<Bahan> getListBahanLabel(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Bahan> iList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM bahan_label";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        "nama like '%"+ Filter.replace("'", "''") +"%' OR " +
                        "code like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY [no] ";
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
                obj.code = cursor.getString(2);
                obj.harga = cursor.getDouble(3);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListBahanLabel", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }

    public static boolean cekValidasiBahanLabel(Context ctx, String Nama, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM bahan_label WHERE nama='"+ Nama.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiBahanLabel", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveBahanLabel(Context ctx, Bahan obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.id>=1) {
                SQL = "UPDATE bahan_label SET " +
                        "nama='"+ obj.nama.replace("'", "''") +"', " +
                        "code='"+ obj.code.replace("'", "''") +"', " +
                        "harga=" + String.valueOf(obj.harga).replace(",", ".") +
                        " WHERE [no]=" + obj.id;
            } else {
                SQL = "SELECT max([no]) as nomax FROM bahan_label";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor.getCount()>=1) {
                    obj.id = cursor.getInt(0) + 1;
                } else {
                    obj.id = 1;
                }
                cursor.close();

                SQL = "INSERT INTO bahan_label ([no], nama, code, harga) values ("+ obj.id +", " +
                        "'"+ obj.nama.replace("'", "''") +"', " +
                        "'"+ obj.code.replace("'", "''") +"', " +
                        String.valueOf(obj.harga).replace(",", ".") +")";
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

    public static List<Bahan> getListTypeTaffeta(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Bahan> iList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM type_taffeta";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        "nama like '%"+ Filter.replace("'", "''") +"%' OR " +
                        "code like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY [no] ";
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
                obj.code = cursor.getString(2);
                obj.harga = cursor.getDouble(3);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListTypeTaffeta", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }

    public static boolean cekValidasiTypeTaffeta(Context ctx, String Nama, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM type_taffeta WHERE nama='"+ Nama.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiTypeTaffeta", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveTypeTaffeta(Context ctx, Bahan obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.id>=1) {
                SQL = "UPDATE type_taffeta SET " +
                        "nama='"+ obj.nama.replace("'", "''") +"', " +
                        "code='"+ obj.code.replace("'", "''") +"', " +
                        "harga=" + String.valueOf(obj.harga).replace(",", ".") +
                        " WHERE [no]=" + obj.id;
            } else {
                SQL = "SELECT max([no]) as nomax FROM type_taffeta";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor.getCount()>=1) {
                    obj.id = cursor.getInt(0) + 1;
                } else {
                    obj.id = 1;
                }
                cursor.close();

                SQL = "INSERT INTO type_taffeta ([no], nama, code, harga) values ("+ obj.id +", " +
                        "'"+ obj.nama.replace("'", "''") +"', " +
                        "'"+ obj.code.replace("'", "''") +"', " +
                        String.valueOf(obj.harga).replace(",", ".") +")";
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

    public static List<Label> getListLabel(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Label> iList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.US));
        try {
            String selectQuery = "SELECT label.*, bahan_label.nama as bahan FROM label left join bahan_label on bahan_label.[no]=label.id_bahan";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        " label.dokumen like '%"+ Filter.replace("'", "''") +"%' OR " +
                        " bahan_label.nama like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY label.tgl desc, label.dokumen desc";
            if (page>=1 && limit>=1) {
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit)+1 + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

//                String sql = "create table label([no] integer primary key, " +
//                        "dokumen text null, " +
//                        "tgl datetime default current_timestamp," +
//                        "id_bahan int," +
//                        "harga_bahan real," +
//                        "lebar real," +
//                        "tinggi real," +
//                        "gap real," +
//                        "pisau real," +
//                        "pembulatan real," +
//                        "qty_order real," +
//                        "qty_jual real," +
//                        "biaya_pisau real," +
//                        "biaya_tinta real," +
//                        "biaya_toyobo real," +
//                        "biaya_operator real," +
//                        "biaya_kirim real," +
//                        "biaya_total real);";

                Label obj           = new Label();
                obj.no              = cursor.getInt(0);
                obj.dokumen         = cursor.getString(1);
                obj.tgl             = df.parse(cursor.getString(2));
                obj.id_bahan        = cursor.getInt(3);
                obj.harga_bahan     = cursor.getDouble(4);
                obj.lebar           = cursor.getDouble(5);
                obj.tinggi          = cursor.getDouble(6);
                obj.gap             = cursor.getDouble(7);
                obj.pisau           = cursor.getDouble(8);
                obj.pembulatan      = cursor.getDouble(9);
                obj.qty_order       = cursor.getDouble(10);
                obj.qty_jual        = cursor.getDouble(11);
                obj.biaya_pisau     = cursor.getDouble(12);
                obj.biaya_tinta     = cursor.getDouble(13);
                obj.biaya_toyobo    = cursor.getDouble(13);
                obj.biaya_operator  = cursor.getDouble(13);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListBahanLabel", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }

    public static boolean cekValidasiLabel(Context ctx, String Dokumen, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM label WHERE dokumen='"+ Dokumen.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiLabel", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveLabel(Context ctx, Label obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
//            if (obj.no>=1) {
//                SQL = "UPDATE label SET " +
//                        "dokumen='"+ obj.nama.replace("'", "''") +"', " +
//                        "tgl='"+ obj.tgl.replace("'", "''") +"', " +
//                        "harga=" + String.valueOf(obj.harga).replace(",", ".") +
//                        " WHERE no=" + obj.id;
//            } else {
//                SQL = "SELECT max([no]) as nomax FROM bahan_label";
//                Cursor cursor = db.rawQuery(SQL, null);
//                if (cursor.getCount()>=1) {
//                    obj.id = cursor.getInt(0) + 1;
//                } else {
//                    obj.id = 1;
//                }
//                cursor.close();
//
//                SQL = "INSERT INTO bahan_label (no, nama, code, harga) values ("+ obj.id +", " +
//                        "'"+ obj.nama.replace("'", "''") +"', " +
//                        "'"+ obj.code.replace("'", "''") +"', " +
//                        String.valueOf(obj.harga).replace(",", ".") +")";
//            }
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
