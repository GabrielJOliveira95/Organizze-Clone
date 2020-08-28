package com.android.oliveiragabriel.meusgastos.model

class NewDespesas(
    var valor: String? = null,
    var dataDespesa: String? = null,
    var categoria: String? = null,
    var descricao: String? = null
) {
    fun salvarNovaDespesa() {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val idUser = Base64Converter.codificarBase(auth?.currentUser?.email!!).replace("\n", "")

        dataBase?.child("despesa")?.child(idUser)?.child("01-04")?.push()?.setValue(this)

    }
}