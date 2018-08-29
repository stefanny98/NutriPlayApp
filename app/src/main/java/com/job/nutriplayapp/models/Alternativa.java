package com.job.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Alternativa implements Parcelable{

    private String id, nombre;
    private Boolean estado;


    public Alternativa() {
    }

    public Alternativa(String id, Boolean estado, String nombre) {
        this.id = id;
        this.estado = estado;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Alternativa{" +
                "id='" + id + '\'' +
                ", estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public Alternativa(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        byte tmpEstado = in.readByte();
        estado = tmpEstado == 0 ? null : tmpEstado == 1;
    }

    public static final Creator<Alternativa> CREATOR = new Creator<Alternativa>() {
        @Override
        public Alternativa createFromParcel(Parcel in) {
            return new Alternativa(in);
        }

        @Override
        public Alternativa[] newArray(int size) {
            return new Alternativa[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeByte((byte) (estado == null ? 0 : estado ? 1 : 2));
    }
}
