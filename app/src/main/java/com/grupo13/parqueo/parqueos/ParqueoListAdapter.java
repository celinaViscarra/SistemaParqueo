package com.grupo13.parqueo.parqueos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.grupo13.parqueo.R;
import com.grupo13.parqueo.modelo.Parqueo;

import org.w3c.dom.Text;

import java.util.List;

public class ParqueoListAdapter extends RecyclerView.Adapter<ParqueoListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo, txtDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
        }
    }

    private List<Parqueo> parqueaderos;

    public ParqueoListAdapter(List<Parqueo> parqueos){
        parqueaderos = parqueos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View parqueoView = inflater.inflate(R.layout.parqueo_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(parqueoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Parqueo selected = parqueaderos.get(position);
        TextView txtTitulo = holder.txtTitulo;
        TextView txtDesc = holder.txtDesc;
        txtTitulo.setText(selected.nombre_parqueo);
        String estado = selected.ocupado == 1?"Ocupado":"Libre";
        txtDesc.setTextColor(selected.ocupado == 1 ? Color.RED: Color.rgb(91,224,61));
        txtDesc.setText("Estado: "+ estado);
    }

    @Override
    public int getItemCount() {
        return parqueaderos.size();
    }

}
