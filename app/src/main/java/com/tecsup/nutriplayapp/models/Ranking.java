package com.tecsup.nutriplayapp.models;

public class Ranking {

    private String id, nombre;
    private Integer exp;

    public Ranking (){

    }

    public Ranking(String id, String nombre, Integer exp) {
        this.id = id;
        this.nombre = nombre;
        this.exp = exp;
    }

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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", exp=" + exp +
                '}';
    }
}
