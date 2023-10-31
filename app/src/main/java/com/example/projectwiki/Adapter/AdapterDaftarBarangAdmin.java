package com.example.projectwiki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectwiki.Admin.FormUpdateBarang;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDaftarBarangAdmin extends RecyclerView.Adapter<AdapterDaftarBarangAdmin.ViewHolder>{

    List<ModelBarang> modelBarangs;
    Context context;

    public static final String EXTRA_DB4_BPID = "bpid";
    public static final String EXTRA_DB4_NAMEB = "nama_barang";
    public static final String EXTRA_DB4_TGL = "tgl_upload";
    public static final String EXTRA_DB4_IMG = "img_barang";
    public static final String EXTRA_DB4_DES = "deskripsi";
    public static final String EXTRA_DB4_JML = "jml_barang";
    public static final String EXTRA_DB4_TYPE = "type_user";

    private AdapterDaftarBarangAdmin.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (AdapterDaftarBarangAdmin.OnItemClickListener listener){
        mListener = listener;
    }
    public AdapterDaftarBarangAdmin(Context context, List<ModelBarang> modelBarangs) {
        this.context = context;
        this.modelBarangs = modelBarangs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_suplay_admin,parent,false);
        AdapterDaftarBarangAdmin.ViewHolder viewHolder = new AdapterDaftarBarangAdmin.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        holder.name_suplay.setText(modelBarangs.get(position).getNama_barang());
        holder.total_suplay.setText(modelBarangs.get(position).getJml_barang());
//        holder.imageitem.setText(modelBarangs.get(position).getJml_barang());

        final ModelBarang modelBarang = modelBarangs.get(position);

        Glide.with(holder.imageitem.getContext())
                .load(modelBarangs.get(position).getImg_barang())
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(holder.imageitem);

        holder.btn_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormUpdateBarang.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(EXTRA_DB4_NAMEB, modelBarang.getNama_barang());
                intent.putExtra(EXTRA_DB4_JML, modelBarang.getJml_barang());
                intent.putExtra(EXTRA_DB4_DES, modelBarang.getDeskripsi());
                intent.putExtra(EXTRA_DB4_TGL, modelBarang.getTgl_upload());
                intent.putExtra(EXTRA_DB4_IMG, modelBarang.getImg_barang());
                intent.putExtra(EXTRA_DB4_TYPE, "adapterintent");
                intent.putExtra(EXTRA_DB4_BPID, modelBarang.getBpid());
                context.startActivity(intent);

//                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), MainActivity2.class));
            }
        });
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
