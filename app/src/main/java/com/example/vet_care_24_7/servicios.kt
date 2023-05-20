package com.example.vet_care_24_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.FirebaseDatabase

class servicios : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef02: DatabaseReference
    private val database = FirebaseDatabase.getInstance()
    val PATH_USERS="users/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        auth = Firebase.auth
        myRef = database.getReference(PATH_USERS+auth.currentUser!!.uid)
        myRef02 = database.getReference(PATH_USERS)
        val cons=findViewById<CheckBox>(R.id.Consultas)
        val Urgen=findViewById<CheckBox>(R.id.Urgen)
        val Vac=findViewById<CheckBox>(R.id.Vac)
        val Hosp=findViewById<CheckBox>(R.id.Hosp)
        val Cir=findViewById<CheckBox>(R.id.Cir)
        val An=findViewById<CheckBox>(R.id.An)
        val Trata=findViewById<CheckBox>(R.id.Trata)
        val Con=findViewById<CheckBox>(R.id.Con)
        val Despara=findViewById<CheckBox>(R.id.Despara)
        val Dermatol=findViewById<CheckBox>(R.id.Dermatol)
        val Geria=findViewById<CheckBox>(R.id.Geria)
        val general=findViewById<CheckBox>(R.id.general)


        val cancelar=findViewById<Button>(R.id.cancelarVet)
        val env=findViewById<Button>(R.id.enviarVet)

        cancelar.setOnClickListener{
            val intent = Intent(this, menu_principal::class.java)
            startActivity(intent)

        }

        env.setOnClickListener{
            if(cons?.isChecked==true)
            {
                agregarServ(cons.text.toString())
            }
            if(Urgen?.isChecked==true)
            {
                agregarServ(Urgen.text.toString())
            }
            if(Vac?.isChecked==true)
            {
                agregarServ(Vac.text.toString())
            }
            if(Hosp?.isChecked==true)
            {
                agregarServ(Hosp.text.toString())
            }
            if(Cir?.isChecked==true)
            {
                agregarServ(Cir.text.toString())
            }
            if(An?.isChecked==true)
            {
                agregarServ(An.text.toString())
            }
            if(Trata?.isChecked==true)
            {
                agregarServ(Trata.text.toString())
            }
            if(Con?.isChecked==true)
            {
                agregarServ(Con.text.toString())
            }
            if(Despara?.isChecked==true)
            {
                agregarServ(Despara.text.toString())
            }
            if(Dermatol?.isChecked==true)
            {
                agregarServ(Dermatol.text.toString())
            }
            if(Geria?.isChecked==true)
            {
                agregarServ(Geria.text.toString())
            }
            if(general?.isChecked==true)
            {
                agregarServ(general.text.toString())
            }


            val intent = Intent(this, menu_principal::class.java)
            startActivity(intent)

        }


    }
    private fun agregarServ(serv: String) {
        val servicioRef = myRef.child("servicio")

        // Obtener la lista actual del nodo "servicio"
        servicioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val disponibleList = dataSnapshot.getValue(object : GenericTypeIndicator<ArrayList<String>>() {})

                    // Verificar si la lista es nula y crear una nueva lista en caso afirmativo
                    val updatedList = disponibleList ?: ArrayList()

                    // Verificar si "Consulta" existe en la lista
                    if (!updatedList.contains(serv)) {
                        // Agregar "Consulta" a la lista
                        updatedList.add(serv)

                        // Actualizar la lista en Firebase
                        servicioRef.setValue(updatedList)
                            .addOnSuccessListener {
                                // La lista se actualizó correctamente
                            }
                            .addOnFailureListener { error ->
                                // Ocurrió un error al actualizar la lista
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al leer los datos de Firebase
            }
        })

    }

}