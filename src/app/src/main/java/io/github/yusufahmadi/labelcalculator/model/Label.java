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

//    spinner_bahan;
//    HargaModal, Lebar,
//    Tinggi, Gap,
//    Pisau,LebarBahanBelanja,
//    1RollPcs,SaranOrderPcs,
//    QtyOrderPcs,PembulatanRoll,
//    KebutuhanRoll,BiayaPisau,
//    BiayaTinta,BiayaToyobo,
//    BiayaOperator,BiayaKirim,
//    BiayaTotal, Catatan,
//    ModalBahanUtuh ,ModalPerPcs ,
//    JualSesuaiOrder ,Profit1,
//    JualSesuaiSaran ,Profit2;

//    textView30Persen,textView50Persen, textView100Persen,textView125Persen,textView150Persen,textView175Persen,textView200Persen,textView75Persen;

    public int no;
    public String dokumen;
    public Date tgl;
    public int id_bahan;
    public String bahan;
    public double harga_modal;
    public double lebar;
    public double tinggi;
    public double gap;
    public double pisau;
    public double pembulatan;
    public double qty_order;
    public double jual_sesuai_order;
    public double biaya_pisau;
    public double biaya_tinta;
    public double biaya_toyobo;
    public double biaya_operator;
    public double biaya_kirim;

    public double getBiaya_total() {
        return biaya_pisau + biaya_tinta + biaya_toyobo + biaya_operator + biaya_kirim;
    }

    public double net_profit_sesuai_order() {
        double modal_bahan_utuh=0.0, modal_per_pcs=0.0, lebar_bahan_belanjanya = 0.0;
        lebar_bahan_belanjanya = (lebar*pisau) + 8 + ((pisau-1)*gap);
        modal_bahan_utuh = (harga_modal*lebar_bahan_belanjanya)*pembulatan;
        modal_per_pcs = qty_order==0 ? 1 : modal_bahan_utuh / qty_order;
        return ((jual_sesuai_order-modal_per_pcs)*qty_order)-getBiaya_total();
    }

    public double net_profit_sesuai_saran() {
        double modal_bahan_utuh=0.0, modal_per_pcs=0.0, lebar_bahan_belanjanya = 0.0;
        lebar_bahan_belanjanya = (lebar*pisau) + 8 + ((pisau-1)*gap);
        modal_bahan_utuh = (harga_modal*lebar_bahan_belanjanya)*pembulatan;
        modal_per_pcs = qty_order==0 ? 1 : modal_bahan_utuh / qty_order;
        return ((jual_sesuai_order-modal_per_pcs)*qty_order)-getBiaya_total();
    }
}
