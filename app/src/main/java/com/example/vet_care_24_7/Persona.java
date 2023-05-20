package com.example.vet_care_24_7;

import java.util.List;

public class Persona {
    private String uid;
    private String nombre;
    private String apellido;
    private double numIdentificacion;
    private double latitud;
    private double longitud;
    private boolean disponible;

    private boolean toastMostrado;

    private String tipo;

    private long celular;

    private List<String> servicio;

    public Persona() {

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean getDisponible() {
        return disponible;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(double numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }


    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean getToastMostrado() {
        return toastMostrado;
    }

    public void setToastMostrado(boolean toastMostrado) {
        this.toastMostrado = toastMostrado;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public void setServicio(List<String> servicio) {
        this.servicio = servicio;
    }

    public List<String> getServicio() {
        return servicio;
    }


}

