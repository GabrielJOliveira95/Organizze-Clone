package com.android.oliveiragabriel.meusgastos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesas.*

class DespesasActivity : AppCompatActivity() {
    private var despesaTotal = 0.0
    private var despesaAtual = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesas)

        editTextDataDespesas.setText(MyData.dataAtual())
        recuperarDespesa()

        salvarDespesas.setOnClickListener {
            salvarDespesas()
        }

    }

    fun validarCamposSalvarDespesas(): Boolean {

        val campoValidado: Boolean

        when {
            editTextValorDespesas.text.toString().isEmpty() -> {
                editLayoutDataDespesa.isErrorEnabled = false
                editLayoutDescricaoDespesas.isErrorEnabled = false

                editTextValorDespesas.error = "Digite o valor da despesa"
                campoValidado = false
            }
            editTextDataDespesas.text.toString().isEmpty() -> {

                editLayoutDataDespesa.isErrorEnabled
                editLayoutDataDespesa.error = "Digite a data"
                campoValidado = false
            }
            editTextCategoriaDespesas.text.toString().isEmpty() -> {
                editLayoutDataDespesa.isErrorEnabled = false

                editLayoutCategoriaDespesas.isErrorEnabled
                editLayoutCategoriaDespesas.error = "Digite a categoria"
                campoValidado = false
            }
            editTexDescricaoDespesas.text.toString().isEmpty() -> {
                editLayoutCategoriaDespesas.isErrorEnabled = false

                editLayoutDescricaoDespesas.isErrorEnabled
                editLayoutDescricaoDespesas.error = "Digite a descriçao"
                campoValidado = false
            }
            else -> {
                editLayoutDataDespesa.isErrorEnabled = false
                editLayoutCategoriaDespesas.isErrorEnabled = false
                editLayoutDescricaoDespesas.isErrorEnabled = false
                campoValidado = true

            }
        }
        return campoValidado

    }


    fun salvarDespesas() {

        if (validarCamposSalvarDespesas()) {
            val despesas = Movimentacoes()

            val despesaDigitada = editTextValorDespesas.text.toString()
            despesaAtual = despesaDigitada.toDoubleOrNull()!!
            despesas.valor = despesaTotal + despesaAtual
            despesas.data = editTextDataDespesas.text.toString()
            despesas.categoria = editTextCategoriaDespesas.text.toString()
            despesas.descricao = editTexDescricaoDespesas.text.toString()

            atualizarDespesas(despesas.valor!!)
            despesas.salvarNovaDespesa(this)
        }
    }

    fun atualizarDespesas(despesa: Double){
        val databaseReference = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email!!).replace("\n", "")
        databaseReference?.child("users")?.child(idUser)?.child("despesas")?.setValue(despesa)
    }

    fun recuperarDespesa(){

        val databaseReference = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val userId = Base64Converter.codificarBase(email!!)

        databaseReference?.child("users")?.child(userId)?.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(NewUser::class.java)
                despesaTotal = user?.despesas!!
            }
        })
    }


}