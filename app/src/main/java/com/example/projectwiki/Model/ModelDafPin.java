package com.example.projectwiki.Model;

public class ModelDafPin {

    String pid, bpid, uid, nama, nama_barang, tgl_pinjam, req_pinjam, keterangan, total_bpinjam, due_date, status, peminjaman;

    public ModelDafPin() {
        this.pid = pid;
        this.bpid = bpid;
        this.uid = uid;
        this.nama = nama;
        this.nama_barang = nama_barang;
        this.tgl_pinjam = tgl_pinjam;
        this.req_pinjam = req_pinjam;
        this.keterangan = keterangan;
        this.total_bpinjam = total_bpinjam;
        this.due_date = due_date;
        this.status = status;
        this.peminjaman = peminjaman;
    }

    public String getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(String peminjaman) {
        this.peminjaman = peminjaman;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBpid() {
        return bpid;
    }

    public void setBpid(String bpid) {
        this.bpid = bpid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getReq_pinjam() {
        return req_pinjam;
    }

    public void setReq_pinjam(String req_pinjam) {
        this.req_pinjam = req_pinjam;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTotal_bpinjam() {
        return total_bpinjam;
    }

    public void setTotal_bpinjam(String total_bpinjam) {
        this.total_bpinjam = total_bpinjam;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
