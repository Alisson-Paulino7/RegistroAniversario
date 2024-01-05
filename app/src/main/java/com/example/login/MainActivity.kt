package com.example.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifap.persistenciadados.SQLHelper
import br.edu.unifap.persistenciadados.TBL_CADASTRO
import br.edu.unifap.persistenciadados.TBL_CADASTRO_PASS
import br.edu.unifap.persistenciadados.TBL_CADASTRO_USER


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editTextlogin: EditText
    private lateinit var editTextsenha: EditText
    private lateinit var botaologar: Button
    private lateinit var imageView: ImageView


    private val sqlHelper = SQLHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.logo)
        editTextlogin = findViewById(R.id.editTextlogin)
        editTextsenha = findViewById(R.id.editTextsenha)
        botaologar = findViewById(R.id.botao_logar)

        botaologar.setOnClickListener(this)

        val animacao = RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

        animacao.interpolator = LinearInterpolator()
        animacao.repeatCount = Animation.INFINITE
        animacao.duration = 3000

        imageView.startAnimation(animacao)
    }

    override fun onClick(view: View?) {

        val sql = "select * from $TBL_CADASTRO where $TBL_CADASTRO_USER = ? and $TBL_CADASTRO_PASS = ?"

        val bancodados = sqlHelper.readableDatabase

        val cursor = bancodados.rawQuery(sql, arrayOf(editTextlogin.text.toString(), editTextsenha.text.toString()))


        if ((editTextlogin.text.toString() == "")
            or (editTextsenha.text.toString() == "")
        ) {
            Toast.makeText(
                this,
                "Por favor, preencha todos os campos!",
                Toast.LENGTH_LONG
            ).show()

        }
        else if (!cursor.moveToNext()) {
            Toast.makeText(this, "Usuário não localizado!",
                Toast.LENGTH_LONG).show()

            val contentValue = ContentValues().apply {
                put(TBL_CADASTRO_USER, editTextlogin.text.toString())
                put(TBL_CADASTRO_PASS, editTextsenha.text.toString())
            }

            val bancodados = sqlHelper.writableDatabase

            // INSERT INTO usuario (login, password) VALUES (edtLogin, edtPassword)
            val id = bancodados.insert(TBL_CADASTRO, null, contentValue)

            if (id == -1L) {
                Toast.makeText(
                    this, "Falha na inclusão!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this, "Informações salvas com sucesso! \n ID: $id",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        else
         {
            val intent = Intent(this, telainicial::class.java)
            startActivity(intent)

             Toast.makeText(
                 this,
                 "Seja bem vindo!",
                 Toast.LENGTH_LONG
             ).show()

        }
    }
}