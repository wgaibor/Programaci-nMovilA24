package ec.edu.lemas.teclemasvet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.activity.DetailsItemsActivity;
import ec.edu.lemas.teclemasvet.activity.ListPetsActivity;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class ListPetsAdapter extends RecyclerView.Adapter<ListPetsAdapter.ViewHolder> {

    Activity activity;
    List<PetEntity> lstPets;

    private onItemSelectedListener listener;



    public ListPetsAdapter(Activity activity, List<PetEntity> lstPets) {
        this.activity = activity;
        this.lstPets = lstPets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetEntity objMascota = lstPets.get(position);
        holder.nombreMascota.setText(objMascota.getNombre());
        holder.tipoMascota.setText(objMascota.getTipo());
        Glide.with(activity)
                .load(objMascota.getUrl())
                .into(holder.imgMascota);
        holder.tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemSelected(position);
            }
        });
        /*holder.tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiar = new Intent(activity, DetailsItemsActivity.class);
                cambiar.putExtra("imgUrl", objMascota.getUrl());
                cambiar.putExtra("nombreMascota", objMascota.getNombre());
                cambiar.putExtra("tipoMascota", objMascota.getTipo());
                activity.startActivity(cambiar);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return lstPets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMascota;
        TextView nombreMascota;
        TextView tipoMascota;
        CardView tarjeta;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMascota = itemView.findViewById(R.id.img_Mascota);
            nombreMascota = itemView.findViewById(R.id.tv_nombreMascota);
            tipoMascota = itemView.findViewById(R.id.tv_tipoMascota);
            tarjeta = itemView.findViewById(R.id.cv_pets);
        }
    }

    public interface onItemSelectedListener {
        public void onItemSelected(int position);
    }

    public void setListener(onItemSelectedListener listener) {
        this.listener = listener;
    }
}
