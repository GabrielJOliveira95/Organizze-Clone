package com.android.oliveiragabriel.meusgastos.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseSetting {

    companion object {
        private var firebaseDatabase: DatabaseReference? = null
        private var autenticar: FirebaseAuth? = null

        fun getFirebaseDataBase(): DatabaseReference? {

            if (firebaseDatabase == null) {
                firebaseDatabase = FirebaseDatabase.getInstance().reference
            }
            return firebaseDatabase
        }

        fun getFirebaseAuth(): FirebaseAuth? {

            if (autenticar == null) {
                autenticar = FirebaseAuth.getInstance()
            }

            return autenticar

        }
    }
}