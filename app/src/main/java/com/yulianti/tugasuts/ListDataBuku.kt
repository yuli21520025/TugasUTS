package com.yulianti.tugasuts

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListDataBuku : AppCompatActivity(), PerbaruiData.dataListener {
    //Deklarasi Variable untuk RecyclerView
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    //Deklarasi Variable Database Reference & ArrayList dengan Parameter Class Model kita.
    val database = FirebaseDatabase.getInstance()
    private var dataBuku = ArrayList<DataBukuActivity>()
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_buku)
        recyclerView = findViewById(R.id.datalist)
        supportActionBar!!.title = "Data Buku"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }
    private fun GetData() {
        Toast.makeText(applicationContext, "Mohon bersabar...",
            Toast.LENGTH_LONG).show()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("Admin").child(getUserID).child("Buku")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val buku =
                                snapshot.getValue(DataBukuActivity::class.java)
                            buku?.key = snapshot.key
                            DataBukuActivity.add(buku!!)
                        }
                        adapter = PerbaruiData(DataBukuActivity, this@ListDataBuku)
                        recyclerView?.adapter = adapter
                        (adapter as PerbaruiData).notifyDataSetChanged()
                        Toast.makeText(applicationContext,"Data Berhasil Dimuat",
                            Toast.LENGTH_LONG).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Data Gagal Dimuat",
                        Toast.LENGTH_LONG).show()
                    Log.e("ListDataBuku", databaseError.details + " " +
                            databaseError.message)
                }
            })
    }
    private fun MyRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(applicationContext,
            DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(applicationContext,
                R.drawable.garis_bawah)!!)
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun onDeleteData(data: DataBukuActivity?, position: Int) {

        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        val getKey = intent.extras!!.getString("getPrimaryKey")
        if(getReference != null){
            getReference.child("Admin")
                .child(getUserID)
                .child("Mahasiswa")
                .child(getKey!!)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this@ListDataBuku, "Data Berhasil Dihapus",
                        Toast.LENGTH_SHORT).show();
                    finish()
                }
        }
    }
}
