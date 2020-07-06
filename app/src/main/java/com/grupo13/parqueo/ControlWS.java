package com.grupo13.parqueo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grupo13.parqueo.comments.Comments;
import com.grupo13.parqueo.modelo.Calificacion;
import com.grupo13.parqueo.modelo.Comentario;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Parqueo;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ControlWS {
    public static final String ASSETS_URL = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/assets/";

    public static void traerDatos(Context context, MainActivity main) {
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
        helper.vaciarBD();
        requestQueue.start();
        JsonObjectRequest requestAllData = new JsonObjectRequest
                (Request.Method.GET, url + "alldata", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Ubicacion>>() {}.getType();
                            //Primero van las ubicaciones
                            List<Ubicacion> ubicaciones = gson.fromJson(response.getJSONArray("ubicacion").toString(), listType);
                            helper.ubicacionDao().insertarUbicaciones(ubicaciones);
                            main.cargarUbicaciones();
                            //Despues van los parqueos
                            listType = new TypeToken<List<Parqueo>>() {}.getType();
                            List<Parqueo> parqueos = gson.fromJson(response.getJSONArray("parqueo").toString(), listType);
                            helper.parqueoDao().insertarParqueos(parqueos);

                            listType = new TypeToken<List<Usuario>>() {}.getType();
                            List<Usuario> users = gson.fromJson(response.getJSONArray("usuario").toString(), listType);
                            helper.usuarioDao().insertarUsuarios(users);

                            listType = new TypeToken<List<Comentario>>() {}.getType();
                            List<Comentario> comentarios = gson.fromJson(response.getJSONArray("comentario").toString(), listType);
                            helper.comentarioDao().insertarComentarios(comentarios);

                            listType = new TypeToken<List<Calificacion>>() {}.getType();
                            List<Calificacion> calificaciones = gson.fromJson(response.getJSONArray("calificacion").toString(), listType);
                            helper.calificacionDao().insertarCalificaciones(calificaciones);

                            JSONArray arrayImagenes = response.getJSONArray("imagen");
                            List<Imagen> imagenes = new ArrayList<>();
                            for(int i=0; i<arrayImagenes.length();i++){
                                JSONObject obj = arrayImagenes.getJSONObject(i);
                                Imagen pivote = new Imagen();
                                pivote.id_imagen = Integer.parseInt(obj.getString("id_imagen"));
                                pivote.id_ubicacion = Integer.parseInt(obj.getString("id_ubicacion"));
                                pivote.es_galeria = Integer.parseInt(obj.getString("es_galeria"));
                                pivote.fecha_imagen = Calendar.getInstance();
                                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                                pivote.fecha_imagen.setTime(dateParser.parse(obj.getString("fecha_imagen")));
                                pivote.filename = obj.getString("filename");
                                imagenes.add(pivote);
                            }
                            helper.imagenDao().insertarImagenes(imagenes);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        requestQueue.add(requestAllData);

    }

    public static void subirFoto(Context context, String foto, String ubicacion) {
        String url = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/index.php/api/imagenupload";

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String respuesta = "";
        StringRequest requestFoto = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("POTOGRAFIA-EXITO", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("POTOGRAFIA-FALLO", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_ubicacion", ubicacion);
                params.put("extension", "jpg");
                params.put("base64", foto);
                return params;
            }
        };
        requestQueue.add(requestFoto);

    }

    public static void registrarUsuario(Context context, GoogleSignInAccount account) {
        String url = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/index.php/api/usuario";

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String respuesta = "";
        StringRequest registrar = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REGISTRO", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("REGISTRO", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", account.getEmail());
                params.put("contrasena", "asdklasdkaslkdasdasda");
                params.put("nombre_usuario", account.getDisplayName());
                return params;
            }
        };
        requestQueue.add(registrar);

    }

    public static void onErrorResponse(VolleyError error, Context context) {
        NetworkResponse response = error.networkResponse;
        if (response != null && response.statusCode == 404) {
            //Con esto detectamos errores 404 que vengan del WS
            //Toast.makeText(context, "404",Toast.LENGTH_SHORT).show();
        }
    }

    public static void subirComentario(Context context, String ubicacion, String usuario, String texto, Comments comments) {
        String url = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/index.php/api/comentario";
        ControlBD helper = ControlBD.getInstance(context);
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String respuesta = "";
        StringRequest comentario = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("COMENTARIO ENVIADO", response);
                        try {
                            JSONObject resultado = new JSONObject(response);
                            if(resultado.getString("resultado").equals("1")){
                                Comentario nuevo = new Comentario();
                                nuevo.id_comentario = Integer.parseInt(resultado.getString("id_comentario"));
                                nuevo.id_ubicacion = Integer.parseInt(ubicacion);
                                nuevo.texto = texto;
                                nuevo.usuario = usuario;
                                helper.comentarioDao().insertarComentario(nuevo);
                                Toast.makeText(context, "El mensaje se envio con exito.", Toast.LENGTH_LONG).show();
                                comments.initData();
                            }else{
                                Toast.makeText(context, "Error al mandar el mensaje.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("FALLO CONEXION", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", usuario);
                params.put("id_ubicacion", String.valueOf(ubicacion));
                params.put("texto", texto);
                return params;
            }
        };
        requestQueue.add(comentario);
    }
}
