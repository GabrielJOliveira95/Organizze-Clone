package com.android.oliveiragabriel.meusgastos.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.adapter.AdapterRecyclerView
import com.android.oliveiragabriel.meusgastos.model.Base64Converter
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.android.oliveiragabriel.meusgastos.model.Movimentacoes
import com.android.oliveiragabriel.meusgastos.model.NewUser
import com.google.firebase.database.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_inicial.*
import kotlinx.android.synthetic.main.content_inicial.*
import java.text.DecimalFormat

class InicialActivity : AppCompatActivity() {

    private var saldoTotal = 0.0
    private lateinit var eventListener: ValueEventListener
    private lateinit var database: DatabaseReference
    private var mesano = ""
    private val listOfMovimentacoes = mutableListOf<Movimentacoes>()
    private lateinit var adapter: AdapterRecyclerView
    private var movimentacoes = Movimentacoes()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)
        setSupportActionBar(toolbar)

        mySwipe()
        myRecyclerView()

        fab_despesas.setOnClickListener {
            goDespesasActivity()
        }

        fab_receitas.setOnClickListener {
            goReceitasActivity()
        }

        progressBar()
        calendarSetting()


    }

    fun myRecyclerView() {
        adapter = AdapterRecyclerView(listOfMovimentacoes, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)
    }

    fun mySwipe() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    removerMovimentação(viewHolder)
                    Log.i("ITEM MOVIDO", "ITEM MOVIDO")
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    fun removerMovimentação(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Deletar")
        alertDialog.setMessage("Tem certeza de que deseja remover essa movimentação?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Sim") { _, _ ->
            val fireBase = FireBaseSetting.getFirebaseDataBase()
            val auth = FireBaseSetting.getFirebaseAuth()
            val email = auth?.currentUser?.email.toString()
            val idUser = Base64Converter.codificarBase(email).replace("\n", "")

            val position = viewHolder.adapterPosition
            val key = movimentacoes.id
            movimentacoes = listOfMovimentacoes[position]


            fireBase?.child("movimentacoes")?.child(idUser)?.child(mesano)?.child(key.toString())?.removeValue()

            adapter.notifyItemRemoved(position)



        }

        alertDialog.setNegativeButton(
            "Não"
        ) { _, _ ->
            adapter.notifyDataSetChanged()
        }
        alertDialog.create()
        alertDialog.show()
    }

    fun progressBar() {
        if (nomeInicial.text == "") {
            progressBar.visibility = View.VISIBLE
        } else if (nomeInicial.text != "") {
            progressBar.visibility = View.GONE
        }
    }

    fun sair() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle("Sair")
        alertDialog.setMessage("Tem certeza de que deseja sair?")
        alertDialog.setNegativeButton("Não", null)
        alertDialog.setIcon(R.drawable.ic_baseline_exit_to_app_24)
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

    fun calendarSetting() {
        val dias = arrayOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
        val meses = arrayOf(
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
        )
        calendarView.setTitleMonths(meses)
        calendarView.setWeekDayLabels(dias)
        val mes = calendarView.currentDate.month + 1
        val mesf = String.format("%02d", mes)
        val calendarAno = calendarView.currentDate.year
        mesano = "$mesf$calendarAno"

        calendarView.setOnMonthChangedListener { _: MaterialCalendarView, calendarDay: CalendarDay ->
            val mes = calendarDay.month + 1
            val mesf = String.format("%02d", mes)
            mesano = "$mesf${calendarDay.year}"
            recuperarTransacoes()
        }
    }


    fun recuperarTransacoes() {
        val databaseReference = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email.toString()
        val idUser = Base64Converter.codificarBase(email).replace("\n", "")
        listOfMovimentacoes.clear()
        databaseReference?.child("movimentacoes")?.child(idUser)?.child(mesano)
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        val transacoes: Movimentacoes = item.getValue(Movimentacoes::class.java)!!
                        movimentacoes.id = item.key
                        listOfMovimentacoes.add(transacoes)
                        adapter.notifyDataSetChanged()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("ERRO", "DETALHES ${error.details}")
                    Log.i("ERRO", "ERRO ${error.message}")
                }
            })
    }

    fun recuperarDadosUser() {
        database = FireBaseSetting.getFirebaseDataBase()!!
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email.toString()).replace("\n", "")

        eventListener = database.child("users").child(idUser)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.i("ERRO", error.message)
                    Log.i("ERRO", error.details)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(NewUser::class.java)
                    val numberFormat = DecimalFormat("00.00")

                    nomeInicial.text = "Olá, ${user?.nome?.split(" ")?.get(0)}"

                    saldoTotal = user?.entradaDinheiro!! - user.despesas
                    val saldoTotalString = numberFormat.format(saldoTotal)
                    saldoInicial.text = "R$ ${user.entradaDinheiro - user.despesas}"
                    progressBar()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUser()
        recuperarTransacoes()

    }

    override fun onStop() {
        database.removeEventListener(eventListener)
        super.onStop()
    }

    override fun onBackPressed() {
        sair()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        with(item?.itemId) {
            if (this == R.id.sair) {
                sair()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
