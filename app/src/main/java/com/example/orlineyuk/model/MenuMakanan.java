package com.example.orlineyuk.model;

public class MenuMakanan {

    String id;
    String namaMakanan;
    int hargaMakanan;

    public MenuMakanan(){

    }

    public MenuMakanan(String id, String namaMakanan, int hargaMakanan) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = hargaMakanan;
    }

    public String getId() {
        return id;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public int getHargaMakanan() {
        return hargaMakanan;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public void setHargaMakanan(int hargaMakanan) {
        this.hargaMakanan = hargaMakanan;
    }
}
