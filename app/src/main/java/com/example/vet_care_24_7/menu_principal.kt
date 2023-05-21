package com.example.vet_care_24_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class menu_principal : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef02: DatabaseReference
    val PATH_USERS="users/"
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)


        var vetDom=findViewById<Button>(R.id.vetDom)
        var user = intent.getStringExtra("user")
        auth = Firebase.auth
        myRef = database.getReference(PATH_USERS+auth.currentUser!!.uid)
        myRef02 = database.getReference(PATH_USERS)
        //myRef02 = database.getReference(PATH_USERS)
        var tipo=""
        vetDom.setOnClickListener {
            val intent = Intent(this, seleccionarServ::class.java)
            startActivity(intent)
        }



        // Crea un ValueEventListener para escuchar cambios en el campo "tipo"
        val tipoListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Obtén el valor actual del campo "tipo"
                tipo = dataSnapshot.getValue(String::class.java).toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja cualquier error de cancelación aquí
            }
        }

        // Agrega el ValueEventListener a la referencia en la base de datos
        myRef.child("tipo").addValueEventListener(tipoListener)


        val serv = findViewById<Button>(R.id.serv)

        if (tipo !="cliente") {
            serv.visibility = View.VISIBLE
            serv.isEnabled = true
        } else {
            serv.visibility = View.INVISIBLE
            serv.isEnabled = false
        }

        serv.setOnClickListener {
            if (tipo !="cliente") {
                val intent = Intent(this, servicios::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "No disponible para clientes", Toast.LENGTH_LONG).show()


        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cerrarSesion->{
                auth.signOut()
                val intent = Intent(this,MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }
            R.id.Activo->{
                myRef.child("disponible").setValue(true)
                true

                Toast.makeText(this, "Su sesión esta Activa", Toast.LENGTH_LONG).show()
            }
            R.id.Inactivo->{
                myRef.child("disponible").setValue(false)
                myRef.child("toastMostrado").setValue(false)
                //personasNotificadas.remove(myRef.key)
                true
                Toast.makeText(this, "Su sesión esta inactiva", Toast.LENGTH_LONG).show()
            }

        }
        return super.onOptionsItemSelected(item)

    }




}