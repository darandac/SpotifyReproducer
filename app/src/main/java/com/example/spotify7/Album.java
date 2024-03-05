package com.example.spotify7;

import java.util.List;
import java.io.Serializable;


public class Album implements Serializable{
    private String titulo;
    private String imagen;
    private List<Cancion> listaCanciones;

    public Album(String titulo, String imagen, List<Cancion> listaCanciones) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.listaCanciones = listaCanciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public List<Cancion> getListaCanciones() {
        return listaCanciones;
    }
}
