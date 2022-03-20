package com.yulianti.tugasuts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_perbarui_data.*

class PerbaruiData : AppCompatActivity() {
    //Deklarasi Variable
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekKode_Buku: String? = null
    private var cekNama_Buku: String? = null
    private var cekPenerbit: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perbarui_data)
        supportActionBar!!.title = "Update Data"
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekKode_Buku = new_kode_buku.getText().toString()
                cekNama_Buku = new_nama_buku.getText().toString()
                cekPenerbit = new_penerbit.getText().toString()
                if (isEmpty(cekKode_Buku!!) || isEmpty(cekNama_Buku!!) ||
                    isEmpty(cekPenerbit!!)) {
                    Toast.makeText(
                        this@PerbaruiData,
                        "Data harus terisi",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                   val setbuku = DataBukuActivity()
                    setbuku.kode_buku = new_kode_buku.getText().toString()
                    setbuku.nama_buku = new_nama_buku.getText().toString()
                    setbuku.penerbit = new_penerbit.getText().toString()
                    updateMahasiswa(setbuku)
                }
            }
        })
    }
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    private val data: Unit
        private get() {
            val getNIM = intent.extras!!.getString("dataKode_Buku")
            val getNama = intent.extras!!.getString("dataNama_Buku")
            val getJurusan = intent.extras!!.getString("dataPenerbit")
            new_kode_buku!!.setText(getNIM)
            new_nama_buku!!.setText(getNama)
            new_penerbit!!.setText(getJurusan)
        }
    private fun updateMahasiswa(mahasiswa: DataBukuActivity) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("Buku")
            .child(getKey!!)
            .setValue(mahasiswa)
            .addOnSuccessListener {
                new_kode_buku!!.setText("")
                new_nama_buku!!.setText("")
                new_penerbit!!.setText("")
                Toast.makeText(this@PerbaruiData, "Data Sukses",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}
