package com.android.oliveiragabriel.meusgastos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesas.*
import kotlinx.android.synthetic.main.activity_receita.*

class ReceitaActivity : AppCompatActivity() {
    private var receitaRecuperadaEditText = 0.0
    private var receitaFirebase = 0.0
    private var despesaTotal = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        editTexDataReceita.setText(MyData.dataAtual())
        fabReceita.setOnClickListener {
            salvarReceita()
        }
        recuperarReceita()

    }

    fun validarCamposSalvarReceita(): Boolean {

        val campoValidado: Boolean

        when {
            editTextValorReceita.text.toString().isEmpty() -> {
                editLayoutDataReceita.isErrorEnabled = false
                editLayoutDescricaoReceita.isErrorEnabled = false

                editTextValorReceita.error = "Digite o valor da despesa"
                campoValidado = false
            }
            editTexDataReceita.text.toString().isEmpty() -> {

                editLayoutDataReceita.isErrorEnabled
                editLayoutDataReceita.error = "Digite a data"
                campoValidado = false
            }
            editTexCategoriaReceita.text.toString().isEmpty() -> {
                editLayoutDataReceita.isErrorEnabled = false

                editLayoutCategoriaReceita.isErrorEnabled
                editLayoutCategoriaReceita.error = "Digite a categoria"
                campoValidado = false
            }
            editTexDescricaoReceita.text.toString().isEmpty() -> {
                editLayoutCategoriaReceita.isErrorEnabled = false

                editLayoutDescricaoReceita.isErrorEnabled
                editLayoutDescricaoReceita.error = "Digite a descriçao"
                campoValidado = false
            }
            else -> {
                editLayoutDataReceita.isErrorEnabled = false
                editLayoutCategoriaReceita.isErrorEnabled = false
                editLayoutDescricaoReceita.isErrorEnabled = false
                campoValidado = true

            }
        }
        return campoValidado

    }

    fun salvarReceita() {

        if (validarCamposSalvarReceita()) {
            val receita = Movimentacoes()
            receita.tipo = "credito"
            receitaRecuperadaEditText = (editTextValorReceita.text.toString()).toDouble()
            receita.valor = receitaRecuperadaEditText
            receita.data = editTexDataReceita.text.toString()
            receita.categoria = editTexCategoriaReceita.text.toString()
            receita.descricao = editTexDescricaoReceita.text.toString()

            despesaTotal = receitaRecuperadaEditText + receitaFirebase
            receita.salvar(this, editTexDataReceita.text.toString())
            atualizarReceita(despesaTotal)
            finish()
        }
    }

    fun recuperarReceita() {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()

        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email.toString()).replace("\n", "")

        dataBase?.child("users")?.child(idUser)?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(NewUser::class.java)
                receitaFirebase = user?.entradaDinheiro!!
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun atualizarReceita(valor: Double) {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email.toString()).replace("\n", "")

        dataBase?.child("users")?.child(idUser)?.child("entradaDinheiro")?.setValue(valor)
    }
}




