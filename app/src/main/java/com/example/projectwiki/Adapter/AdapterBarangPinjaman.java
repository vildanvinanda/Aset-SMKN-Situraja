package com.example.projectwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterBarangPinjaman extends RecyclerView.Adapter<AdapterBarangPinjaman.ViewHolder>{


    LayoutInflater inflater;

    List<ModelDafPin> modelDafPins;
    Context context;


    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterBarangPinjaman(Context context, List<ModelDafPin> modelDafPins) {
        this.context = context;
        this.modelDafPins = modelDafPins;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_dafpin,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        holder.name_suplay.setText(modelDafPins.get(position).getNama_barang());

        String inaktif = modelDafPins.get(position).getStatus().toString();

        if(inaktif.equals("aktif")){
            holder.tgl_tegatwaktu.setText(modelDafPins.get(position).getDue_date());
            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.Hijautua));
            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.Hijaumuda));
        } else if(inaktif.equals("peringatan")) {
            holder.tgl_tegatwaktu.setText(modelDafPins.get(position).getDue_date());
            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.Merahtua));
            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.Merahmuda));
        } else {
            holder.tgl_tegatwaktu.setText(modelDafPins.get(position).getDue_date());
            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.abutua));
            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.abumuda));
        }



    }

    public void clear() {
        int size = modelDafPins.size();
        modelDafPins.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return modelDafPins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageartikel;
        public TextView name_suplay, tgl_tegatwaktu;
        public CardView itemdafpin;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            itemdafpin = (CardView) itemView.findViewById(R.id.itemdafpin);
            imageartikel = (ImageView) itemView.findViewById(R.id.imageartikel);
            name_suplay = (TextView) itemView.findViewById(R.id.name_suplay);
            tgl_tegatwaktu = (TextView) itemView.findViewById(R.id.tgl_tegatwaktu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
