package com.ghtdeveloper.my_uasd_bani.modelo;


public class Materias
{
    //Variables
    private String id;
    private String materia;
    private String profesor;
    private String seccion;
    private String lugar;
    private String aula;

    //Constructor de la clase
    public Materias() {}//Se requiere

    //Getters And Setters
    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}//Fin de la clase  Materias
