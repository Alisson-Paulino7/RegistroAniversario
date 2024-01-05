package br.edu.unifap.persistenciadados

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context) : SQLiteOpenHelper(
        context, BD_NOME, null, BD_VERSAO) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        sqliteDatabase!!.execSQL(
            "create table $TBL_NIVER ( " +
                    "$TBL_NIVER_ID INTEGER primary key autoincrement, " +
                    "$TBL_NIVER_NOME TEXT not null, " +
                    "$TBL_NIVER_FONE TEXT not null, " +
                    "$TBL_NIVER_DTNASC DATE not null );"
        )
        sqliteDatabase!!.execSQL(
                    "create table $TBL_CADASTRO ( " +
                    "$TBL_CADASTRO_ID INTEGER primary key autoincrement, " +
                    "$TBL_CADASTRO_USER TEXT not null, " +
                    "$TBL_CADASTRO_PASS TEXT not null); "

        )
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {
        TODO("Not yet implemented")
    }
}