package com.android.oliveiragabriel.meusgastos.model

import android.util.Base64
import java.util.*

class Base64Converter {

    companion object {

        fun codificarBase(email: String): String {
            return Base64.encodeToString(email.toByteArray(), Base64.DEFAULT).replace("('\\n||\\r", "")
        }

        fun decodificarBase(txtCodificado: String): String {
            return String(Base64.decode(txtCodificado, Base64.DEFAULT))
        }
    }
}