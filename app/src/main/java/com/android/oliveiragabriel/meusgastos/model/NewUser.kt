package com.android.oliveiragabriel.meusgastos.model

import com.google.firebase.database.Exclude


class NewUser (@get:Exclude var id: String? = null, var nome: String? = null, var email: String? = null, @get:Exclude var passaword: String? = null, var despesas: Double = 0.0, var entradaDinheiro: Double = 0.0){

    fun salvarNewUser(){
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val id = this.id.toString().replace("\n", "")
        dataBase?.child("users")?.child(id)?.setValue(this)
    }

}

