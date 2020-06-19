package io.github.yusufahmadi.labelcalculator.model;

import java.io.Serializable;
import java.util.Date;

public class Paket  implements Serializable {

    public int no;
    public String dokumen;
    public Date tgl;
//    Perhitungan Belanja Bahan Baku Label
    public int customer_minta_bikin_jadinya_line;
    public double qty_order_customer_pcs;
    public long isi_roll;
//    public double xtotal_jadi_roll; //=Qty_Order_Customer_Pcs/Isi_Roll
    public double lebar;// (mm)
    public double tinggi;// (mm)
    public double pisau_yang_digunakan; // Kiri Ke Kanan
//    public double xlebar_bahan; //(mm)	=(Lebar * Pisau_yang_digunakan )+(3*(Pisau_yang_digunakan - 1)+8)
//    public double x1roll_jadiny_pcs; //=	(975000/( Tinggi +3)*(Pisau Yang digunakan))
//    public double  xjadi_belanja_bahan_baku_label_dalam_Roll; // =	Qty_Order_Customer_Pcs / X1RollJadiny_Pcs
    public double dibulatkan;
//    Perhitungan Pemakaian Ribbonnya  BILA Customer meminta sesuai order label
    public double lebar_ribbon;// (mm)
    public double panjang_ribbon;// (Meter)
//    public double x1roll_ribbon_bisa_cetak_pcs; // =	(PanjangRibbon*1000)/(Tinggi +3)*Customer_Minta_Bikin_Jadinya_Line
//    public double xmaka_kebutuhan_ribbonnya ; //Qty_Order_Customer_Pcs /  X1_Roll_Ribbon_bisa_cetak_Pcs

}

