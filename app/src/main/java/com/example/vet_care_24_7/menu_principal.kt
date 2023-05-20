package com.example.vet_care_24_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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

        vetDom.setOnClickListener {
            val intent = Intent(this, ListaUsuariosActivity::class.java)
            startActivity(intent)
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