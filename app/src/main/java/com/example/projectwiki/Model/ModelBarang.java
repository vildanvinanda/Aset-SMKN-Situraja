package com.example.projectwiki.Model;

public class ModelBarang {

    private String bpid, nama_barang, jml_barang, deskripsi, tgl_upload, img_barang;

    public ModelBarang() {
        this.bpid = bpid;
        this.nama_barang = nama_barang;
        this.jml_barang = jml_barang;
        this.deskripsi = deskripsi;
        this.tgl_upload = tgl_upload;
        this.img_barang = img_barang;
    }

//    public ModelBarang(){
//
//    }

    public String getBpid() {
        return bpid;
    }

    public void setBpid(String bpid) {
        this.bpid = bpid;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getJml_barang() {
        return jml_barang;
    }

    public void setJml_barang(String jml_barang) {
        this.jml_barang = jml_barang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTgl_upload() {
        return tgl_upload;
    }

    public void setTgl_upload(String tgl_upload) {
        this.tgl_upload = tgl_upload;
    }

    public String getImg_barang() {
        return img_barang;
    }

    public void setImg_barang(String img_barang) {
        this.img_barang = img_barang;
    }
}
