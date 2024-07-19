package com.example.productolemas.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productolemas.R;
import com.example.productolemas.entity.ProductoEntity;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.LemasHolder> {

    Activity activity;
    List<ProductoEntity> lstProducto;

    public ProductoAdapter(Activity activity, List<ProductoEntity> lstProducto) {
        this.activity = activity;
        this.lstProducto = lstProducto;
    }

    @NonNull
    @Override
    public LemasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_layout, parent, false);
        return new LemasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LemasHolder holder, int position) {
        ProductoEntity item = lstProducto.get(position);
        holder.marca.setText(item.getMarca());
        holder.modelo.setText(item.getModelo());
        holder.descripcion.setText(item.getDescripcion());
        holder.precio.setText(item.getPrecio());
        holder.estado.setText(item.getEstado());
    }

    @Override
    public int getItemCount() {
        return lstProducto.size();
    }

    class LemasHolder extends RecyclerView.ViewHolder {
        TextView marca;
        TextView modelo;
        TextView descripcion;
        TextView precio;
        TextView estado;


        public LemasHolder(@NonNull View itemView) {
            super(itemView);

            marca = itemView.findViewById(R.id.tv_marca);
            modelo = itemView.findViewById(R.id.tv_modelo);
            descripcion = itemView.findViewById(R.id.tv_descripcion);
            precio = itemView.findViewById(R.id.tv_precio);
            estado = itemView.findViewById(R.id.tv_estado);
        }
    }
}
