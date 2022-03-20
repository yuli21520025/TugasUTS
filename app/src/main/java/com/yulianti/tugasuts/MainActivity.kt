package com.yulianti.tugasuts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        showdata.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.save -> {
               val getUserID = auth!!.currentUser!!.uid

                val database = FirebaseDatabase.getInstance()

                val getkode_buku: String = kode_buku.getText().toString()
                val getnama_buku: String = nama_buku.getText().toString()
                val getpenerbit: String = penerbit.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getkode_buku) || isEmpty(getnama_buku) || isEmpty(getpenerbit)) {

                    Toast.makeText(this@MainActivity, "Data harus diisi lengkap", Toast.LENGTH_SHORT).show()
                } else {

                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                        .setValue(DataBukuActivity(getkode_buku, getnama_buku, getpenerbit))
                        .addOnCompleteListener(this) {
                                    kode_buku.setText("")
                            nama_buku.setText("")
                            penerbit.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }


            R.id.logout ->

                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Berhasil keluar", Toast.LENGTH_SHORT)
                                .show()
                            intent = Intent(applicationContext, MasukActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            R.id.showdata -> {
                startActivity(Intent(this@MainActivity, ListDataBuku::class.java))
            }

        }
    }
}

