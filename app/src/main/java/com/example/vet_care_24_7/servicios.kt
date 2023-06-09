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
    private lateinit var cons: CheckBox
    private lateinit var Urgen: CheckBox
    private lateinit var Vac: CheckBox
    private lateinit var Hosp: CheckBox
    private lateinit var Cir: CheckBox
    private lateinit var An: CheckBox
    private lateinit var Trata: CheckBox
    private lateinit var Con: CheckBox
    private lateinit var Despara: CheckBox
    private lateinit var Dermatol: CheckBox
    private lateinit var Geria: CheckBox
    private lateinit var general: CheckBox
    private lateinit var cancelar: Button
    private lateinit var env: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        auth = Firebase.auth
        myRef = database.getReference(PATH_USERS+auth.currentUser!!.uid)
        myRef02 = database.getReference(PATH_USERS)
        cons = findViewById(R.id.Consultas)
        Urgen = findViewById(R.id.Urgen)
        Vac = findViewById(R.id.Vac)
        Hosp = findViewById(R.id.Hosp)
        Cir = findViewById(R.id.Cir)
        An = findViewById(R.id.An)
        Trata = findViewById(R.id.Trata)
        Con = findViewById(R.id.Con)
        Despara = findViewById(R.id.Despara)
        Dermatol = findViewById(R.id.Dermatol)
        Geria = findViewById(R.id.Geria)
        general = findViewById(R.id.general)
        cancelar = findViewById(R.id.cancelarVet)
        env = findViewById(R.id.enviarVet)


        val cancelar=findViewById<Button>(R.id.cancelarVet)
        val env=findViewById<Button>(R.id.enviarVet)

        cancelar.setOnClickListener{
            val intent = Intent(this, menu_principal::class.java)
            startActivity(intent)

        }

        env.setOnClickListener{

            mirarCajas()


            val intent = Intent(this, menu_principal::class.java)
            startActivity(intent)

        }


    }

    private fun mirarCajas() {
        val serviciosSeleccionados = ArrayList<String>()

        if (cons.isChecked) {
            serviciosSeleccionados.add(cons.text.toString())
        }
        if (Urgen.isChecked) {
            serviciosSeleccionados.add(Urgen.text.toString())
        }
        if (Vac.isChecked) {
            serviciosSeleccionados.add(Vac.text.toString())
        }
        if (Hosp.isChecked) {
            serviciosSeleccionados.add(Hosp.text.toString())
        }
        if (Cir.isChecked) {
            serviciosSeleccionados.add(Cir.text.toString())
        }
        if (An.isChecked) {
            serviciosSeleccionados.add(An.text.toString())
        }
        if (Trata.isChecked) {
            serviciosSeleccionados.add(Trata.text.toString())
        }
        if (Con.isChecked) {
            serviciosSeleccionados.add(Con.text.toString())
        }
        if (Despara.isChecked) {
            serviciosSeleccionados.add(Despara.text.toString())
        }
        if (Dermatol.isChecked) {
            serviciosSeleccionados.add(Dermatol.text.toString())
        }
        if (Geria.isChecked) {
            serviciosSeleccionados.add(Geria.text.toString())
        }
        if (general.isChecked) {
            serviciosSeleccionados.add(general.text.toString())
        }

        agregarServ(serviciosSeleccionados)
    }

    private fun agregarServ(servicios: ArrayList<String>) {
        val servicioRef = myRef.child("servicio")

        // Obtener la lista actual del nodo "servicio"
        servicioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val disponibleList = dataSnapshot.getValue(object : GenericTypeIndicator<ArrayList<String>>() {})

                    // Verificar si la lista es nula y crear una nueva lista en caso afirmativo
                    val updatedList = disponibleList ?: ArrayList()

                    // Agregar los servicios seleccionados a la lista
                    updatedList.addAll(servicios)

                    // Eliminar duplicados utilizando un conjunto
                    val uniqueSet = LinkedHashSet(updatedList)
                    updatedList.clear()
                    updatedList.addAll(uniqueSet)

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

            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al leer los datos de Firebase
            }
        })
    }


}