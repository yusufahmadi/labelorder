package io.github.yusufahmadi.labelorder;

public class Bahan {

    private int _id;
    private String _nama;
    private int _harga;


    public Bahan (){
        this._id = 0;
        this._nama = "";
        this._harga=0;
    }

    public Bahan (int id,String Nama ,int Harga){
        this._id = id;
        this._nama = Nama;
        this._harga = Harga;
    }

    public void setId(int id){
        this._id = id;
    }

    public int getId(){
        return this._id;
    }


    public void setNama(String nama){
        this._nama = nama;
    }

    public String getNama(){
        return this._nama;
    }

    public void setHarga(int harga){
        this._harga = harga;
    }

    public int getHarga(){
        return this._harga;
    }

}
