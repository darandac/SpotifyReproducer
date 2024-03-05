package com.example.spotify7;
import android.os.Parcel;
import android.os.Parcelable;

public class Cancion implements Parcelable {
    private String titulo;
    private String escritor;
    private String productor;
    private String duracion;

    public Cancion(String titulo, String escritor, String productor, String duracion) {
        this.titulo = titulo;
        this.escritor = escritor;
        this.productor = productor;
        this.duracion = duracion;
    }

    // Métodos necesarios para implementar Parcelable
    protected Cancion(Parcel in) {
        titulo = in.readString();
        escritor = in.readString();
        productor = in.readString();
        duracion = in.readString();
    }

    public static final Creator<Cancion> CREATOR = new Creator<Cancion>() {
        @Override
        public Cancion createFromParcel(Parcel in) {
            return new Cancion(in);
        }

        @Override
        public Cancion[] newArray(int size) {
            return new Cancion[size];
        }
    };

    // Implementar Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(escritor);
        dest.writeString(productor);
        dest.writeString(duracion);
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getEscritor() {
        return escritor;
    }

    public String getProductor() {
        return productor;
    }

    public String getDuracion() {
        return duracion;
    }
}
