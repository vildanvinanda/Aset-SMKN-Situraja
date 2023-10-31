package com.example.projectwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwiki.FaqModel;
import com.example.projectwiki.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class AdapterFaq extends RecyclerView.Adapter<AdapterFaq.ViewHolder>{

    List<FaqModel> faqModels;
    Context context;

    private AdapterFaq.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (AdapterFaq.OnItemClickListener listener){
        mListener = listener;
    }
    public AdapterFaq(Context context, List<FaqModel> faqModels) {
        this.context = context;
        this.faqModels = faqModels;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_faq,parent,false);
        AdapterFaq.ViewHolder viewHolder = new AdapterFaq.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( @NotNull ViewHolder holder, int position) {
        holder.pertanyaan.setText(faqModels.get(position).getPertanyaan());
        holder.jawaban.setText(faqModels.get(position).getJawaban());

        holder.rowrightfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                boolean button1IsVisible = holder.jawabanbg.getVisibility();
                if(holder.jawabanbg.getVisibility() == View.GONE){
                    holder.jawabanbg.setVisibility(View.VISIBLE);
                    holder.rowrightfaq.animate().rotation(holder.rowrightfaq.getRotation()+90).start();
                }
                else{
                    holder.jawabanbg.setVisibility(View.GONE);
                    holder.rowrightfaq.animate().rotation(holder.rowrightfaq.getRotation()-90).start();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return faqModels.size();
    }

    public void clear() {
        int size = faqModels.size();
        faqModels.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pertanyaan, jawaban;
        ImageView rowrightfaq;
        RelativeLayout jawabanbg;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            pertanyaan = (TextView) itemView.findViewById(R.id.pertanyaan);
            jawaban = (TextView) itemView.findViewById(R.id.jawaban);
            rowrightfaq = (ImageView) itemView.findViewById(R.id.rowrightfaq);
            jawabanbg = (RelativeLayout) itemView.findViewById(R.id.jawabanbg);

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
