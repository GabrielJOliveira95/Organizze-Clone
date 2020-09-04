package com.android.oliveiragabriel.meusgastos.model

import android.content.Context
import android.widget.Toast
import com.android.oliveiragabriel.meusgastos.activity.DespesasActivity

class Movimentacoes(
    var valor: Double? = null,
    var data: String? = null,
    var categoria: String? = null,
    var descricao: String? = null
) {


    fun salvarNovaDespesa(context: Context) {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val idUser = Base64Converter.codificarBase(auth?.currentUser?.email!!).replace("\n", "")
        val mesAnoData = MyData.mesAnoData()

        dataBase?.child("despesa")?.child(idUser)?.child(mesAnoData)?.push()?.setValue(this)
            ?.addOnSuccessListener {
                Toast.makeText(context, "Despesa Salva", Toast.LENGTH_LONG).show()
            }

    }

    fun salvarNovaReceita(context: Context) {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val idUser = Base64Converter.codificarBase(auth?.currentUser?.email!!).replace("\n", "")
        val mesAnoData = MyData.mesAnoData()

        dataBase?.child("receita")?.child(idUser)?.child(mesAnoData)?.push()?.setValue(this)
            ?.addOnSuccessListener {
                Toast.makeText(context, "Despesa Salva", Toast.LENGTH_LONG).show()
            }
    }
}