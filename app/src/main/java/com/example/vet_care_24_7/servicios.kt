package com.example.vet_care_24_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class servicios : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        auth = Firebase.auth

        val cancelar=findViewById<Button>(R.id.cancelarVet)
        val env=findViewById<Button>(R.id.enviarVet)

        cancelar.setOnClickListener{
            val intent = Intent(this, menu_principal::class.java)
            startActivity(intent)

        }



    }
}