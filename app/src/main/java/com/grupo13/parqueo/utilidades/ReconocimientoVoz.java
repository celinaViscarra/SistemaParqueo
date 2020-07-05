package com.grupo13.parqueo.utilidades;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.grupo13.parqueo.modelo.Ubicacion;

import java.util.List;

public class ReconocimientoVoz {

    String[] comandos = {"parqueo", "encontrar parqueo", "parqueo mas cercano", "parqueo más cercano", "parking", "find parking"};

    String entradaUsuario;

    public ReconocimientoVoz(String entradaUsuario) {
        this.entradaUsuario = entradaUsuario;
    }

    public boolean esComandoValido(){
        boolean encontrado = false;
        for (String c: comandos){
            if (c.equals(entradaUsuario)) {
                encontrado = true;
                break;
            }
        }
        return  encontrado;
    }

    public Ubicacion getRespuesta(LatLng miUbicacion, List<Ubicacion> parqueos){
        float distanciaMasCercana = Float.MAX_VALUE;
        Ubicacion masCercana = null;

        for (Ubicacion parqueo: parqueos){

            float r1 = (float)Math.pow((parqueo.latitud - miUbicacion.latitude), 2);
            float r2 = (float)Math.pow((parqueo.longitud - miUbicacion.longitude), 2);
            float r3 = r1 + r2;
            float distancia = (float) Math.sqrt(r3);

            if(distancia < distanciaMasCercana){
                distanciaMasCercana = distancia;
                masCercana = parqueo;
            }
        }
        return  masCercana;
    }

    public int getDistanciaMetros(LatLng miUbicacion, Ubicacion parqueo) {
        double earthRadius = 6371; // km

        double lat1 = Math.toRadians(miUbicacion.latitude);
        double lon1 = Math.toRadians(miUbicacion.longitude);
        double lat2 = Math.toRadians(parqueo.latitud);
        double lon2 = Math.toRadians(parqueo.longitud);

        double dlon = lon2-lon1;
        double dlat = lat2-lat1;

        double sinlat = Math.sin(dlat / 2);
        double sinlon = Math.sin(dlon / 2);

        double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
        double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

        double distanceInMeters = earthRadius * c * 1000;

        return (int)distanceInMeters;
    }
}
