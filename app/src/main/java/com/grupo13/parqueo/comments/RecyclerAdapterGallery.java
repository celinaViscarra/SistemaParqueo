package com.grupo13.parqueo.comments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grupo13.parqueo.ControlBD;
import com.grupo13.parqueo.R;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Ubicacion;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterGallery extends RecyclerView.Adapter<RecyclerAdapterGallery.ViewHolderGaleria>{
    private static final String TAG = "GALERIAAA";
    String URL = "https://eisi.fia.ues.edu.sv/eisi13/parqueows/assets/";

    private Context context;
    private Ubicacion ubicacion;
    private List<Imagen> imagenes = new ArrayList<>();

    public RecyclerAdapterGallery(Context context, Ubicacion ubicacion) {
        this.context = context;
        this.ubicacion = ubicacion;

        imagenes = ControlBD.getInstance(context).imagenDao().obtenerImagenesPorUbicacion(ubicacion.id_ubicacion);
    }

    @NonNull
    @Override
    public ViewHolderGaleria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_galeria, parent,
                false);
        ViewHolderGaleria holder = new ViewHolderGaleria(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGaleria holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //Modificar para la obtencion de fotos de perfil.
        Glide.with(context).asBitmap().load(URL + imagenes.get(position).filename).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ViewHolderGaleria extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolderGaleria(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_galeria);
        }
    }

}
