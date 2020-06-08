package com.grupo13.parqueo.modulo;

public class Ubicacion {
    private int idParqueo;
    private String nomParqueo;
    private float longitud, latitud;

    public Ubicacion(int idParqueo, String nomParqueo, float longitud, float latitud) {
        this.idParqueo = idParqueo;
        this.nomParqueo = nomParqueo;
        this.longitud = longitud;
        this.latitud = latitud;
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
