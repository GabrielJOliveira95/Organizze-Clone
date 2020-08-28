package com.android.oliveiragabriel.meusgastos.model

import java.text.SimpleDateFormat

class MyData {

    companion object{

        val currentData = System.currentTimeMillis()
        val sdf = SimpleDateFormat("dd/MM/YYYY")
        var data: String? = null

        fun dataAtual(): String?{

            data = sdf.format(currentData)
            return data
        }
    }
}