package com.grupo13.parqueo.modulo;

public class Parqueo {
    private int idParqueo;
    private String nomParqueo;
    private boolean reservado;

    public Parqueo(int idParqueo, String nomParqueo, boolean reservado) {
        this.idParqueo = idParqueo;
        this.nomParqueo = nomParqueo;
        this.reservado = reservado;
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
