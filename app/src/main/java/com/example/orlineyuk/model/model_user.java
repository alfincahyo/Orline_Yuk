package com.example.orlineyuk.model;

public class model_user {

    String id;
    String nama;
    String nohp;
    String email;

    public model_user(){

    }

    public model_user(String id, String nama, String nohp, String email) {
        this.id = id;
        this.nama = nama;
        this.nohp = nohp;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public String getNohp() {
        return nohp;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
