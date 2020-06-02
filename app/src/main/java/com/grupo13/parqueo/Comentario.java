package com.grupo13.parqueo;

public class Comentario {
    private int idComentario;
    private String texto;

    public Comentario(int idComentario, String texto) {
        this.idComentario = idComentario;
        this.texto = texto;
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
