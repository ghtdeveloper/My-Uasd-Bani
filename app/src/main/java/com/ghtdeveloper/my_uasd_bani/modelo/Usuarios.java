package com.ghtdeveloper.my_uasd_bani.modelo;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Date;


public class Usuarios
{

    /**
     * Se definen las variables a utilizar
     */
    private  String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String facultad;
    private String carrera;
    private String claveAcceso;
    private Date fechaRegistro;
    private String urlFotoPerfil;

    /*
        Se define el constructor de la clase
     */
    public Usuarios( String nb, String ap, String co, String fc, String ca, String clave,
                    Date fechaRegistro)
    {
        this.nombre = nb;
        this.apellido = ap;
        this.correo = co;
        this.facultad = fc;
        this.carrera = ca;
        this.claveAcceso = clave;
        this.fechaRegistro = fechaRegistro;
    }//Fin del constructor de la clase

    public Usuarios(Context context) { }

    /*
        Se definen los getters & Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    ///*****************************************************************************************

    /**
     * Se sobreescribe el metodo toString para agregar la estructura de los
     * datos utilizando la notacion JSON
     */
    @NonNull
    @Override
    public String toString()
    {
        return"collect_usuarios{"+
                "id='"+id+'\''+
                ",nombre='"+nombre+'\''+
                ",apelido='"+apellido+'\''+
                ",correo='"+correo+'\''+
                ",facultad='"+facultad+'\''+
                ",carrera='"+carrera+'\''+
                ",claveAcceso='"+claveAcceso+'\''+
                ",fechaRegistro='"+fechaRegistro+'}';
    }//Fin del metodo toString


}//Fin de la class Usuarios
