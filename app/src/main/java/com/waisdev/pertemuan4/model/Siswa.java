package com.waisdev.pertemuan4.model;

public class Siswa {

    private int id;
    private String nama;
    private String tempat_lahir;

    public Siswa(int id, String nama, String tempat_lahir) {
        this.id = id;
        this.nama = nama;
        this.tempat_lahir = tempat_lahir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }
}
