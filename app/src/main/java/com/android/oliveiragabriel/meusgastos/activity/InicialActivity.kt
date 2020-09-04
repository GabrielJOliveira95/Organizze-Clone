package com.android.oliveiragabriel.meusgastos.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.Base64Converter
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.android.oliveiragabriel.meusgastos.model.NewUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_inicial.*
import kotlinx.android.synthetic.main.content_inicial.*

class InicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)
        setSupportActionBar(toolbar)

        fab_despesas.setOnClickListener {
            goDespesasActivity()
        }

        fab_receitas.setOnClickListener {
            goReceitasActivity()
        }

        calendarSetting()

        recuperarDadosUser()

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

    fun calendarSetting(){
        val dias = arrayOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
        val meses = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
        calendarView.setTitleMonths(meses)
        calendarView.setWeekDayLabels(dias)

        calendarView.setOnMonthChangedListener { _: MaterialCalendarView, calendarDay: CalendarDay ->
            Log.i("DATA", "DATA ${calendarDay.day} ${calendarDay.month + 1} ${calendarDay.year}")
        }
    }

    fun recuperarDadosUser(){
        val database = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email.toString()).replace("\n", "")

        database?.child("users")?.child(idUser)?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
               Log.i("ERRO", error.message)
               Log.i("ERRO", error.details)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(NewUser::class.java)
                val receita: Double
                val despesa: Double

                nomeInicial.text = user?.nome//.split(" ", "").toString()
                receita = user?.entradaDinheiro!!
                despesa = user.despesas
                saldoInicial.text = "R$ " + (receita - despesa).toString()



            }
        })
    }

    override fun onBackPressed() {
        sair()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        with(item?.itemId){
            if (this == R.id.sair){
              sair()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
