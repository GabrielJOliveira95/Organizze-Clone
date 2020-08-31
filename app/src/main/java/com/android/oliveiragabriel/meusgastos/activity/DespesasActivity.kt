package com.android.oliveiragabriel.meusgastos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.MyData
import com.android.oliveiragabriel.meusgastos.model.NewDespesas
import kotlinx.android.synthetic.main.activity_cadastrar.*
import kotlinx.android.synthetic.main.activity_despesas.*

class DespesasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesas)

        editTextDataDespesas.setText(MyData.dataAtual())

        salvarDespesas.setOnClickListener {
            salvarDespesas()
        }

    }


    fun salvarDespesas() {

        if (validarCamposSalvarDespesas()) {

            val despesas = NewDespesas()
            val despesasFloat = editTextDataDespesas.text.toString()
            despesas.valor = despesasFloat.toDouble()
            despesas.dataDespesa = editTextDataDespesas.text.toString()
            despesas.categoria = editTextCategoriaDespesas.text.toString()
            despesas.descricao = editTexDescricaoDespesas.text.toString()
            despesas.salvarNovaDespesa(this)
        }
    }

    fun validarCamposSalvarDespesas(): Boolean {

        val campoValidado: Boolean

        when {
            editTextValorDespesas.text.toString().isEmpty() -> {
                editLayoutDataDespesas.isErrorEnabled = false
                editLayoutDescricaoDespesas.isErrorEnabled = false

                editTextValorDespesas.error = "Digite o valor da despesa"
                campoValidado = false
            }
            editTextDataDespesas.text.toString().isEmpty() -> {

                editLayoutDataDespesas.isErrorEnabled
                editLayoutDataDespesas.error = "Digite a data"
                campoValidado = false
            }
            editTextCategoriaDespesas.text.toString().isEmpty() -> {
                editLayoutDataDespesas.isErrorEnabled = false

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
                editLayoutDataDespesas.isErrorEnabled = false
                editLayoutCategoriaDespesas.isErrorEnabled = false
                editLayoutDescricaoDespesas.isErrorEnabled = false
                campoValidado = true

            }
        }
        return campoValidado

    }
}