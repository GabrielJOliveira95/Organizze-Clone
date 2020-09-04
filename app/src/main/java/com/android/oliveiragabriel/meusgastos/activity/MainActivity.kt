package com.android.oliveiragabriel.meusgastos.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import kotlinx.android.synthetic.main.activity_inicial.*

class MainActivity : IntroActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)


        manterLogado()
        addSlide()

    }


    fun addSlide() {

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro1)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro2)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro3)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro4)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.cadastro)
                .canGoForward(false)
                .build()
        )
    }

    fun cadastrar(view: View) {
        startActivity(Intent(this@MainActivity, CadastroActivity::class.java))
        Toast.makeText(this@MainActivity, "Cadastrar", Toast.LENGTH_SHORT).show()
    }

    fun logar(view: View) {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        Toast.makeText(this@MainActivity, "Cadastrar", Toast.LENGTH_SHORT).show()
    }

    fun manterLogado(){

        val fireBaseAuth = FireBaseSetting.getFirebaseAuth()
        val user = fireBaseAuth?.currentUser

        if (user != null){
            startActivity(Intent(this@MainActivity, InicialActivity::class.java))
        }
    }
}

