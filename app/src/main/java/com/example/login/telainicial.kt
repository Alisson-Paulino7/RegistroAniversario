package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class telainicial : AppCompatActivity(), View.OnClickListener {

    private lateinit var botao_filtro: Button
    private lateinit var botao_novo: Button
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telainicial)

        botao_novo = findViewById<Button>(R.id.novo)
        botao_filtro = findViewById(R.id.filtro)


        val btnSair = findViewById<Button>(R.id.sair)
        btnSair.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        botao_novo.setOnClickListener {
            val intent = Intent(this, aniver::class.java)
            startActivity(intent)
        }

        botao_filtro.setOnClickListener {
            val intent = Intent(this, listagem::class.java)
            startActivity(intent)
        }



    }

    override fun onClick(view: View?) {




    }
}