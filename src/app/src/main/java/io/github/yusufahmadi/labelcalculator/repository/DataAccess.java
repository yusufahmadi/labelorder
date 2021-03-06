package io.github.yusufahmadi.labelcalculator.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yusufahmadi.labelcalculator.helper.DataHelper;
import io.github.yusufahmadi.labelcalculator.model.Bahan;
import io.github.yusufahmadi.labelcalculator.model.Label;
import io.github.yusufahmadi.labelcalculator.model.Paket;
import io.github.yusufahmadi.labelcalculator.model.Ribbon;
import io.github.yusufahmadi.labelcalculator.model.Taffeta;

public class DataAccess {
    private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.US));
    private static DecimalFormat df    = new DecimalFormat("###,###,###.##", new DecimalFormatSymbols(Locale.US));
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
                    cursor.moveToFirst();
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
                    cursor.moveToFirst();
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
                    cursor.moveToFirst();
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

    //Transaksi Label
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
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit) + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                Label obj           = new Label();
                obj.no              = cursor.getInt(0);
                obj.dokumen         = cursor.getString(1);
                obj.tgl             = df.parse(cursor.getString(2));
                obj.id_bahan        = cursor.getInt(3);
                obj.harga_modal     = cursor.getDouble(4);
                obj.lebar           = cursor.getDouble(5);
                obj.tinggi          = cursor.getDouble(6);
                obj.gap             = cursor.getDouble(7);
                obj.pisau           = cursor.getDouble(8);
                obj.pembulatan      = cursor.getDouble(9);
                obj.qty_order       = cursor.getDouble(10);
                obj.jual_sesuai_order = cursor.getDouble(11);
                obj.biaya_pisau     = cursor.getDouble(12);
                obj.biaya_tinta     = cursor.getDouble(13);
                obj.biaya_toyobo    = cursor.getDouble(14);
                obj.biaya_operator  = cursor.getDouble(15);
                obj.biaya_kirim     = cursor.getDouble(16);
                obj.bahan           = cursor.getString(17);
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
            if (obj.no>=1) {
                SQL = "UPDATE label SET " +
                        "dokumen='"+ obj.dokumen.replace("'", "''") +"', " +
                        "tgl='"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        "id_bahan=" + String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        "harga_bahan=" + String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        "lebar=" + String.valueOf(obj.lebar).replace(",", ".") + "," +
                        "tinggi=" + String.valueOf(obj.tinggi).replace(",", ".") + "," +
                        "gap=" + String.valueOf(obj.gap).replace(",", ".") + "," +
                        "pisau=" + String.valueOf(obj.pisau).replace(",", ".") + "," +
                        "pembulatan=" + String.valueOf(obj.pembulatan).replace(",", ".") + "," +
                        "qty_order=" + String.valueOf(obj.qty_order).replace(",", ".") + "," +
                        "jual_sesuai_order=" + String.valueOf(obj.jual_sesuai_order).replace(",", ".") + "," +
                        "biaya_pisau=" + String.valueOf(obj.biaya_pisau).replace(",", ".") + "," +
                        "biaya_tinta=" + String.valueOf(obj.biaya_tinta).replace(",", ".") + "," +
                        "biaya_toyobo=" + String.valueOf(obj.biaya_toyobo).replace(",", ".") + "," +
                        "biaya_operator=" + String.valueOf(obj.biaya_operator).replace(",", ".") + "," +
                        "biaya_kirim=" + String.valueOf(obj.biaya_kirim).replace(",", ".") +
                        " WHERE [no]=" + obj.no;
            } else {
                SQL = "SELECT max([no]) as nomax FROM label";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor != null) {
                    if (cursor.getCount()>=1) {
                        //error pas kosong belum ada data
                        cursor.moveToFirst();
                        obj.no = cursor.getInt(0) + 1;
                    } else {
                        obj.no = 1;
                    }
                } else {
                    obj.no = 1;
                }
                cursor.close();

                SQL = "INSERT INTO label ([no],dokumen,tgl,id_bahan,harga_bahan,lebar,tinggi,gap ,pisau,pembulatan,qty_order,jual_sesuai_order,biaya_pisau,biaya_tinta ,biaya_toyobo ,biaya_operator,biaya_kirim) values ("+ obj.no +", " +
                        "'"+ obj.dokumen.replace("'", "''") +"', " +
                        "'"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        String.valueOf(obj.lebar).replace(",", ".") + "," +
                        String.valueOf(obj.tinggi).replace(",", ".") + "," +
                        String.valueOf(obj.gap).replace(",", ".") + "," +
                        String.valueOf(obj.pisau).replace(",", ".") + "," +
                        String.valueOf(obj.pembulatan).replace(",", ".") + "," +
                        String.valueOf(obj.qty_order).replace(",", ".") + "," +
                        String.valueOf(obj.jual_sesuai_order).replace(",", ".") + "," +
                        String.valueOf(obj.biaya_pisau).replace(",", ".") + "," +
                        String.valueOf(obj.biaya_tinta).replace(",", ".") + "," +
                        String.valueOf(obj.biaya_toyobo).replace(",", ".") + "," +
                        String.valueOf(obj.biaya_operator).replace(",", ".") + "," +
                        String.valueOf(obj.biaya_kirim).replace(",", ".") + ")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("saveLabel", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }


    //Transaksi Ribbon
    public static List<Ribbon> getListRibbon(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Ribbon> iList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.US));
        try {
            String selectQuery = "SELECT ribbon.*, bahan.nama as bahan FROM ribbon left join bahan on bahan.[no]=ribbon.id_bahan";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        " ribbon.dokumen like '%"+ Filter.replace("'", "''") +"%' OR " +
                        " bahan.nama like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY ribbon.tgl desc, ribbon.dokumen desc";
            if (page>=1 && limit>=1) {
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit) + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                Ribbon obj           = new Ribbon();
                obj.no              = cursor.getInt(0);
                obj.dokumen         = cursor.getString(1);
                obj.tgl             = df.parse(cursor.getString(2));
                obj.id_bahan        = cursor.getInt(3);
                obj.harga_modal     = cursor.getDouble(4);
                obj.lebar           = cursor.getDouble(5);
                obj.panjang          = cursor.getDouble(6);
                obj.modal             = cursor.getDouble(7);
                obj.qty           = cursor.getDouble(8);
                obj.jual_roll      = cursor.getDouble(9);
                obj.jumlah_profit_kotor       = cursor.getDouble(10);
                obj.transport = cursor.getDouble(11);
                obj.komisisalesprosen     = cursor.getDouble(12);
                obj.netprofit     = cursor.getDouble(13);
                obj.bahan           = cursor.getString(14);
                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getListRibbon", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }

    public static boolean cekValidasiRibbon(Context ctx, String Dokumen, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM ribbon WHERE dokumen='"+ Dokumen.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiRibbon", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveRibbon(Context ctx, Ribbon obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.no>=1) {
                SQL = "UPDATE ribbon SET " +
                        "dokumen='"+ obj.dokumen.replace("'", "''") +"', " +
                        "tgl='"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        "id_bahan=" + String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        "harga_bahan=" + String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        "lebar=" + String.valueOf(obj.lebar).replace(",", ".") + "," +
                        "panjang=" + String.valueOf(obj.panjang).replace(",", ".") + "," +
                        "modal=" + String.valueOf(obj.modal).replace(",", ".") + "," +
                        "qty=" + String.valueOf(obj.qty).replace(",", ".") + "," +
                        "jual_roll=" + String.valueOf(obj.jual_roll).replace(",", ".") + "," +
                        "jumlah_profit_kotor=" + String.valueOf(obj.jumlah_profit_kotor).replace(",", ".") + "," +
                        "transport=" + String.valueOf(obj.transport).replace(",", ".") + "," +
                        "komisisalesprosen=" + String.valueOf(obj.komisisalesprosen).replace(",", ".") + "," +
                        "netprofit=" + String.valueOf(obj.netprofit).replace(",", ".") +
                        " WHERE [no]=" + obj.no;
            } else {
                SQL = "SELECT max([no]) as nomax FROM ribbon";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor != null) {
                    if (cursor.getCount()>=1) {
                        //error pas kosong belum ada data
                        cursor.moveToFirst();
                        obj.no = cursor.getInt(0) + 1;
                    } else {
                        obj.no = 1;
                    }
                } else {
                    obj.no = 1;
                }
                cursor.close();

                SQL = "INSERT INTO ribbon ([no],dokumen,tgl,id_bahan,harga_bahan,lebar,panjang,modal,qty ,jual_roll ,jumlah_profit_kotor,transport ,komisisalesprosen,netprofit) values ("+ obj.no +", " +
                        "'"+ obj.dokumen.replace("'", "''") +"', " +
                        "'"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        String.valueOf(obj.lebar).replace(",", ".") + "," +
                        String.valueOf(obj.panjang).replace(",", ".") + "," +
                        String.valueOf(obj.modal).replace(",", ".") + "," +
                        String.valueOf(obj.qty).replace(",", ".") + "," +
                        String.valueOf(obj.jual_roll).replace(",", ".") + "," +
                        String.valueOf(obj.jumlah_profit_kotor).replace(",", ".") + "," +
                        String.valueOf(obj.transport).replace(",", ".") + "," +
                        String.valueOf(obj.komisisalesprosen).replace(",", ".") + "," +
                        String.valueOf(obj.netprofit).replace(",", ".") + ")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("saveRibbon", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }


    //Transaksi Taffeta
    public static List<Taffeta> getListTaffeta(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Taffeta> iList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.US));
        try {
            String selectQuery = "SELECT taffeta.*, type_taffeta.nama as bahan FROM taffeta left join type_taffeta on type_taffeta.[no]=taffeta.id_bahan";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        " taffeta.dokumen like '%"+ Filter.replace("'", "''") +"%' OR " +
                        " type_taffeta.nama like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY taffeta.tgl desc, taffeta.dokumen desc";

            if (page>=1 && limit>=1) {
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit) + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                Taffeta obj           = new Taffeta();
                obj.no              = cursor.getInt(0);
                obj.dokumen         = cursor.getString(1);
                obj.tgl             = df.parse(cursor.getString(2));
                obj.id_bahan        = cursor.getInt(3);
                obj.harga_modal     = cursor.getDouble(4);
                obj.lebar           = cursor.getDouble(5);
                obj.panjang          = cursor.getDouble(6);
                obj.modal             = cursor.getDouble(7);
                obj.qty           = cursor.getDouble(8);
                obj.jual_roll      = cursor.getDouble(9);
                obj.jumlah_profit_kotor       = cursor.getDouble(10);
                obj.transport = cursor.getDouble(11);
                obj.komisisalesprosen     = cursor.getDouble(12);
                obj.netprofit     = cursor.getDouble(13);
                obj.kurs           = cursor.getDouble(14);
                obj.bahan           = cursor.getString(15);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("getListTaffeta", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }


    public static boolean cekValidasiTaffeta(Context ctx, String Dokumen, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM taffeta WHERE dokumen='"+ Dokumen.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiTaffeta", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean saveTaffeta(Context ctx, Taffeta obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.no>=1) {
                SQL = "UPDATE taffeta SET " +
                        "dokumen='"+ obj.dokumen.replace("'", "''") +"', " +
                        "tgl='"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        "id_bahan=" + String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        "harga_bahan=" + String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        "lebar=" + String.valueOf(obj.lebar).replace(",", ".") + "," +
                        "panjang=" + String.valueOf(obj.panjang).replace(",", ".") + "," +
                        "modal=" + String.valueOf(obj.modal).replace(",", ".") + "," +
                        "qty=" + String.valueOf(obj.qty).replace(",", ".") + "," +
                        "jual_roll=" + String.valueOf(obj.jual_roll).replace(",", ".") + "," +
                        "jumlah_profit_kotor=" + String.valueOf(obj.jumlah_profit_kotor).replace(",", ".") + "," +
                        "transport=" + String.valueOf(obj.transport).replace(",", ".") + "," +
                        "komisisalesprosen=" + String.valueOf(obj.komisisalesprosen).replace(",", ".") + "," +
                        "netprofit=" + String.valueOf(obj.netprofit).replace(",", ".") +  "," +
                        "kurs=" + String.valueOf(obj.kurs).replace(",", ".") +
                        " WHERE [no]=" + obj.no;
            } else {
                SQL = "SELECT max([no]) as nomax FROM taffeta";

                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor != null) {
                    if (cursor.getCount()>=1) {
                        //error pas kosong belum ada data
                        cursor.moveToFirst();
                        obj.no = cursor.getInt(0) + 1;
                    } else {
                        obj.no = 1;
                    }
                } else {
                    obj.no = 1;
                }
                cursor.close();

                SQL = "INSERT INTO taffeta ([no],dokumen,tgl,id_bahan,harga_bahan,lebar,panjang,modal,qty ,jual_roll ,jumlah_profit_kotor,transport ,komisisalesprosen,kurs,netprofit) values ("+ obj.no +", " +

                        "'"+ obj.dokumen.replace("'", "''") +"', " +
                        "'"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        String.valueOf(obj.id_bahan).replace(",", ".") + "," +
                        String.valueOf(obj.harga_modal).replace(",", ".") + "," +
                        String.valueOf(obj.lebar).replace(",", ".") + "," +
                        String.valueOf(obj.panjang).replace(",", ".") + "," +
                        String.valueOf(obj.modal).replace(",", ".") + "," +
                        String.valueOf(obj.qty).replace(",", ".") + "," +
                        String.valueOf(obj.jual_roll).replace(",", ".") + "," +
                        String.valueOf(obj.jumlah_profit_kotor).replace(",", ".") + "," +
                        String.valueOf(obj.transport).replace(",", ".") + "," +
                        String.valueOf(obj.komisisalesprosen).replace(",", ".") + "," +
                        String.valueOf(obj.kurs).replace(",", ".") + "," +
                        String.valueOf(obj.netprofit).replace(",", ".") + ")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("saveTaffeta", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    //Transaksi Paket
    public static List<Paket> getListPaket(Context ctx, String Filter, int page, int limit) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        List<Paket> iList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.US));
        try {
            String selectQuery = "SELECT paket.* FROM paket ";
            if (!Filter.isEmpty()) {
                selectQuery = selectQuery + " WHERE " +
                        " paket.dokumen like '%"+ Filter.replace("'", "''") +"%'";
            }
            selectQuery = selectQuery + " ORDER BY paket.tgl desc, paket.dokumen desc";

            if (page>=1 && limit>=1) {
                selectQuery = selectQuery + " LIMIT " + String.valueOf((page-1)*limit) + ", " + String.valueOf(limit);
            }
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                Paket obj = new Paket();
                obj.no       = cursor.getInt(0);
                obj.dokumen  = cursor.getString(1);
                obj.tgl      = df.parse(cursor.getString(2));
                obj.customer_minta_bikin_jadinya_line	= cursor.getInt(3);
                obj.qty_order_customer_pcs     			    = cursor.getDouble(4);
                obj.isi_roll           					    = cursor.getInt(5);
                obj.lebar          						= cursor.getDouble(6);
                obj.tinggi             					= cursor.getDouble(7);
                obj.pisau_yang_digunakan     = cursor.getDouble(8);
                obj.dibulatkan      					= cursor.getDouble(9);
                obj.lebar_ribbon       				= cursor.getDouble(10);
                obj.panjang_ribbon 				= cursor.getDouble(11);

                iList.add(obj);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("getListPaket", e.getMessage(), e);
            iList.clear();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return iList;
    }


    public static boolean cekValidasiPaket(Context ctx, String Dokumen, int PK) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        boolean hasil = false;
        try {
            String selectQuery = "SELECT * FROM paket WHERE dokumen='"+ Dokumen.replace("'", "''") +"' AND [no]<>" + PK;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()>=1) {
                hasil = false;
            } else {
                hasil = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("cekValidasiPaket", e.getMessage(), e);
            hasil = false;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
            dbcenter.close();
        }
        return hasil;
    }

    public static boolean savePaket(Context ctx, Paket obj) {
        DataHelper dbcenter = new DataHelper(ctx);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        boolean hasil = false;
        String SQL = "";
        try {
            if (obj.no>=1) {
                SQL = "UPDATE paket SET " +
                        "dokumen='"+ obj.dokumen.replace("'", "''") +"', " +
                        "tgl='"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        "customer_minta_bikin_jadinya_line =" + String.valueOf(obj.customer_minta_bikin_jadinya_line).replace(",", ".") + "," +
                        "qty_order_customer_pcs=" + String.valueOf(obj.qty_order_customer_pcs).replace(",", ".") + "," +
                        "isi_roll =" + String.valueOf(obj.isi_roll).replace(",", ".") + "," +
                        "lebar =" + String.valueOf(obj.lebar).replace(",", ".") + "," +
                        "tinggi =" + String.valueOf(obj.tinggi).replace(",", ".") + "," +
                        "pisau_yang_digunakan =" + String.valueOf(obj.pisau_yang_digunakan).replace(",", ".") + "," +
                        "dibulatkan =" + String.valueOf(obj.dibulatkan).replace(",", ".") + "," +
                        "lebar_ribbon =" + String.valueOf(obj.lebar_ribbon).replace(",", ".") + "," +
                        "panjang_ribbon =" + String.valueOf(obj.panjang_ribbon).replace(",", ".") + " " +
                        " WHERE [no]=" + obj.no;
            } else {
                SQL = "SELECT max([no]) as nomax FROM paket";
                Cursor cursor = db.rawQuery(SQL, null);
                if (cursor != null) {
                    if (cursor.getCount()>=1) {
                        //error pas kosong belum ada data
                        cursor.moveToFirst();
                        obj.no = cursor.getInt(0) + 1;
                    } else {
                        obj.no = 1;
                    }
                } else {
                    obj.no = 1;
                }
                cursor.close();
                SQL = "INSERT INTO paket ([no],dokumen,tgl,customer_minta_bikin_jadinya_line,qty_order_customer_pcs,isi_roll,lebar,tinggi,pisau_yang_digunakan,dibulatkan,lebar_ribbon,panjang_ribbon) values ("+ obj.no +", " +

                        "'"+ obj.dokumen.replace("'", "''") +"', " +
                        "'"+ dt.format(obj.tgl).replace("'", "''") +"', " +
                        String.valueOf(obj.customer_minta_bikin_jadinya_line).replace(",", ".") + "," +
                        String.valueOf(obj.qty_order_customer_pcs).replace(",", ".") + "," +
                        String.valueOf(obj.isi_roll).replace(",", ".") + "," +
                        String.valueOf(obj.lebar).replace(",", ".") + "," +
                        String.valueOf(obj.tinggi).replace(",", ".") + "," +
                        String.valueOf(obj.pisau_yang_digunakan).replace(",", ".") + "," +
                        String.valueOf(obj.dibulatkan).replace(",", ".") + "," +
                        String.valueOf(obj.lebar_ribbon).replace(",", ".") + "," +
                        String.valueOf(obj.panjang_ribbon).replace(",", ".") + ")";
            }
            db.execSQL(SQL);
            hasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("savePaket", e.getMessage(), e);
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
