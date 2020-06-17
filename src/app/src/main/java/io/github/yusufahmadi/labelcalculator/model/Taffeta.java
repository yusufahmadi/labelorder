package io.github.yusufahmadi.labelcalculator.model;

import java.io.Serializable;
import java.util.Date;

public class Taffeta implements Serializable {


    public int no;
    public String dokumen;
    public Date tgl;
    public int id_bahan;
    public String bahan;
    public double harga_modal;
    public double kurs;
    public double lebar;
    public double panjang;
    public double modal;

    public double qty;
    public double jual_roll;
    public double jumlah_profit_kotor;
    public double transport;
    public double komisisalesprosen;
    //    public double _komisisalesmominal;
    public double get_komisisales_nominal() {
        return (komisisalesprosen *jumlah_profit_kotor)/100;
    }
    public double netprofit;
}
