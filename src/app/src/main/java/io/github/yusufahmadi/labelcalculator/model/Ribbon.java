package io.github.yusufahmadi.labelcalculator.model;

import java.io.Serializable;
import java.util.Date;

public class Ribbon implements Serializable {


    public int no;
    public String dokumen;
    public Date tgl;
    public int id_bahan;
    public String bahan;
    public double harga_modal;
    public double lebar;
    public double panjang;
    public double modal;

//    public double _10Persen;
//    public double _15Persen;
//    public double _25Persen;
//    public double _35Persen;
//    public double _45Persen;
//    public double _55Persen;
//    public double _65Persen;
//    public double _75Persen;

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
