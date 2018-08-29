package com.job.nutriplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Contenido implements Parcelable{

    private String id,texto1,texto2,texto3,texto4,textoPrincipal;
    private String imagen1,imagen2,imagen3,imagen4,imagenPrincipal;
    private String subtitulo1,subtitulo2,subtitulo3,subtitulo4;

    public Contenido() {
    }

    public Contenido(String id, String texto1, String texto2, String texto3, String texto4, String textoPrincipal, String imagen1, String imagen2, String imagen3, String imagen4, String imagenPrincipal, String subtitulo1, String subtitulo2, String subtitulo3, String subtitulo4) {
        this.id = id;
        this.texto1 = texto1;
        this.texto2 = texto2;
        this.texto3 = texto3;
        this.texto4 = texto4;
        this.textoPrincipal = textoPrincipal;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
        this.imagenPrincipal = imagenPrincipal;
        this.subtitulo1 = subtitulo1;
        this.subtitulo2 = subtitulo2;
        this.subtitulo3 = subtitulo3;
        this.subtitulo4 = subtitulo4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto1() {
        return texto1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }

    public String getTexto2() {
        return texto2;
    }

    public void setTexto2(String texto2) {
        this.texto2 = texto2;
    }

    public String getTexto3() {
        return texto3;
    }

    public void setTexto3(String texto3) {
        this.texto3 = texto3;
    }

    public String getTexto4() {
        return texto4;
    }

    public void setTexto4(String texto4) {
        this.texto4 = texto4;
    }

    public String getTextoPrincipal() {
        return textoPrincipal;
    }

    public void setTextoPrincipal(String textoPrincipal) {
        this.textoPrincipal = textoPrincipal;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen3() {
        return imagen3;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }

    public String getImagen4() {
        return imagen4;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }

    public String getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(String imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public String getSubtitulo1() {
        return subtitulo1;
    }

    public void setSubtitulo1(String subtitulo1) {
        this.subtitulo1 = subtitulo1;
    }

    public String getSubtitulo2() {
        return subtitulo2;
    }

    public void setSubtitulo2(String subtitulo2) {
        this.subtitulo2 = subtitulo2;
    }

    public String getSubtitulo3() {
        return subtitulo3;
    }

    public void setSubtitulo3(String subtitulo3) {
        this.subtitulo3 = subtitulo3;
    }

    public String getSubtitulo4() {
        return subtitulo4;
    }

    public void setSubtitulo4(String subtitulo4) {
        this.subtitulo4 = subtitulo4;
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "id='" + id + '\'' +
                ", texto1='" + texto1 + '\'' +
                ", texto2='" + texto2 + '\'' +
                ", texto3='" + texto3 + '\'' +
                ", texto4='" + texto4 + '\'' +
                ", textoPrincipal='" + textoPrincipal + '\'' +
                ", imagen1='" + imagen1 + '\'' +
                ", imagen2='" + imagen2 + '\'' +
                ", imagen3='" + imagen3 + '\'' +
                ", imagen4='" + imagen4 + '\'' +
                ", imagenPrincipal='" + imagenPrincipal + '\'' +
                ", subtitulo1='" + subtitulo1 + '\'' +
                ", subtitulo2='" + subtitulo2 + '\'' +
                ", subtitulo3='" + subtitulo3 + '\'' +
                ", subtitulo4='" + subtitulo4 + '\'' +
                '}';
    }

    public Contenido(Parcel in) {
        id = in.readString();
        texto1 = in.readString();
        texto2 = in.readString();
        texto3 = in.readString();
        texto4 = in.readString();
        textoPrincipal = in.readString();
        imagen1 = in.readString();
        imagen2 = in.readString();
        imagen3 = in.readString();
        imagen4 = in.readString();
        imagenPrincipal = in.readString();
        subtitulo1 = in.readString();
        subtitulo2 = in.readString();
        subtitulo3 = in.readString();
        subtitulo4 = in.readString();
    }

    public static final Creator<Contenido> CREATOR = new Creator<Contenido>() {
        @Override
        public Contenido createFromParcel(Parcel in) {
            return new Contenido(in);
        }

        @Override
        public Contenido[] newArray(int size) {
            return new Contenido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(texto1);
        dest.writeString(texto2);
        dest.writeString(texto3);
        dest.writeString(texto4);
        dest.writeString(textoPrincipal);
        dest.writeString(imagen1);
        dest.writeString(imagen2);
        dest.writeString(imagen3);
        dest.writeString(imagen4);
        dest.writeString(imagenPrincipal);
        dest.writeString(subtitulo1);
        dest.writeString(subtitulo2);
        dest.writeString(subtitulo3);
        dest.writeString(subtitulo4);
    }
}
