package ec.edu.lemas.teclemasvet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class ListPetsAdapter extends RecyclerView.Adapter<ListPetsAdapter.ViewHolder> {

    Activity activity;
    List<PetEntity> lstPets;

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
    }

    @Override
    public int getItemCount() {
        return lstPets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMascota;
        TextView nombreMascota;
        TextView tipoMascota;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMascota = itemView.findViewById(R.id.img_Mascota);
            nombreMascota = itemView.findViewById(R.id.tv_nombreMascota);
            tipoMascota = itemView.findViewById(R.id.tv_tipoMascota);
        }
    }
}
