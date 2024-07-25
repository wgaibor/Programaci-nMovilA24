package com.example.productolemas.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productolemas.LemasApplication;
import com.example.productolemas.R;
import com.example.productolemas.activity.UpdateProductoActivity;
import com.example.productolemas.entity.ProductoEntity;
import com.example.productolemas.entity.ProductoRequestEntity;
import com.example.productolemas.entity.ResponseEntity;
import com.example.productolemas.interfaces.Ilemas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.LemasHolder> {

    Activity activity;
    List<ProductoEntity> lstProducto;
    Call<ResponseEntity> entityCall;

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
        holder.tarjetaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(activity, UpdateProductoActivity.class);
                intento.putExtra("idProducto", item.getIdProducto());
                intento.putExtra("marca", item.getMarca());
                intento.putExtra("modelo", item.getModelo());
                intento.putExtra("descripcion", item.getDescripcion());
                intento.putExtra("precio", item.getPrecio());
                intento.putExtra("estado", item.getEstado());
                intento.putExtra("imagen", item.getImagen());
                activity.startActivity(intento);
            }
        });
        holder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ilemas peticion = LemasApplication.getmInstance().getRestAdapter().create(Ilemas.class);
                ProductoEntity producto = new ProductoEntity();
                producto.setIdProducto(item.getIdProducto());

                ProductoRequestEntity entidad = new ProductoRequestEntity();
                entidad.setOp(activity.getString(R.string.op_eliminar));
                entidad.setData(producto);

                entityCall = peticion.saveItem(entidad);
                entityCall.enqueue(new Callback<ResponseEntity>() {
                    @Override
                    public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                        if (response.isSuccessful()){
                            ResponseEntity respuesta = response.body();
                            Toast.makeText(activity, respuesta.getMensaje(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
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
        CardView tarjetaItem;
        ImageView icDelete;


        public LemasHolder(@NonNull View itemView) {
            super(itemView);

            marca = itemView.findViewById(R.id.tv_marca);
            modelo = itemView.findViewById(R.id.tv_modelo);
            descripcion = itemView.findViewById(R.id.tv_descripcion);
            precio = itemView.findViewById(R.id.tv_precio);
            estado = itemView.findViewById(R.id.tv_estado);
            tarjetaItem = itemView.findViewById(R.id.cvItemProducto);
            icDelete = itemView.findViewById(R.id.ivEliminarProducto);
        }
    }
}
