package com.example.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifap.persistenciadados.SQLHelper
import br.edu.unifap.persistenciadados.TBL_NIVER
import br.edu.unifap.persistenciadados.TBL_NIVER_DTNASC
import br.edu.unifap.persistenciadados.TBL_NIVER_FONE
import br.edu.unifap.persistenciadados.TBL_NIVER_NOME
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class aniver : AppCompatActivity(), View.OnClickListener {
    private lateinit var editTextNome: EditText
    private lateinit var editTextFone: EditText
    private lateinit var enviar: Button
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateButton: Button


    private val sqlHelper = SQLHelper(this)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        initDatePicker();
        dateButton = findViewById(R.id.SelectDate)
        dateButton.text = getTodaysDate()

        editTextNome = findViewById<EditText>(R.id.editTextNome)
        editTextFone = findViewById<EditText>(R.id.editTextFone)
        enviar = findViewById<Button>(R.id.enviar)

        val btnretorno = findViewById<Button>(R.id.retorno)
            btnretorno.setOnClickListener  {
                val intent = Intent(this, telainicial::class.java)
                startActivity(intent)
            }

        enviar.setOnClickListener(this)

    }
    private fun getTodaysDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val formattedDate = makeDateString(day, month + 1, year)
            dateButton.text = formattedDate
        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis();
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day)
        return sdf.format(cal.time)
    }
    fun openDatePicker(view: View) {
        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View?) {

        if (editTextNome.text.toString().equals("")
            or editTextFone.text.toString().equals("")
            or dateButton.text.toString().equals("")
        ) {
            Toast.makeText(
                this,
                "Por favor, preencha todos os campos!",
                Toast.LENGTH_LONG
            ).show()

        } else if (editTextNome.text.toString() != ""
                && editTextFone.text.toString() != ""
                && dateButton.text.toString() != "") {

            val sql = "select * from $TBL_NIVER where $TBL_NIVER_FONE = ?"

            val bd = sqlHelper.readableDatabase

            val cursor = bd.rawQuery(sql, arrayOf(editTextFone.text.toString()))

            if (cursor.moveToNext()) {
                Toast.makeText(
                    this, "Usuário já registrado!",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                val tamanhofone = editTextFone.length()

                if (tamanhofone >= 8 && tamanhofone <= 11) {

                    val contentValue = ContentValues().apply {
                        put(TBL_NIVER_NOME, editTextNome.text.toString())
                        put(TBL_NIVER_FONE, editTextFone.text.toString())
                        put(TBL_NIVER_DTNASC, dateButton.text.toString())
                    }

                    val bancodados = sqlHelper.writableDatabase

                    // INSERT INTO usuario (login, password) VALUES (edtLogin, edtPassword)
                    val id = bancodados.insert(TBL_NIVER, null, contentValue)

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
                } else {
                    Toast.makeText(
                        this, "Telefone deve ser maior que 7 e menor que 12 dígitos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            bd.close()
        }
    }
}


