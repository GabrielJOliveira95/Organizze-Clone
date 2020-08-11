package com.android.oliveiragabriel.meusgastos.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.oliveiragabriel.meusgastos.R
import com.android.oliveiragabriel.meusgastos.model.Base64Converter
import com.android.oliveiragabriel.meusgastos.model.FireBaseSetting
import com.android.oliveiragabriel.meusgastos.model.NewUser
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_cadastrar.*

class CadastroActivity : AppCompatActivity() {

    lateinit var firebaserUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        btn_cadastrar_user.setOnClickListener {
            cadastrarUser()
        }
    }


    fun validarCampoCadastrar(): Boolean {

        val campoValidado: Boolean

        when {
            textInputNome.text.toString().isEmpty() -> {

                textInputLayoutNome.isErrorEnabled
                textInputLayoutNome.error = "Digite seu nome"
                campoValidado = false
            }
            textInputEmail.text.toString().isEmpty() -> {
                textInputLayoutNome.isErrorEnabled = false

                textInputLayoutEmail.isErrorEnabled
                textInputLayoutEmail.error = "Digite seu e-mail"
                campoValidado = false
            }
            textInputSenha.text.toString().isEmpty() -> {
                textInputLayoutEmail.isErrorEnabled = false
                campoValidado = false

                textInputLayoutSenha.isErrorEnabled
                textInputLayoutSenha.error = "Digite sua senha"
            }
            else -> {
                textInputLayoutNome.isErrorEnabled = false
                textInputLayoutEmail.isErrorEnabled = false
                textInputLayoutSenha.isErrorEnabled = false
                campoValidado = true

            }


        }
        return campoValidado

    }

    fun cadastrarUser() {
        if (validarCampoCadastrar()) {
            val nome = textInputNome.text.toString()
            val email = textInputEmail.text.toString()
            val senha = textInputSenha.text.toString()

            val user = NewUser()
            user.nome = nome
            user.email = email
            user.passaword = senha
            val cadastrar = FireBaseSetting.getFirebaseAuth()

            cadastrar?.createUserWithEmailAndPassword(user.email!!, user.passaword!!)
                ?.addOnCompleteListener {

                    if (it.isSuccessful) {
                        firebaserUser = cadastrar.currentUser!!
                        Toast.makeText(this, "sucesso", Toast.LENGTH_LONG).show()
                        val idCodificado = Base64Converter.codificarBase(user.email!!)
                        user.id = idCodificado
                        user.salvarNewUser()
                        acessarTelaInicial()
                    } else {
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthUserCollisionException) {
                            textInputLayoutEmail.isErrorEnabled
                            textInputLayoutEmail.error = "Esse e-mail já está cadastrado"
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            textInputLayoutSenha.isErrorEnabled
                            textInputLayoutSenha.error = "Digite uma senha com no mínimo 6 digitos"

                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            textInputLayoutEmail.isErrorEnabled
                            textInputLayoutEmail.error = "Digite um e-mail válido"
                        }
                    }
                }


        }
    }

    fun acessarTelaInicial() {
        if (firebaserUser != null) {
            startActivity(Intent(this, InicialActivity::class.java))
        }
    }
}
