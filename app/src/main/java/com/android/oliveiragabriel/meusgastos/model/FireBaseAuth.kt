package com.android.oliveiragabriel.meusgastos.model

import com.google.firebase.auth.FirebaseAuth

class FireBaseAuth {

    companion object{

        private var autenticar: FirebaseAuth? = null

        fun getFirebase(): FirebaseAuth? {

            if (autenticar == null) {
                autenticar = FirebaseAuth.getInstance()
            }

            return autenticar

        }
    }
}