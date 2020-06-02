package com.grupo13.parqueo;

import java.sql.Date;

public class Imagen {
    int idImagen;
    Date fechaImagen;

    public Imagen(int id, Date fecha){
        this.idImagen=id;
        this.fechaImagen=fecha;
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
