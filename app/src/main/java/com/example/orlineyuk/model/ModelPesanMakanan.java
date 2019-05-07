package com.example.orlineyuk.model;

public class ModelPesanMakanan {

    String nama;
    String harga;
    String Jumlah;

    public ModelPesanMakanan(){

    }


    public ModelPesanMakanan(String nama, String harga, String jumlah) {
        this.nama = nama;
        this.harga = harga;
        Jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getJumlah() {
        return Jumlah;
    }
}
