package com.android.oliveiragabriel.meusgastos.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.Movimentacoes
import java.text.DecimalFormat

class AdapterRecyclerView(mutableList: MutableList<Movimentacoes>, var context: Context) : RecyclerView.Adapter<ViewHolder>() {


    var lisfOfMovimentacoes = mutableListOf<Movimentacoes>()

    init {
        lisfOfMovimentacoes = mutableList
        lisfOfMovimentacoes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater : View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlayout, parent, false)

        return MyViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return lisfOfMovimentacoes.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is MyViewHolder){

            val valorFormatado = DecimalFormat("00.00")
            holder.txt_titulo.text = lisfOfMovimentacoes[position].categoria
            holder.txt_descricao.text = lisfOfMovimentacoes[position].descricao.toString()
            holder.txt_valor.text = valorFormatado.format(lisfOfMovimentacoes[position].valor)
            holder.txt_valor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))

            if (lisfOfMovimentacoes[position].tipo.equals("gastos")){
                holder.txt_valor.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                holder.txt_valor.text = "-${valorFormatado.format(lisfOfMovimentacoes[position].valor)}"
            }



        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txt_titulo = itemView.findViewById<TextView>(R.id.txt_titulo)
        var txt_descricao = itemView.findViewById<TextView>(R.id.txt_descricao)
        var txt_valor = itemView.findViewById<TextView>(R.id.txt_valor)
    }

}