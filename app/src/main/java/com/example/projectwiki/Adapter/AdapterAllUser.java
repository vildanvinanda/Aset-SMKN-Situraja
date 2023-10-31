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
import com.example.projectwiki.Model.ModelUser;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterAllUser extends RecyclerView.Adapter<AdapterAllUser.ViewHolder>{

    LayoutInflater inflater;

    ArrayList<ModelUser> modelUsers;
    Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterAllUser(Context context, ArrayList<ModelUser> modelUsers) {
        this.context = context;
        this.modelUsers = modelUsers;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_dashboard,parent,false);
        ViewHolder viewHolder = new AdapterAllUser.ViewHolder(view);
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
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        holder.name_suplay.setText(modelUsers.get(position).getNama());
        holder.tgl_item.setText(modelUsers.get(position).getJk());
        holder.des_suplay.setText(modelUsers.get(position).getAlamat());
        holder.total_suplay.setText(modelUsers.get(position).getKelas());

        Glide.with(holder.imageartikel.getContext()).load(modelUsers.get(position).getImg_user()).centerCrop().into(holder.imageartikel);

    }

    @Override
    public int getItemCount() {
        return modelUsers.size();
    }

    public void clear() {
        int size = modelUsers.size();
        modelUsers.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView itemdashboard;
        public ImageView imageartikel;
        public TextView name_suplay, des_suplay, total_suplay, tgl_item;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            itemdashboard = (CardView) itemView.findViewById(R.id.itemdashboard);
            imageartikel = (ImageView) itemView.findViewById(R.id.imageartikel);
            name_suplay = (TextView) itemView.findViewById(R.id.name_suplay);
            des_suplay = (TextView) itemView.findViewById(R.id.des_suplay);
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
