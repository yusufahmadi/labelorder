package io.github.yusufahmadi.labelcalculator.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import io.github.yusufahmadi.labelcalculator.model.Bahan;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "labelcalculator.db";
    private static final int DATABASE_VERSION = 6;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table bahan([no] integer primary key, nama text, harga real);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO bahan ([no], nama, harga) VALUES ('1', 'Wax',1650),('2', 'Wax Resin',3410),('3', 'Resin / CL',5500),('4', 'Resin Frosen',6380);";
        db.execSQL(sql);

        dbUpgrade2(db);
        dbUpgrade3(db);
        dbUpgrade4(db);
        dbUpgrade5(db);
        dbUpgrade6(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if (oldVersion<2) {
            dbUpgrade2(db);
        }

        if (oldVersion<3) {
            dbUpgrade3(db);
        }

        if (oldVersion<4) {
            dbUpgrade4(db);
        }

        if (oldVersion<5) {
            dbUpgrade5(db);
        }

        if (oldVersion<6) {
            dbUpgrade6(db);
        }
    }

    private void dbUpgrade2(SQLiteDatabase db) {
        Log.d("On Upgrade", "DB Struktur 2");
        String sql = "create table bahan_label([no] integer primary key, nama text, code text, harga real);";
        db.execSQL(sql);
        sql = "INSERT INTO bahan_label ([no], nama, code, harga) VALUES " +
                "('1', 'SC Avery', 'AW0416FT', 4642)," +
                "('2', 'Yupo Avery', 'BW0277', 9864.8)," +
                "('3', 'Thermal Avery', 'NW0045FN', 6791.4)," +
                "('4', 'HVS Avery', 'AW0289',  5276.7)," +
                "('5', 'Overlaminating Avery', 'BW0112N',  7276.5)," +
                "('6', 'SC Camel Backing Putih', 'LP ART-B / GL P-E',  4480)," +
                "('7', 'SC Camel Backing Biru', 'LP ART-B / GL BM',  4480)," +
                "('8', 'Cromo Camel Backing Putih', 'LP CC / GL P-A',  5160)," +
                "('9', 'Cromo Camel Backing Biru', 'LP CC / GL BM',  5160)," +
                "('10', 'Yupo Camel', 'LF OPP PSM / GL P-A',  9220)," +
                "('11', 'Thermal Putih/Biru Camel', 'LP THERMAL GL P-A/BM',  6850)," +
                "('12', 'HVS Camel', 'LP HVS-A / GL P-A/BM',  4520);";
        db.execSQL(sql);
    }

    private void dbUpgrade3(SQLiteDatabase db) {
        Log.d("On Upgrade", "DB Struktur 3");
        String sql = "create table label([no] integer primary key, " +
                "dokumen text, " +
                "tgl datetime default current_timestamp," +
                "id_bahan int," +
                "harga_bahan real," +
                "lebar real," +
                "tinggi real," +
                "gap real," +
                "pisau real," +
                "pembulatan real," +
                "qty_order real," +
                "jual_sesuai_order real," +
                "biaya_pisau real," +
                "biaya_tinta real," +
                "biaya_toyobo real," +
                "biaya_operator real," +
                "biaya_kirim real);";
        db.execSQL(sql);
    }

    private void dbUpgrade4(SQLiteDatabase db) {
        Log.d("On Upgrade", "DB Struktur 2");
        String sql = "create table type_taffeta([no] integer primary key, nama text, code text, harga real);";
        db.execSQL(sql);
        sql = "INSERT INTO type_taffeta ([no], nama, code, harga) VALUES " +
                "('1', '3500', '', 0.1)," +
                "('2', '37BT', '', 0.112)," +
                "('3', '661', 'Rp. 2000', 0.157);";
        db.execSQL(sql);
    }

    private void dbUpgrade5(SQLiteDatabase db) {
        Log.d("On Upgrade", "DB Struktur 5");
        String sql;
        sql = "drop table label;";
        db.execSQL(sql);

        sql = "create table label([no] integer primary key, " +
                "dokumen text, " +
                "tgl datetime default current_timestamp," +
                "id_bahan int," +
                "harga_bahan real," +
                "lebar real," +
                "tinggi real," +
                "gap real," +
                "pisau real," +
                "pembulatan real," +
                "qty_order real," +
                "jual_sesuai_order real," +
                "biaya_pisau real," +
                "biaya_tinta real," +
                "biaya_toyobo real," +
                "biaya_operator real," +
                "biaya_kirim real);";
        db.execSQL(sql);
    }

    private void dbUpgrade6(SQLiteDatabase db) {
        Log.d("On Upgrade", "DB Struktur 6");
        String sql;
        sql = "create table ribbon([no] integer primary key, " +
                "dokumen text, " +
                "tgl datetime default current_timestamp," +
                "id_bahan int," +
                "harga_bahan real," +
                "lebar real," +
                "panjang real," +
                "modal real," +
                "qty real," +
                "jual_roll real," +
                "jumlah_profit_kotor real," +
                "transport real," +
                "komisisalesprosen real," +
                "netprofit real);";
        db.execSQL(sql);
    }
}