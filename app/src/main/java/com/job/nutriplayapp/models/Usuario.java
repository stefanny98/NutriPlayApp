package com.job.nutriplayapp.models;

public class Usuario {
    private String nombre;
    private String correo;
    private String avatar;
    private Integer exp;
    private Integer monedas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getMonedas() {
        return monedas;
    }

    public void setMonedas(Integer monedas) {
        this.monedas = monedas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", avatar='" + avatar + '\'' +
                ", exp=" + exp +
                ", monedas=" + monedas +
                '}';
    }
}
