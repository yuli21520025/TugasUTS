package com.yulianti.tugasuts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.core.Context
import java.lang.reflect.Array.get

class RuangDataBuku(private val listbuku: ArrayList<DataBukuActivity>, context: ListDataBuku) :
    RecyclerView.Adapter<RuangDataBuku.ViewHolder>() {
    private val context: Context
    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Kode_Buku: TextView
        val Nama_Buku: TextView
        val Penerbit: TextView
        val ListItem: LinearLayout
        init {//Menginisialisasi View yang terpasang pada layout RecyclerView kita
            Kode_Buku = itemView.findViewById(R.id.kode_buku)
            Nama_Buku = itemView.findViewById(R.id.nama_buku)
            Penerbit = itemView.findViewById(R.id.penerbit)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {

        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.desain_tampilan, parent, false)
        return ViewHolder(V)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val Kode_Buku: String? = listbuku.get(position).kode_buku
        val Nama_Buku: String? = listbuku.get(position).nama_buku
        val Penerbit: String? = listbuku.get(position).penerbit

        holder.Kode_Buku.text = "Kode_Buku: $Kode_Buku"
        holder.Nama_Buku.text = "Nama_Buku: $Nama_Buku"
        holder.Penerbit.text = "Penerbit: $Penerbit"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {

                        holder.ListItem.setOnLongClickListener { view ->
                            val action = arrayOf("Update", "Delete")
                            val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                            alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                                when (i) {
                                    0 -> {
                                        /* Berpindah Activity pada halaman layout updateData dan mengambil data pada
                                       listMahasiswa, berdasarkan posisinya untuk dikirim pada activity selanjutnya */
                                        val bundle = Bundle()
                                        bundle.putString("dataKode_Buku", RuangDataBuku[position].kode_buku)
                                        bundle.putString("dataNama_Buku", RuangDataBuku[position].nama_buku)
                                        bundle.putString("dataPenerbit", RuangDataBuku[position].penerbit)
                                        bundle.putString("getPrimaryKey", RuangDataBuku[position].key)
                                        val intent = Intent(view.context, PerbaruiData::class.java)
                                        intent.putExtras(bundle)
                                        context.startActivity(intent)
                                    }
                                    1 -> {
                                        listener?.onDeleteData(ListDataBuku.get(position), position)

                                    }
                            })
                            alert.create()
                            alert.show()
                            true
                        }
                        return true;
                    }
                })
                return true
            }
        })
    }
    override fun getItemCount(): Int {
        return listbuku.size
        interface dataListener {
            fun onDeleteData(data: DataBukuActivity?, position: Int)
        }
        var listener: dataListener? = null
        //Membuat Konstruktor, untuk menerima input dari Database
        fun RecyclerViewAdapter(ListDataBuku: ArrayList<DataBukuActivity>: ArrayList<DataBukuActivity>?, context:
        Context?) {
            this.listbuku = ListDataBuku!!
            this.context = context!!
            listener = context as ListDataBuku?
        }


    }
    init {
        this.context = context
    }
}
