package com.example.projectwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectwiki.Model.ModelPengajuan;
import com.example.projectwiki.Model.ModelPengembalian;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPengembalianBarang extends RecyclerView.Adapter<AdapterPengembalianBarang.ViewHolder>{

    List<ModelPengembalian> modelPengembalians;
    Context context;


    private AdapterPengembalianBarang.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (AdapterPengembalianBarang.OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterPengembalianBarang(Context context, List<ModelPengembalian> modelPengembalians) {
        this.context = context;
        this.modelPengembalians = modelPengembalians;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_dafpengembalian,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_suplay.setText(modelPengembalians.get(position).getNama_barang());
        holder.tgl_tegatwaktu.setText(modelPengembalians.get(position).getTgl_pinjam());
        holder.txt_status.setText(modelPengembalians.get(position).getStatus());

        String inaktif = holder.txt_status.getText().toString();
//        String instatuspengajuan = modelPengajuans.get(position).getStatus_pengajuan().toString();
//        String cek = modelPengembalians.get(position).getStatus_pengajuan();

        if(inaktif.equals("Menunggu")) {
            holder.indicator.setBackground(context.getResources().getDrawable(R.drawable.ic_menunggu));
//            holder.bgcard2.setBackground(context.getResources().getDrawable(R.color.KuningMuda2));
        } else {
            holder.indicator.setBackground(context.getResources().getDrawable(R.drawable.ic_diterima));
//            holder.bgcard2.setBackground(context.getResources().getDrawable(R.color.Hijaumuda2));
        }

        Glide.with(holder.imageartikel.getContext()).load(modelPengembalians.get(position).getImg_pengembalian()).centerCrop().into(holder.imageartikel);
//        if(inaktif.equals("aktif")){
//            holder.tgl_tegatwaktu.setText(modelPengajuans.get(position).getDue_date());
//            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.Hijautua));
//            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.Hijaumuda));
//        } else if(inaktif.equals("peringatan")) {
//            holder.tgl_tegatwaktu.setText(modelPengajuans.get(position).getDue_date());
//            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.Merahtua));
//            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.Merahmuda));
//        } else {
//            holder.tgl_tegatwaktu.setText(modelPengajuans.get(position).getDue_date());
//            holder.tgl_tegatwaktu.setTextColor(context.getResources().getColor(R.color.abutua));
//            holder.tgl_tegatwaktu.setBackground(context.getResources().getDrawable(R.color.abumuda));
//        }
    }

    public void clear() {
        int size = modelPengembalians.size();
        modelPengembalians.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return modelPengembalians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageartikel,indicator;
        public TextView name_suplay, tgl_tegatwaktu,txt_status;
        public CardView itemdafpin;
        public RelativeLayout bgcard2;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            itemdafpin = (CardView) itemView.findViewById(R.id.itemdafpin);
            imageartikel = (ImageView) itemView.findViewById(R.id.imageartikel);
            indicator = (ImageView) itemView.findViewById(R.id.indicator);
            name_suplay = (TextView) itemView.findViewById(R.id.name_suplay);
            tgl_tegatwaktu = (TextView) itemView.findViewById(R.id.tgl_tegatwaktu);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            bgcard2 = (RelativeLayout) itemView.findViewById(R.id.bgcard2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
