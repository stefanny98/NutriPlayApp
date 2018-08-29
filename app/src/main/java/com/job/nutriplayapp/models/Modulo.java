package com.job.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Modulo implements Parcelable {

    private String id, titulo, descripcion, picture;
    private Contenido contenido;
   // private ArrayList<Contenido> contenido;
    private Pregunta pregunta1, pregunta2, pregunta3;

    public Modulo(){}

    public Modulo(String id, String titulo, String descripcion, String picture, Contenido contenido, Pregunta pregunta1, Pregunta pregunta2, Pregunta pregunta3) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.picture = picture;
        this.contenido = contenido;
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    public Pregunta getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(Pregunta pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public Pregunta getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(Pregunta pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public Pregunta getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(Pregunta pregunta3) {
        this.pregunta3 = pregunta3;
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", picture='" + picture + '\'' +
                ", contenido=" + contenido +
                ", pregunta1=" + pregunta1 +
                ", pregunta2=" + pregunta2 +
                ", pregunta3=" + pregunta3 +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeString(picture);
        dest.writeParcelable(contenido,flags);
        dest.writeParcelable(pregunta1,flags);
        dest.writeParcelable(pregunta2,flags);
        dest.writeParcelable(pregunta3,flags);
    }

    public Modulo(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        picture = in.readString();
        contenido = in.readParcelable(getClass().getClassLoader());
        pregunta1 = in.readParcelable(getClass().getClassLoader());
        pregunta2 = in.readParcelable(getClass().getClassLoader());
        pregunta3 = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Modulo> CREATOR = new Creator<Modulo>() {
        @Override
        public Modulo createFromParcel(Parcel in) {
            return new Modulo(in);
        }

        @Override
        public Modulo[] newArray(int size) {
            return new Modulo[size];
        }
    };

}
