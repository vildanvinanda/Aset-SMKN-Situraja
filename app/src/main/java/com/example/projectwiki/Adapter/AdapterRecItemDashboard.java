package com.example.projectwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterRecItemDashboard extends RecyclerView.Adapter<AdapterRecItemDashboard.ViewHolder> {

    LayoutInflater inflater;

    List<ModelBarang> modelBarangs;
    Context context;

//    public static final String EXTRA_1NAME = "nama_barang";
//    public static final String EXTRA_1BARANG = "jml_barang";
//    public static final String EXTRA_1DES = "deskripsi";
//    public static final String EXTRA_1TGL = "tgl_upload";
//    public static final String EXTRA_1IMG = "img_barang";

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterRecItemDashboard(Context context, List<ModelBarang> modelBarangs) {
        this.context = context;
        this.modelBarangs = modelBarangs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_dashboard,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.itemcontoh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, HomeActivity.class);
//                intent.putExtra("namahewan",dataModels.get(viewHolder.getAdapterPosition()).getNamahewan());
//                context.startActivity(intent);
//            }
//        });
        return viewHolder;
    }





    @Override
    public void onBindViewHolder( @NotNull ViewHolder holder, int position) {
        final ModelBarang modelBarang = modelBarangs.get(position);

        String cek = modelBarang.getBpid();

        holder.name_suplay.setText(modelBarangs.get(position).getNama_barang());
        holder.tgl_item.setText(modelBarangs.get(position).getTgl_upload());
        holder.des_suplay.setText(modelBarangs.get(position).getDeskripsi());
        holder.total_suplay.setText(modelBarangs.get(position).getJml_barang());
        Glide.with(holder.imageartikel.getContext()).load(modelBarangs.get(position).getImg_barang()).centerCrop().into(holder.imageartikel);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), DetailBarang.class);
//                intent.putExtra(EXTRA_1NAME, modelBarang.getNama_barang());
//                intent.putExtra(EXTRA_1BARANG, modelBarang.getJml_barang());
//                intent.putExtra(EXTRA_1DES, modelBarang.getDeskripsi());
//                intent.putExtra(EXTRA_1TGL, modelBarang.getTgl_upload());
//                intent.putExtra(EXTRA_1IMG, modelBarang.getImg_barang());
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

//        Glide.with(holder.imageartikel.getContext())
//                .load(modelBarangs.get(position).getImg_barang())
//                .centerCrop()
//                .placeholder(R.drawable.gambar)
//                .into(holder.imageartikel);

    }

    public void clear() {
        int size = modelBarangs.size();
        modelBarangs.clear();
        notifyItemRangeRemoved(0, size);
    }
    private final int limit = 3;
    @Override
    public int getItemCount() {
//        int limit = 3;
//        return Math.min(modelBarangs.size(),3);
        return modelBarangs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView itemdashboard;
        public ImageView imageartikel;
        public TextView name_suplay, des_suplay, total_suplay, tgl_item, txt_bpid;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            itemdashboard = (CardView) itemView.findViewById(R.id.itemdashboard);
            imageartikel = (ImageView) itemView.findViewById(R.id.imageartikel);
            name_suplay = (TextView) itemView.findViewById(R.id.name_suplay);
            des_suplay = (TextView) itemView.findViewById(R.id.des_suplay);
            txt_bpid = (TextView) itemView.findViewById(R.id.txt_bpid);
            total_suplay = (TextView) itemView.findViewById(R.id.total_suplay);
            tgl_item = (TextView) itemView.findViewById(R.id.tgl_item);

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
