package com.example.projectwiki.Model;

public class ModelPengembalian {

    String idpb, pid, bpid, uid, nama, nis, nama_barang, tgl_pinjam, req_pinjam, keterangan, jml_barang, status, img_pengembalian, due_date, tgl_penerimaan;

    public ModelPengembalian() {
        this.idpb = idpb;
        this.pid = pid;
        this.bpid = bpid;
        this.uid = uid;
        this.nama = nama;
        this.nis = nis;
        this.nama_barang = nama_barang;
        this.tgl_pinjam = tgl_pinjam;
        this.req_pinjam = req_pinjam;
        this.keterangan = keterangan;
        this.jml_barang = jml_barang;
        this.status = status;
        this.img_pengembalian = img_pengembalian;
        this.due_date = due_date;
        this.tgl_penerimaan = tgl_penerimaan;
    }


    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTgl_penerimaan() {
        return tgl_penerimaan;
    }

    public void setTgl_penerimaan(String tgl_penerimaan) {
        this.tgl_penerimaan = tgl_penerimaan;
    }

    public String getIdpb() {
        return idpb;
    }

    public void setIdpb(String idpb) {
        this.idpb = idpb;
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

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
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

    public String getJml_barang() {
        return jml_barang;
    }

    public void setJml_barang(String jml_barang) {
        this.jml_barang = jml_barang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg_pengembalian() {
        return img_pengembalian;
    }

    public void setImg_pengembalian(String img_pengembalian) {
        this.img_pengembalian = img_pengembalian;
    }
}
