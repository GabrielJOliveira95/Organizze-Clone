package com.android.oliveiragabriel.meusgastos.activity

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.FireBaseAuth
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_inicial.*

class InicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }


    fun sair() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle("Sair")
        alertDialog.setMessage("Tem certeza de que deseja sair?")
        alertDialog.setNegativeButton("Não", null)
        alertDialog.setPositiveButton("Sim") { _: DialogInterface, _: Int ->

            val fireBaseAuth = FireBaseAuth.getFirebase()
            fireBaseAuth?.signOut()
            finish()
        }
        alertDialog.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        sair()
    }
}
