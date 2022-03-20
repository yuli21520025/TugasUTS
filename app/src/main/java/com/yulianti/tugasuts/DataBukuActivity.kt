package com.yulianti.tugasuts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DataBukuActivity  {
    var kode_buku: String? = null
    var nama_buku: String? = null
    var penerbit: String? = null
    var key: String? = null
    constructor() {}
    constructor(nim: String?, nama: String?, jurusan: String?) {
        this.kode_buku = nim
        this.nama_buku = nama
        this.penerbit = jurusan
    }


}
