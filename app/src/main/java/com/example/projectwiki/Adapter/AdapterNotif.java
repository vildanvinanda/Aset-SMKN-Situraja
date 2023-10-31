package com.example.projectwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.Model.ModelNotif;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class AdapterNotif extends RecyclerView.Adapter<AdapterNotif.ViewHolder> {

    List<ModelNotif> modelNotifs;

    Context context;


    private AdapterNotif.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (AdapterNotif.OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterNotif(Context context, List<ModelNotif> modelNotifs) {
        this.context = context;
        this.modelNotifs = modelNotifs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notif,parent,false);
        AdapterNotif.ViewHolder viewHolder = new AdapterNotif.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        holder.txtjudulnotif.setText(modelNotifs.get(position).getJudul());
        holder.txttglnotif.setText(modelNotifs.get(position).getTgl_kirim());
        holder.isinotif.setText(modelNotifs.get(position).getKeterangan());

    }

    @Override
    public int getItemCount() {
        return modelNotifs.size();
    }
    public void clear() {
        int size = modelNotifs.size();
        modelNotifs.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtjudulnotif,txttglnotif,isinotif;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            txtjudulnotif = (TextView) itemView.findViewById(R.id.txtjudulnotif);
            txttglnotif = (TextView) itemView.findViewById(R.id.txttglnotif);
            isinotif = (TextView) itemView.findViewById(R.id.isinotif);
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
