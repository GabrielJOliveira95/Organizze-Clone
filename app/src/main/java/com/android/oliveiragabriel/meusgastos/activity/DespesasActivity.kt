package com.android.oliveiragabriel.meusgastos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.MyData
import com.android.oliveiragabriel.meusgastos.model.NewDespesas
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


    fun salvarDespesas(){

        val despesas = NewDespesas()
        despesas.valor = editTextValorDespesas.text.toString()
        despesas.dataDespesa = editTextDataDespesas.text.toString()
        despesas.categoria = editTextCategoriaDespesas.text.toString()
        despesas.descricao = editTexDescricaoDespesas.text.toString()
        despesas.salvarNovaDespesa()


    }
}