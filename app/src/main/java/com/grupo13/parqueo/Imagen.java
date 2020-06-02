package com.grupo13.parqueo;

import java.sql.Date;

public class Imagen {
    private int idImagen;
    private Date fechaImagen;

    public Imagen(int idImagen, Date fechaImagen) {
        this.idImagen = idImagen;
        this.fechaImagen = fechaImagen;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public Date getFechaImagen() {
        return fechaImagen;
    }

    public void setFechaImagen(Date fechaImagen) {
        this.fechaImagen = fechaImagen;
    }
}
