package com.job.nutriplayapp.models;

public class Juego {

    private String id;
    private Boolean estado;
    private String pregunta, titulo, respuesta, tipo;
    private Integer puntos;

    public Juego() {
    }

    public Juego(String id, Boolean estado, String pregunta, String titulo, String respuesta, String tipo, Integer puntos) {
        this.id = id;
        this.estado = estado;
        this.pregunta = pregunta;
        this.titulo = titulo;
        this.respuesta = respuesta;
        this.tipo = tipo;
        this.puntos = puntos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
}