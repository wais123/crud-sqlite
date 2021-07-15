package com.waisdev.pertemuan4.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.waisdev.pertemuan4.R;
import com.waisdev.pertemuan4.RecyclerTouchListener;
import com.waisdev.pertemuan4.adapter.SiswaAdapter;
import com.waisdev.pertemuan4.helper.DBHandler;
import com.waisdev.pertemuan4.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class LihatSiswaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SiswaAdapter adapter;
    DBHandler dbHandler;
    TextView tvResultAdapter;
    List<Siswa> siswaList = new ArrayList<>();
    Siswa siswaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_siswa);

        initComponents();
        initRecyclerView();
        cekDataRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.rv_siswa);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dbHandler = new DBHandler(LihatSiswaActivity.this);
        siswaList.addAll(dbHandler.getSemuaSiswa());
        adapter = new SiswaAdapter(siswaList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    private void initComponents(){
        tvResultAdapter = findViewById(R.id.tv_resultAdapter);
    }

    private void cekDataRecyclerView(){
        if (adapter.getItemCount() == 0){
            tvResultAdapter.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            tvResultAdapter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showActionsDialog(final int position){
        CharSequence pilih[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(pilih, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //get dialog
                    showSiswaDialog(siswaList.get(position), position);
                }else {
                    //delete
                    dbHandler.hapusDataSiswa(siswaList.get(position));
                    siswaList.remove(position);
                    adapter.notifyItemChanged(position);
                }
            }
        });
        builder.show();
    }

    private void showSiswaDialog(Siswa siswa, final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.siswa_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(LihatSiswaActivity.this);

        builder.setView(view);

        EditText inputNama = view.findViewById(R.id.et_nama);
        EditText inputTL = view.findViewById(R.id.et_tempatlahir);
        inputNama.setText(siswa.getNama());
        inputTL.setText(siswa.getTempat_lahir());

        builder.setCancelable(false)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputNama.getText().toString())){
                    Toast.makeText(LihatSiswaActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(inputTL.getText().toString())){
                    Toast.makeText(LihatSiswaActivity.this, "Enter Tempat Lahir", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    alertDialog.dismiss();
                }

                siswaModel = dbHandler.getSiswa(siswa.getId());
                siswaModel.setNama(inputNama.getText().toString());
                siswaModel.setTempat_lahir(inputNama.getText().toString());
                dbHandler.updateSiswa(siswaModel);
                siswaList.set(position, siswaModel);
                adapter.notifyItemChanged(position);

            }
        });
    }
}