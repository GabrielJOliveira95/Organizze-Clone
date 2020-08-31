package com.android.oliveiragabriel.meusgastos.model

import java.text.SimpleDateFormat

class MyData {

    companion object {
        val currentData = System.currentTimeMillis()
        val sdf = SimpleDateFormat("dd/MM/YYYY")
        var data: String? = null

        fun dataAtual(): String? {

            data = sdf.format(currentData)
            return data
        }

        fun mesAnoData(): String{

            val mesmAnoData = data?.split("/")
            val dia = mesmAnoData?.get(0)
            val ano = mesmAnoData?.get(1)

            return dia + ano
        }
    }
}