package com.grupo13.parqueo;

public class Ubicacion {
    int idParqueo;
    String nomParqueo;
    float longitud, latitud;

    public Ubicacion(int id, String nom, float lon, float lat){
        this.idParqueo=id;
        this.nomParqueo=nom;
        this.longitud=lon;
        this.latitud=lat;
    }

    public int getIdParqueo() {
        return idParqueo;
    }

    public void setIdParqueo(int idParqueo) {
        this.idParqueo = idParqueo;
    }

    public String getNomParqueo() {
        return nomParqueo;
    }

    public void setNomParqueo(String nomParqueo) {
        this.nomParqueo = nomParqueo;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }
}
