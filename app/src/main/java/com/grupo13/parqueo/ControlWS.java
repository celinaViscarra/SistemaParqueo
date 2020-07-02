package com.grupo13.parqueo;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupo13.parqueo.modelo.Calificacion;
import com.grupo13.parqueo.modelo.Comentario;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Parqueo;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.modelo.Usuario;

import java.lang.reflect.Type;
import java.util.List;

public class ControlWS {
    public static void traerDatos(Context context){
        String url = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/index.php/api/";
        ControlBD helper = ControlBD.getInstance(context);
        RequestQueue requestQueue;

        //Instanciar el cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        //COnfigurar la red para que use HttpURLConnection como el cliente HTTP
        Network network = new BasicNetwork(new HurlStack());
        //Instanciar RequestQueue con el cache y la red
        requestQueue = new RequestQueue(cache, network);
        //Iniciar la cola
        requestQueue.start();
        helper.vaciarBD();
        //TODO: Agregarle una forma de revisar si tiene internet.
        //Si, se pudo hacer mas corto este proceso pero no tenia ganas :v
        //Cargamos la lista de ubicaciones
        StringRequest requestUbicaciones = new StringRequest(
                Request.Method.GET,
                url+"ubicacion",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Ubicacion>>(){}.getType();
                    List<Ubicacion> ubicaciones = gson.fromJson(response, listType);
                    helper.ubicacionDao().insertarUbicaciones(ubicaciones);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestUbicaciones);
        StringRequest requestParqueos = new StringRequest(
                Request.Method.GET,
                url+"parqueo",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Parqueo>>(){}.getType();
                    List<Parqueo> parqueos = gson.fromJson(response, listType);
                    helper.parqueoDao().insertarParqueos(parqueos);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestParqueos);
        StringRequest requestUsuarios = new StringRequest(
                Request.Method.GET,
                url+"usuario",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Usuario>>(){}.getType();
                    List<Usuario> usuarios = gson.fromJson(response, listType);
                    helper.usuarioDao().insertarUsuarios(usuarios);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestUsuarios);
        StringRequest requestComentarios = new StringRequest(
                Request.Method.GET,
                url+"comentario",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Comentario>>(){}.getType();
                    List<Comentario> comentarios = gson.fromJson(response, listType);
                    helper.comentarioDao().insertarComentarios(comentarios);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestComentarios);

        StringRequest requestCalificaciones = new StringRequest(
                Request.Method.GET,
                url+"calificacion",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Calificacion>>(){}.getType();
                    List<Calificacion> calificaciones = gson.fromJson(response, listType);
                    helper.calificacionDao().insertarCalificaciones(calificaciones);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestCalificaciones);

        StringRequest requestImagenes = new StringRequest(
                Request.Method.GET,
                url+"imagen",
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Imagen>>(){}.getType();
                    List<Imagen> imagenes = gson.fromJson(response, listType);
                    helper.imagenDao().insertarImagenes(imagenes);
                },
                error -> onErrorResponse(error,context)
        );
        requestQueue.add(requestComentarios);

    }

    public static void onErrorResponse(VolleyError error,Context context){
        NetworkResponse response = error.networkResponse;
        if(response != null && response.statusCode == 404){
            //Con esto detectamos errores 404 que vengan del WS
            //Toast.makeText(context, "404",Toast.LENGTH_SHORT).show();
        }
    }
}
