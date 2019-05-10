package com.example.orlineyuk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orlineyuk.R;
import com.example.orlineyuk.model.ModelPesanMakanan;

import java.util.ArrayList;

public class AdapterListPesanan extends RecyclerView.Adapter<AdapterListPesanan.myViewHolder3> {

    Context context;
    ArrayList<ModelPesanMakanan> pesan;

    public AdapterListPesanan(Context c, ArrayList<ModelPesanMakanan> listpesan){
        context = c;
        pesan = listpesan;
    }

    class myViewHolder3 extends RecyclerView.ViewHolder{
        TextView shnama, shjum, shbiaya, shstatus;

        public myViewHolder3(View itemView){
            super(itemView);

            shnama = (TextView) itemView.findViewById(R.id.namapes);
            shjum = (TextView) itemView.findViewById(R.id.jumpes);
            shbiaya = (TextView) itemView.findViewById(R.id.biayastat);
            shstatus = (TextView) itemView.findViewById(R.id.statuspes);
        }
    }

    @NonNull
    @Override
    public myViewHolder3 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myViewHolder3(LayoutInflater.from(context)
                .inflate(R.layout.recycler_list_pesanan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder3 holder, final int i) {
        holder.shbiaya.setText(String.valueOf(pesan.get(i).getHarga()));
        holder.shjum.setText(String.valueOf(pesan.get(i).getJumpesanan()));
        holder.shnama.setText(pesan.get(i).getNama());
        holder.shstatus.setText(pesan.get(i).getStatPesan());
    }

    @Override
    public int getItemCount() {
        return pesan.size();
    }


}
