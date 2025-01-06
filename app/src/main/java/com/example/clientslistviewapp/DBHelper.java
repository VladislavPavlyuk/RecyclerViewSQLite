package com.example.clientslistviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "clients.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CLIENTS = "clients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CLIENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SURNAME + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        onCreate(db);
    }

    public void addClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURNAME, client.getSurname());
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_PHONE, client.getPhone());
        db.insert(TABLE_CLIENTS, null, values);
        db.close();
    }

    public void updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURNAME, client.getSurname());
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_PHONE, client.getPhone());
        db.update(TABLE_CLIENTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(client.getId())});
        db.close();
    }

    public void deleteClient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Client getClient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLIENTS, new String[]{COLUMN_ID, COLUMN_SURNAME, COLUMN_NAME, COLUMN_PHONE},
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Client client = new Client();
            client.setId(cursor.getInt(0));
            client.setSurname(cursor.getString(1));
            client.setName(cursor.getString(2));
            client.setPhone(cursor.getString(3));
            cursor.close();
            return client;
        } else {
            return null;
        }
    }

    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(cursor.getInt(0));
                client.setSurname(cursor.getString(1));
                client.setName(cursor.getString(2));
                client.setPhone(cursor.getString(3));
                clientList.add(client);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clientList;
    }
}