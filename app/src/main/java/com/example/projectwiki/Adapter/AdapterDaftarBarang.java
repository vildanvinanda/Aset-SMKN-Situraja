package com.example.projectwiki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDaftarBarang extends RecyclerView.Adapter<AdapterDaftarBarang.ViewHolder>{

    List<ModelBarang> modelBarangs;
    Context context;

    private AdapterDaftarBarang.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (AdapterDaftarBarang.OnItemClickListener listener){
        mListener = listener;
    }
    public AdapterDaftarBarang(Context context, List<ModelBarang> modelBarangs) {
        this.context = context;
        this.modelBarangs = modelBarangs;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_suplay,parent,false);
        AdapterDaftarBarang.ViewHolder viewHolder = new AdapterDaftarBarang.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        holder.name_suplay.setText(modelBarangs.get(position).getNama_barang());
        holder.total_suplay.setText(modelBarangs.get(position).getJml_barang());
//        holder.btn_pinjam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        Glide.with(holder.imageitem.getContext()).load(modelBarangs.get(position).getImg_barang()).centerCrop().into(holder.imageitem);

    }

    @Override
    public int getItemCount() {
        return modelBarangs.size();
    }
    public void clear() {
        int size = modelBarangs.size();
        modelBarangs.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageitem;
        TextView name_suplay, total_suplay, btn_pinjam;


        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            imageitem = (ImageView) itemView.findViewById(R.id.imageitem);
            name_suplay = (TextView) itemView.findViewById(R.id.name_suplay);
            total_suplay = (TextView) itemView.findViewById(R.id.total_suplay);
            btn_pinjam = (TextView) itemView.findViewById(R.id.btn_pinjam);

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
