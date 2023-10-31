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
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.Model.ModelUser;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDaftarUser extends RecyclerView.Adapter<AdapterDaftarUser.ViewHolder>{

    List<ModelUser> modelUsers;
    Context context;


    private AdapterDaftarUser.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (AdapterDaftarUser.OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterDaftarUser(Context context, List<ModelUser> modelUsers) {
        this.context = context;
        this.modelUsers = modelUsers;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_listuser,parent,false);
        AdapterDaftarUser.ViewHolder viewHolder = new AdapterDaftarUser.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        holder.addnama.setText(modelUsers.get(position).getNama());
        holder.addkls.setText(modelUsers.get(position).getKelas());
        holder.addnis.setText(modelUsers.get(position).getNis());


        Glide.with(holder.img_user.getContext())
                .load(modelUsers.get(position).getImg_user())
                .centerCrop()
                .into(holder.img_user);




    }

    public void clear() {
        int size = modelUsers.size();
        modelUsers.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return modelUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_user;
        public TextView addnama, addnis, addkls;
        public CardView itemlistuser;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            itemlistuser = (CardView) itemView.findViewById(R.id.itemlistuser);
            img_user = (ImageView) itemView.findViewById(R.id.img_user);
            addnama = (TextView) itemView.findViewById(R.id.addnama);
            addnis = (TextView) itemView.findViewById(R.id.addnis);
            addkls = (TextView) itemView.findViewById(R.id.addkls);

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
