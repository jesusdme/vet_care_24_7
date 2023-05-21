package com.example.vet_care_24_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import UsuariosAdapter
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage



class ListaUsuariosActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var usuariosAdapter: UsuariosAdapter
    val PATH_USERS = "users/"
    var califica = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)


        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth



        val servList = intent.getStringArrayListExtra("arrayListKey")
        if (servList != null) {
            agregarServ(servList)
        }


        val listaUsuarios: MutableList<Persona> = mutableListOf()
        val recyclerViewUsuarios: RecyclerView = findViewById(R.id.recyclerViewUsuarios)
        usuariosAdapter =
            UsuariosAdapter(listaUsuarios, object : UsuariosAdapter.OnVerPosicionClickListener {
                override fun onVerPosicionClick(usuario: Persona) {
                    // Manejar el evento de clic en el botón "Ver posición" del usuario
                    val intent =
                        Intent(this@ListaUsuariosActivity, MapsActivityPorPersona::class.java)
                    intent.putExtra("uid", usuario.uid)
                    startActivity(intent)
                }
            })
        recyclerViewUsuarios.adapter = usuariosAdapter
        recyclerViewUsuarios.layoutManager = LinearLayoutManager(this)

        // Obtener la lista de usuarios desde la base de datos Firebase Realtime Database
        val pathUsuarios = "users"
        val usuariosRef = database.getReference(pathUsuarios)

        usuariosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaUsuarios.clear()

                //obtener la lista de servicios del usuario

                for (usuarioSnapshot in dataSnapshot.children) {
                    val usuario = usuarioSnapshot.getValue(Persona::class.java)
                    if (usuario != null) {

                        if (servList != null) {
                            califica = tienenElementosEnComun(
                                servList,
                                usuario.servicio as ArrayList<String>
                            )
                        }


                        if (usuario.disponible && usuario.tipo == "Vet" && califica == true)// &&califica==true)
                            usuario?.let {
                                listaUsuarios.add(usuario)
                            }
                    }
                }


                usuariosAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error de la lectura de la lista de usuarios
                Toast.makeText(
                    this@ListaUsuariosActivity,
                    "Error al obtener la lista de usuarios",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun agregarServ(servicios: ArrayList<String>) {
        var myRef = database.getReference(PATH_USERS + auth.currentUser!!.uid)

        val servicioRef = myRef.child("servicio")

        // Obtener la lista actual del nodo "servicio"
        servicioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val disponibleList =
                        dataSnapshot.getValue(object : GenericTypeIndicator<ArrayList<String>>() {})

                    if (disponibleList != null) {
                        tienenElementosEnComun(servicios, disponibleList)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al leer los datos de Firebase
            }
        })
    }

    fun tienenElementosEnComun(
        arrayList1: ArrayList<String>,
        arrayList2: ArrayList<String>
    ): Boolean {
        val intersection = ArrayList<String>(arrayList1)
        intersection.retainAll(arrayList2)

        return intersection.size > 0

    }

}
