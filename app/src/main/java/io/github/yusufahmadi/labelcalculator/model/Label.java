package io.github.yusufahmadi.labelcalculator.model;

import java.io.Serializable;
import java.util.Date;

public class Label implements Serializable {
//    "dokumen text null, " +
//    "tgl datetime default current_timestamp," +
//    "id_bahan int," +
//    "harga_bahan real," +
//    "lebar real," +
//    "tinggi real," +
//    "gap real," +
//    "pisau real," +
//    "pembulatan real," +
//    "qty_order real," +
//    "qty_jual real," +
//    "biaya_pisau real," +
//    "biaya_tinta real," +
//    "biaya_toyobo real," +
//    "biaya_operator real," +
//    "biaya_kirim real," +
//    "biaya_total real";

    public int no;
    public String dokumen;
    public Date tgl;
    public int id_bahan;
    public String bahan;
    public double harga_bahan;
    public double lebar;
    public double tinggi;
    public double gap;
    public double pisau;
    public double pembulatan;
    public double qty_order;
    public double qty_jual;
    public double biaya_pisau;
    public double biaya_tinta;
    public double biaya_toyobo;
    public double biaya_operator;
    public double biaya_kirim;

    public double getBiaya_total() {
        return biaya_pisau + biaya_tinta + biaya_toyobo + biaya_operator + biaya_kirim;
    }
}
