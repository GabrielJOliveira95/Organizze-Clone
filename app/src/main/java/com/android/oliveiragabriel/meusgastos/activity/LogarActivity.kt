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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_logar.*

class LoginActivity : AppCompatActivity() {
    private lateinit var nome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logar)

        supportActionBar?.title = getString(R.string.login)
        btn_logar_user.setOnClickListener {
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

    fun logarUser() {
        val email = textInputEmailLogar.text.toString()
        val senha = textInputSenhaLogar.text.toString()

        if (validarCampoLogar()) {
            val user = NewUser()
            user.email = email
            user.passaword = senha

            val autenticar = FireBaseSetting.getFirebaseAuth()
            autenticar?.signInWithEmailAndPassword(user.email!!, user.passaword!!)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        acessarTelaInicial()

                    } else {

                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            textInputLayoutEmailLogar.isErrorEnabled
                            textInputLayoutSenhaLogar.isErrorEnabled
                            textInputLayoutEmailLogar.error = "E-mail ou senha inválido(s)"
                            textInputLayoutSenhaLogar.error = "E-mail ou senha inválido(s)"
                        } catch (e: FirebaseAuthInvalidUserException) {
                            textInputLayoutEmailLogar.isErrorEnabled
                            textInputLayoutEmailLogar.error = "Usuário não cadastrado"
                        }

                    }
                }
        }
    }

    fun acessarTelaInicial() {
        val dataBase = FireBaseSetting.getFirebaseDataBase()
        val auth = FireBaseSetting.getFirebaseAuth()
        val email = auth?.currentUser?.email
        val idUser = Base64Converter.codificarBase(email.toString()).replace("\n", "")

        dataBase?.child("users")?.child(idUser)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(NewUser::class.java)
                Toast.makeText(
                    applicationContext,
                    "Bem vindo de volta ${user?.nome}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        if (auth != null) {
            startActivity(Intent(this, InicialActivity::class.java))

        }
    }

    override fun onStart() {
        super.onStart()
        textInputSenhaLogar.setText("")
        textInputEmailLogar.setText("")

    }

}
