package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifap.persistenciadados.SQLHelper
import br.edu.unifap.persistenciadados.TBL_NIVER
import br.edu.unifap.persistenciadados.TBL_NIVER_DTNASC
import br.edu.unifap.persistenciadados.TBL_NIVER_FONE
import br.edu.unifap.persistenciadados.TBL_NIVER_NOME

class listagem : AppCompatActivity() {

    private lateinit var escolha_mes: Spinner
    private var MesSelecionado: String = ""

    private val sqlHelper = SQLHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        escolha_mes = findViewById(R.id.escolha_mes)

        val meses = arrayOf(
            "Selecione",
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
        )


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        escolha_mes.adapter = adapter


        escolha_mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mesSelecionado = meses[position]

                MesSelecionado = mesSelecionado
                realizarConsulta()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implemente este método se necessário
            }


        }


        val btnretorno = findViewById<Button>(R.id.retorno)
        btnretorno.setOnClickListener {
            val intent = Intent(this, telainicial::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("Range")
    private fun realizarConsulta() {
        if (MesSelecionado == "Selecione") {
            Toast.makeText(
                this,
                "Por favor, escolha um mês válido!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val numerodoMes = obterNumeroMes(MesSelecionado)

            val sql = "SELECT * FROM $TBL_NIVER WHERE substr($TBL_NIVER_DTNASC, 4, 2) LIKE ?"

            val bd = sqlHelper.readableDatabase

            val cursor = bd.rawQuery(sql, arrayOf("%$numerodoMes"))

            val resultadoStringBuilder = StringBuilder()

            if (cursor.moveToNext()) {
                do {
                    val nome = cursor.getString(cursor.getColumnIndex(TBL_NIVER_NOME))
                    val dataNascimento = cursor.getString(cursor.getColumnIndex(TBL_NIVER_DTNASC))
                    val telefone = cursor.getString(cursor.getColumnIndex(TBL_NIVER_FONE))

                    val resultado = "Nome: $nome\nData de Nasc.: $dataNascimento\nTelefone: $telefone\n ---------------------------- \n"
                    resultadoStringBuilder.append(resultado)
                } while (cursor.moveToNext())

                val textViewResultados = findViewById<TextView>(R.id.textViewResultados)
                textViewResultados.text = resultadoStringBuilder.toString()

                var mensagemSemRegistros = findViewById<TextView>(R.id.mensagemSemRegistros)
                mensagemSemRegistros.text = ""
            } else {
                var mensagemSemRegistros = findViewById<TextView>(R.id.mensagemSemRegistros)
                mensagemSemRegistros.text = "Não há registros para o mês de $MesSelecionado!"

                val textViewResultados = findViewById<TextView>(R.id.textViewResultados)
                textViewResultados.text = ""

                // Limpar o StringBuilder
                resultadoStringBuilder.setLength(0)

//                Toast.makeText(
//                    this,
//                    "Mês selecionado: $numerodoMes",
//                    Toast.LENGTH_LONG
//                ).show()
            }

            cursor.close()
            bd.close()
        }
    }
    private fun obterNumeroMes(mes: String): String {
        return when (mes) {
            "Janeiro" -> "01"
            "Fevereiro" -> "02"
            "Março" -> "03"
            "Abril" -> "04"
            "Maio" -> "05"
            "Junho" -> "06"
            "Julho" -> "07"
            "Agosto" -> "08"
            "Setembro" -> "09"
            "Outubro" -> "10"
            "Novembro" -> "11"
            "Dezembro" -> "12"
            else -> ""
        }
    }
}