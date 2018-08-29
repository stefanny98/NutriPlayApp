package com.job.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pregunta implements Parcelable{

    private String id, pregunta,tipo;
    private int puntos;
    private Alternativa alternativa1, alternativa2, alternativa3;

    public Pregunta() {
    }

    public Pregunta(String id, String pregunta, String tipo, int puntos, Alternativa alternativa1, Alternativa alternativa2, Alternativa alternativa3) {
        this.id = id;
        this.pregunta = pregunta;
        this.tipo = tipo;
        this.puntos = puntos;
        this.alternativa1 = alternativa1;
        this.alternativa2 = alternativa2;
        this.alternativa3 = alternativa3;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Alternativa getAlternativa1() {
        return alternativa1;
    }

    public void setAlternativa1(Alternativa alternativa1) {
        this.alternativa1 = alternativa1;
    }

    public Alternativa getAlternativa2() {
        return alternativa2;
    }

    public void setAlternativa2(Alternativa alternativa2) {
        this.alternativa2 = alternativa2;
    }

    public Alternativa getAlternativa3() {
        return alternativa3;
    }

    public void setAlternativa3(Alternativa alternativa3) {
        this.alternativa3 = alternativa3;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id='" + id + '\'' +
                ", pregunta='" + pregunta + '\'' +
                ", tipo='" + tipo + '\'' +
                ", puntos=" + puntos +
                ", alternativa1=" + alternativa1 +
                ", alternativa2=" + alternativa2 +
                ", alternativa3=" + alternativa3 +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pregunta);
        dest.writeString(tipo);
        dest.writeInt(puntos);
        dest.writeParcelable(alternativa1,flags);
        dest.writeParcelable(alternativa2,flags);
        dest.writeParcelable(alternativa3,flags);
    }

    public Pregunta(Parcel in) {
        id = in.readString();
        pregunta = in.readString();
        tipo = in.readString();
        puntos = in.readInt();
        alternativa1 = in.readParcelable(getClass().getClassLoader());
        alternativa2 = in.readParcelable(getClass().getClassLoader());
        alternativa3 = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
        @Override
        public Pregunta createFromParcel(Parcel in) {
            return new Pregunta(in);
        }

        @Override
        public Pregunta[] newArray(int size) {
            return new Pregunta[size];
        }
    };

}