package com.example.projectwiki;

public class AllDb {

    //API to get data in database
    public static final String SERVER_SEARCH_USER = "http://192.168.100.14projectwiki/getdataUser.php?cek=";
    public static final String SERVER_SEARCH_BARANG_URL = "http://192.168.100.14projectwiki/searchBarang.php?cek=";
    public static final String SERVER_SEARCH_BARANG_PENGAJUAN_URL = "http://192.168.100.14projectwiki/searchDataPengajuan.php?cek=";
    public static final String SERVER_SEARCH_DATA_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/searchDataPengembalian.php?cek=";



    public static final String SERVER_AllUSER_URL = "http://192.168.100.14projectwiki/getDataAllUser.php?cek=user";
    public static final String SERVER_LOGIN = "http://192.168.100.14projectwiki/log.php";
    public static final String SERVER_GET_DATA_BARANG_URL = "http://192.168.100.14projectwiki/getDataBarang.php";
    public static final String SERVER_GET_DATA_USER_UID_URL = "http://192.168.100.14projectwiki/getDataUser_withUID.php?cek=";
    public static final String SERVER_GET_DATA_BPINJAM_URL = "http://192.168.100.14projectwiki/getDataBPinjam.php";
    public static final String SERVER_GET_DATA_NOTIF_URL = "http://192.168.100.14projectwiki/getDataNotif.php?cek=";
    public static final String SERVER_GET_DATA_FAQ_URL = "http://192.168.100.14projectwiki/getDataFAQ.php";
    public static final String SERVER_GET_DATA_USER_NIS_URL = "http://192.168.100.14projectwiki/getDataUserWithNIS.php?cek=";
    public static final String SERVER_GET_DATA_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/getDataPengembalian.php";
    public static final String SERVER_GET_DATA_PENGAJUAN_URL = "http://192.168.100.14projectwiki/getDataPengajuan.php";
    public static final String SERVER_REG_URL = "http://192.168.100.14projectwiki/reg.php";
    public static final String SERVER_GET_DATA_URL = "http://192.168.100.14projectwiki/searchDataPinjaman.php?cek=";
    public static final String SERVER_REGISTER_URL = "https://192.168.100.14/projectmuri/register.php";
    public static final String SERVER_GET_HIGHLIGHT_BARANG_URL = "http://192.168.100.14projectwiki/getHighlightBarang.php";
    public static final String SERVER_COUNT_PINJAM_BARANG_URL = "http://192.168.100.14projectwiki/countBarangPinjaman.php?cek=";
    public static final String SERVER_COUNT_PINJAM_BARANG_URL2 = "http://192.168.100.14projectwiki/countBarangPinjaman2.php?cek=";
    public static final String SERVER_COUNT_PB_ADMIN_URL = "http://192.168.100.14projectwiki/countBarangPinjamanAdmin.php";
    public static final String SERVER_COUNT_PB_ADMIN_URL2 = "http://192.168.100.14projectwiki/countBarangPinjamanAdmin2.php";
    public static final String SERVER_COUNT_USERS_URL = "http://192.168.100.14projectwiki/countuser.php?cek=user";

    //API to insert or post data to database
    public static final String SERVER_INSERT_BPINJAM_URL = "http://192.168.100.14projectwiki/form_pengajuan.php";
    public static final String SERVER_POST_DATA_PENGAJUAN_URL = "http://192.168.100.14projectwiki/dataPengembalian.php";
    public static final String SERVER_INSERT_DATA_PEMINJAMAN_URL = "http://192.168.100.14projectwiki/dataPeminjamanAdmin.php";
    public static final String SERVER_INSERT_DATA_FORM_PENGAJUAN_URL = "http://192.168.100.14projectwiki/form_pengajuan.php";
    public static final String SERVER_INSERT_DATA_FORM_PENGAJUAN_ADMIN_URL = "http://192.168.100.14projectwiki/form_pengajuan_admin.php";
    public static final String SERVER_INSERT_DATA_FORM_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/form_pengembalian.php";
    public static final String SERVER_INSERT_DATA_NOTIF_URL = "http://192.168.100.14projectwiki/insert_notif.php";
    public static final String SERVER_INSERT_DATA_BARANG_URL = "http://192.168.100.14projectwiki/form_namabarang.php";
    public static final String SERVER_INSERT_DATA_FAQ_URL = "http://192.168.100.14projectwiki/form_faq.php";

    //API to update data in database
    public static final String SERVER_UPDATE_DATA_BARANG_URL = "http://192.168.100.14projectwiki/update_barang.php";
    public static final String SERVER_UPDATE_DATA_BARANG_ADMIN_URL = "http://192.168.100.14projectwiki/update_barang_admin.php";
    public static final String SERVER_UPDATE_DATA_BPINJAM_URL = "http://192.168.100.14projectwiki/update_bpinjam.php";
    public static final String SERVER_UPDATE_DATA_PENGAJUAN_URL = "http://192.168.100.14projectwiki/update_dafpengajuan.php";
    public static final String SERVER_UPDATE_DATA_USER_IMG_URL = "http://192.168.100.14projectwiki/update_user.php";
    public static final String SERVER_UPDATE_PASS_USER_URL = "http://192.168.100.14projectwiki/update_password.php";
    public static final String SERVER_UPDATE_DATA_USER_URL = "http://192.168.100.14projectwiki/update_data_pp_user.php";
    public static final String SERVER_UPDATE_DATA_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/update_pengembalian.php";

    public static final String SERVER_UPDATE_NAMA_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/update_untuknamauser/update_nama_pengembalian.php";
    public static final String SERVER_UPDATE_NAMA_FORM_PINJAM_URL = "http://192.168.100.14projectwiki/update_nama_form_pinjam.php";
    public static final String SERVER_UPDATE_NAMA_PINJAMAN_URL = "http://192.168.100.14projectwiki/update_untuknamauser/update_nama_pinjaman.php";


    //API to delete data in database
    public static final String SERVER_DELETE_DATA_PENGAJUAN_URL = "http://192.168.100.14projectwiki/delete_pengajuan.php";
    public static final String SERVER_DELETE_DATA_BARANG_URL = "http://192.168.100.14projectwiki/delete_barang.php";
    public static final String SERVER_DELETE_DATA_PENGEMBALIAN_URL = "http://192.168.100.14projectwiki/delete_pengembalian.php";

    public static final String SERVER_CHECK_PASS_USER_URL = "http://192.168.100.14projectwiki/projectwiki/test2.php";
}
