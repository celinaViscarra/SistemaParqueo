package com.grupo13.parqueo;

public class Parqueo {
    int idParqueo;
    String nomParqueo;
    boolean reservado;

    public Parqueo(int id, String nom, boolean r){
        this.idParqueo=id;
        this.nomParqueo=nom;
        this.reservado=r;
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

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }
}
