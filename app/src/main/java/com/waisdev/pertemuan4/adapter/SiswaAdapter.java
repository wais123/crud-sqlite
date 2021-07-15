package com.waisdev.pertemuan4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waisdev.pertemuan4.R;
import com.waisdev.pertemuan4.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.HolderData> {

    private List<Siswa> siswaList = new ArrayList<>();

    public SiswaAdapter(List<Siswa> siswaList) {
        this.siswaList = siswaList;
    }

    @Override
    public SiswaAdapter.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_siswa, parent, false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(SiswaAdapter.HolderData holder, int position) {
        holder.tvNama.setText(siswaList.get(position).getNama());
        holder.tvTempatLahir.setText(siswaList.get(position).getTempat_lahir());
    }

    @Override
    public int getItemCount() {
        return siswaList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvNama, tvTempatLahir;
        public HolderData( View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_resultnama);
            tvTempatLahir = itemView.findViewById(R.id.tv_resultempatlahir);
        }
    }
}
