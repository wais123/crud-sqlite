package com.waisdev.pertemuan4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.waisdev.pertemuan4.activity.LihatSiswaActivity;
import com.waisdev.pertemuan4.activity.TambahSiswaActivity;

public class MainActivity extends AppCompatActivity {

    Button btnTambah, btnLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTambah = findViewById(R.id.btn_tambah);
        btnLihat = findViewById(R.id.btn_lihat);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahSiswaActivity.class);
                startActivity(intent);
            }
        });

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LihatSiswaActivity.class);
                startActivity(intent);
            }
        });
    }
}