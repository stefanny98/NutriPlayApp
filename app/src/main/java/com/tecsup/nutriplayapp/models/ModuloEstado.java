package com.tecsup.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModuloEstado implements Parcelable{
    private boolean estado;
    private PreguntaEstado pregunta1, pregunta2, pregunta3;

    public ModuloEstado() {
    }

    public ModuloEstado(boolean estado, PreguntaEstado pregunta1, PreguntaEstado pregunta2, PreguntaEstado pregunta3) {
        this.estado = estado;
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
    }

    public ModuloEstado(Parcel in) {
        estado = in.readByte() != 0;
        pregunta1 = in.readParcelable(PreguntaEstado.class.getClassLoader());
        pregunta2 = in.readParcelable(PreguntaEstado.class.getClassLoader());
        pregunta3 = in.readParcelable(PreguntaEstado.class.getClassLoader());
    }

    public static final Creator<ModuloEstado> CREATOR = new Creator<ModuloEstado>() {
        @Override
        public ModuloEstado createFromParcel(Parcel in) {
            return new ModuloEstado(in);
        }

        @Override
        public ModuloEstado[] newArray(int size) {
            return new ModuloEstado[size];
        }
    };

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public PreguntaEstado getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(PreguntaEstado pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public PreguntaEstado getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(PreguntaEstado pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public PreguntaEstado getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(PreguntaEstado pregunta3) {
        this.pregunta3 = pregunta3;
    }

    @Override
    public String toString() {
        return "ModuloEstado{" +
                "estado=" + estado +
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
        dest.writeByte((byte) (estado ? 1 : 0));
        dest.writeParcelable(pregunta1, flags);
        dest.writeParcelable(pregunta2, flags);
        dest.writeParcelable(pregunta3, flags);
    }
}
