package com.tecsup.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PreguntaEstado implements Parcelable{
    private String alternativaCorrecta;
    private String alternativaMarcada;


    public PreguntaEstado() {
    }

    public PreguntaEstado(String alternativaCorrecta, String alternativaMarcada) {
        this.alternativaCorrecta = alternativaCorrecta;
        this.alternativaMarcada = alternativaMarcada;
    }

    protected PreguntaEstado(Parcel in) {
        alternativaCorrecta = in.readString();
        alternativaMarcada = in.readString();
    }

    public static final Creator<PreguntaEstado> CREATOR = new Creator<PreguntaEstado>() {
        @Override
        public PreguntaEstado createFromParcel(Parcel in) {
            return new PreguntaEstado(in);
        }

        @Override
        public PreguntaEstado[] newArray(int size) {
            return new PreguntaEstado[size];
        }
    };

    public String getAlternativaCorrecta() {
        return alternativaCorrecta;
    }

    public void setAlternativaCorrecta(String alternativaCorrecta) {
        this.alternativaCorrecta = alternativaCorrecta;
    }

    public String getAlternativaMarcada() {
        return alternativaMarcada;
    }

    public void setAlternativaMarcada(String alternativaMarcada) {
        this.alternativaMarcada = alternativaMarcada;
    }

    @Override
    public String toString() {
        return "PreguntaEstado{" +
                "alternativaCorrecta='" + alternativaCorrecta + '\'' +
                ", alternativaMarcada='" + alternativaMarcada + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alternativaCorrecta);
        dest.writeString(alternativaMarcada);
    }
}
