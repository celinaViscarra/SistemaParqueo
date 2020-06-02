package com.grupo13.parqueo;

public class Comentario {
    int idComentario;
    String texto;

    public Comentario(int id, String t){
        this.idComentario=id;
        this.texto=t;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
