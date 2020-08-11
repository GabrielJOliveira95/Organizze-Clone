package com.android.oliveiragabriel.meusgastos.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.android.oliveiragabriel.meusgastos.model.NewUser
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.activity_logar.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logar)

        btn_logar_user.setOnClickListener{
            logarUser()
        }
    }


    fun validarCampoLogar(): Boolean {

        val campoValidado: Boolean

        when {
            textInputEmailLogar.text.toString().isEmpty() -> {

                textInputLayoutEmailLogar.isErrorEnabled
                textInputLayoutEmailLogar.error = "Digite seu nome"
                campoValidado = false
            }
            textInputSenhaLogar.text.toString().isEmpty() -> {
                textInputLayoutEmailLogar.isErrorEnabled = false

                textInputLayoutSenhaLogar.isErrorEnabled
                textInputLayoutSenhaLogar.error = "Digite seu e-mail"
                campoValidado = false
            }
            else -> {
                textInputLayoutEmailLogar.isErrorEnabled = false
                textInputLayoutSenhaLogar.isErrorEnabled = false
                campoValidado = true

            }


        }
        return campoValidado

    }

    fun logarUser(){
         val email = textInputEmailLogar.text.toString()
         val senha = textInputSenhaLogar.text.toString()

        if (validarCampoLogar()){
            val user = NewUser()
            user.email = email
            user.passaword = senha

            val autenticar = FireBaseSetting.getFirebaseAuth()
            autenticar?.signInWithEmailAndPassword(user.email!!, user.passaword!!)?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this, "Logado", Toast.LENGTH_LONG).show()
                    acessarTelaInicial()

                }else{

                    try {
                        throw it.exception!!
                    }catch (e: FirebaseAuthInvalidCredentialsException){
                        textInputLayoutEmailLogar.isErrorEnabled
                        textInputLayoutSenhaLogar.isErrorEnabled
                        textInputLayoutEmailLogar.error = "E-mail ou senha inválido(s)"
                        textInputLayoutSenhaLogar.error = "E-mail ou senha inválido(s)"
                    }catch (e: FirebaseAuthInvalidUserException){
                        textInputLayoutEmailLogar.isErrorEnabled
                        textInputLayoutEmailLogar.error = "Usuário não cadastrado"
                    }

                }
            }
        }
    }

    fun acessarTelaInicial(){
        val fireBaseAuth = FireBaseSetting.getFirebaseAuth()
        val user = fireBaseAuth?.currentUser
        if (user != null){
            startActivity(Intent(this, InicialActivity::class.java))
        }
    }


    override fun onStart() {
        super.onStart()
        textInputSenhaLogar.setText("")
        textInputEmailLogar.setText("")

    }
    
}
