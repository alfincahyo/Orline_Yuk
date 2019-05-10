package com.example.orlineyuk.model;

public class ModelPesanMakanan {

    private String id;
    private String nama;
    private String email;
    private Integer harga;
    private Integer jumpesanan;
    private String StatPesan;

    public ModelPesanMakanan(){}

    public ModelPesanMakanan(String id, String nama, Integer jumpesanan, Integer harga, String statPesan, String email) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.harga = harga;
        this.jumpesanan = jumpesanan;
        StatPesan = statPesan;
    }

    public String getNama() {
        return nama;
    }

    public Integer getHarga() {
        return harga;
    }

    public Integer getJumpesanan() {
        return jumpesanan;
    }

    public String getStatPesan() {
        return StatPesan;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
