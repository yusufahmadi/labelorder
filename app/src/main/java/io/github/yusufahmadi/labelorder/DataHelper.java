package io.github.yusufahmadi.labelorder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import io.github.yusufahmadi.labelorder.mdlPublic;
import io.github.yusufahmadi.labelorder.Bahan;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "labelorder.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table bahan(no integer primary key, nama text null, harga real null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO bahan (no, nama, harga) VALUES ('1', 'Wax',1650),('2', 'Wax Resin',3410),('3', 'Resin / CL',5500),('4', 'Resin Frosen',6380);";
        db.execSQL(sql);
                //        Wax	 Rp1,650
                //        Wax Resin	 Rp3,410
                //        Resin / CL	 Rp5,500
                //        Resin Frosen	 Rp6,380
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    public  void refreshBahan(){
        // Select All Query
        String selectQuery = "SELECT  * FROM bahan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments


        mdlPublic.ListStrBahan = new ArrayList<String>();
        mdlPublic.ListStrBahan.clear();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            Bahan Obj = new Bahan();
//            Obj.setId(cursor.getInt(1));
//            Obj.setNama(cursor.getString(2));
//            Obj.setHarga(cursor.getInt(3));
//            mdlPublic.ListBahan.add(Obj);
//        }
    }

}