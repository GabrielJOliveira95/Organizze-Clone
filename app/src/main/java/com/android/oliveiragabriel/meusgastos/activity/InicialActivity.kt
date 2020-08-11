package com.android.oliveiragabriel.meusgastos.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.android.oliveiragabriel.meusgastos.model.NewUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_inicial.*
import kotlinx.android.synthetic.main.content_inicial.*

class InicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)
        setSupportActionBar(toolbar)


        inserir.setOnClickListener{

            val base = FirebaseDatabase.getInstance().reference
            val user = NewUser()
            user.nome = "Gabriel"
            user.email = "gasgsdhgsdhgsudhgusd"
            user.id = "dsgsdgsd"
            base.child("usuario").child("001").setValue(user)

        }

        fab_despesas.setOnClickListener {
            goDespesasActivity()
        }

        fab_receitas.setOnClickListener {
            goReceitasActivity()
        }
    }


    fun sair() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle("Sair")
        alertDialog.setMessage("Tem certeza de que deseja sair?")
        alertDialog.setNegativeButton("Não", null)
        alertDialog.setPositiveButton("Sim") { _: DialogInterface, _: Int ->

            val fireBaseAuth = FireBaseSetting.getFirebaseAuth()
            fireBaseAuth?.signOut()
            finish()
        }
        alertDialog.create()
        alertDialog.show()
    }

    fun goDespesasActivity() {
        startActivity(Intent(applicationContext, DespesasActivity::class.java))
    }

    fun goReceitasActivity() {
        startActivity(Intent(applicationContext, ReceitaActivity::class.java))
    }

    override fun onBackPressed() {
        sair()
    }
}
