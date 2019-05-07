package com.example.orlineyuk.model;

public class MenuMinuman {

    String id;
    String namaMinuman;
    int hargaMinuman;

    public MenuMinuman(){

    }

    public MenuMinuman(String id, String namaMinuman, int hargaMinuman) {
        this.id = id;
        this.namaMinuman = namaMinuman;
        this.hargaMinuman = hargaMinuman;
    }

    public String getId() {
        return id;
    }

    public String getNamaMinuman() {
        return namaMinuman;
    }

    public int getHargaMinuman() {
        return hargaMinuman;
    }
}
