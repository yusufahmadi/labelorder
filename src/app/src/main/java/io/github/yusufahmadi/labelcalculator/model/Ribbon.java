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
    public double getmodal_roll() {
        double Hasil = 0.0;
        try {
            Hasil = (lebar / 1000.0) * panjang * harga_modal;
        } catch (Exception e) {
            e.printStackTrace();
            Hasil = 0.0;
        }
        return Hasil;
    }
    public double qty_order;
    public double jual_roll;
    public double getprofit_kotor() {
        double Hasil = 0.0;
        try {
            Hasil = (jual_roll - getmodal_roll()) * qty_order;
        } catch (Exception e) {
            e.printStackTrace();
            Hasil = 0.0;
        }
        return Hasil;
    }
    public double biaya_transport;
    public double komisi_sales_prosen;
    public double komisi_sales_rp() {
        double Hasil = 0.0;
        try {
            Hasil = (getprofit_kotor() * (komisi_sales_prosen/100));
        } catch (Exception e) {
            e.printStackTrace();
            Hasil = 0.0;
        }
        return Hasil;
    }
    public double getprofit_nett() {
        return getprofit_kotor()-biaya_transport-komisi_sales_rp();
    }
}
