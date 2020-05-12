package io.github.yusufahmadi.labelorder;

import java.sql.Date;

public class PO {
    private String _id;

    public Date get_tanggal() {
        return _tanggal;
    }

    public void set_tanggal(Date _tanggal) {
        this._tanggal = _tanggal;
    }

    public String get_tanggalStr() {
        return _tanggalStr;
    }

    public void set_tanggalStr(String _tanggalStr) {
        this._tanggalStr = _tanggalStr;
    }

    private Date _tanggal;
    private String _tanggalStr;
    private String _nama;
    private String _catatan;
    private double _lebar;
    private double _panjang;
    private int  _idbahan;
    private double _hargaM2;
    private double _modalperroll;
    private double _qty;
    private double _jualroll;
    private double _jumlahprofitkotor;
    private double _transportasi;
    private double _komisisalesprosen;
    private double _komisisalesnominal;
    private double _netprofit;


    public PO (String id,String Nama ,int Harga){
        this._id = "";
        this._nama = "";
        this._catatan = "";
        this._lebar=0;
        this._panjang=0;
        this._idbahan=0;
        this._hargaM2=0;
        this._modalperroll=0;
        this._qty=0;
        this._jualroll=0;
        this._jumlahprofitkotor=0;
        this._transportasi=0;
        this._komisisalesprosen=0;
        this._komisisalesnominal=0;
        this._netprofit=0;
    }

    public PO (String id,String Nama ,String Catatan,
               double Lebar , double Panjang,
               int IDBahan,double HargaM2,
               double ModalPerRoll,
               double Qty,  double JualRoll,
               double JumlahProfitKotor,
               double Transportasi,
               double KomisiSalesProsen,double KomisiSalesNominal,
               double NetProfit){
        this._id = id;
        this._nama = Nama;
        this._catatan = Catatan;
        this._lebar=Lebar;
        this._panjang=Panjang;
        this._idbahan=IDBahan;
        this._hargaM2 = HargaM2;
        this._modalperroll=ModalPerRoll;
        this._qty=Qty;
        this._jualroll=JualRoll;
        this._jumlahprofitkotor=JumlahProfitKotor;
        this._transportasi=Transportasi;
        this._komisisalesprosen=KomisiSalesProsen;
        this._komisisalesnominal=KomisiSalesNominal;
        this._netprofit=NetProfit;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nama() {
        return _nama;
    }

    public void set_nama(String _nama) {
        this._nama = _nama;
    }

    public String get_catatan() {
        return _catatan;
    }

    public void set_catatan(String _catatan) {
        this._catatan = _catatan;
    }

    public double get_lebar() {
        return _lebar;
    }

    public void set_lebar(double _lebar) {
        this._lebar = _lebar;
    }
    public double get_panjang() {
        return _panjang;
    }

    public void set_panjang(double _panjang) {
        this._panjang = _panjang;
    }

    public int get_idbahan() {
        return _idbahan;
    }

    public void set_idbahan(int _idbahan) {
        this._idbahan = _idbahan;
    }

    public double get_hargaM2() {
        return _hargaM2;
    }

    public void set_hargaM2(double _hargaM2) {
        this._hargaM2 = _hargaM2;
    }

    public double get_modalperroll() {
        return _modalperroll;
    }

    public void set_modalperroll(double _modalperroll) {
        this._modalperroll = _modalperroll;
    }

    public double get_qty() {
        return _qty;
    }

    public void set_qty(double _qty) {
        this._qty = _qty;
    }

    public double get_jualroll() {
        return _jualroll;
    }

    public void set_jualroll(double _jualroll) {
        this._jualroll = _jualroll;
    }

    public double get_jumlahprofitkotor() {
        return _jumlahprofitkotor;
    }

    public void set_jumlahprofitkotor(double _jumlahprofitkotor) {
        this._jumlahprofitkotor = _jumlahprofitkotor;
    }

    public double get_transportasi() {
        return _transportasi;
    }

    public void set_transportasi(double _transportasi) {
        this._transportasi = _transportasi;
    }

    public double get_komisisalesprosen() {
        return _komisisalesprosen;
    }

    public void set_komisisalesprosen(double _komisisalesprosen) {
        this._komisisalesprosen = _komisisalesprosen;
    }

    public double get_komisisalesnominal() {
        return _komisisalesnominal;
    }

    public void set_komisisalesnominal(double _komisisalesnominal) {
        this._komisisalesnominal = _komisisalesnominal;
    }

    public double get_netprofit() {
        return _netprofit;
    }

    public void set_netprofit(double _netprofit) {
        this._netprofit = _netprofit;
    }
}
