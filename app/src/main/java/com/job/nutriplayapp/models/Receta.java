package com.job.nutriplayapp.models;

public class Receta {

    private String id;
    private String titulo;
    private String descripcion;
    private String ingredientes;
    private String contenido;
    private String imagen;
    private String tipo;
    private int moneda;

    public Receta(){

    }

    public Receta(String id, String titulo, String descripcion, String ingredientes, String contenido, String imagen, String tipo, int moneda) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.contenido = contenido;
        this.imagen = imagen;
        this.tipo = tipo;
        this.moneda = moneda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }
}
