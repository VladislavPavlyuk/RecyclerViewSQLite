package com.example.clientslistviewapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ClientActivity extends AppCompatActivity {
    private EditText etSurname, etName, etPhone;
    private DBHelper dbHelper;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        etSurname = findViewById(R.id.etSurname);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        dbHelper = new DBHelper(this);

        if (getIntent().hasExtra("client")) {
            client = (Client) getIntent().getSerializableExtra("client");
            etSurname.setText(client.getSurname());
            etName.setText(client.getName());
            etPhone.setText(client.getPhone());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveClient();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveClient() {
        String surname = etSurname.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (client == null) {
            client = new Client();
        }
        client.setSurname(surname);
        client.setName(name);
        client.setPhone(phone);

        if (client.getId() == null) {
            dbHelper.addClient(client);
        } else {
            dbHelper.updateClient(client);
        }

        finish();
    }
}
