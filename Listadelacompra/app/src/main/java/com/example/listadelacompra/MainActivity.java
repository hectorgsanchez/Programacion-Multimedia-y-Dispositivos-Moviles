package com.example.listadelacompra;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText etName, etQuantity;
    Button btnAdd;
    Spinner spinner;

    ArrayList<Item> itemList = new ArrayList<>();
    ShoppingListAdapter adapter;

    // Opción sencilla: nombres en el spinner
    String[] nombres = {"Manzana", "Banana", "Pan"};

    int[] imageIds = {
            R.drawable.apple,
            R.drawable.banana,
            R.drawable.bread
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.spinnerImages);

        // Spinner sencillo (solo nombres)
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        adapter = new ShoppingListAdapter(this, itemList);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        btnAdd.setOnClickListener(v -> addItem());
    }

    private void addItem() {
        String name = etName.getText().toString();
        String qtyStr = etQuantity.getText().toString();

        if (name.isEmpty() || qtyStr.isEmpty()) return;

        int qty = Integer.parseInt(qtyStr);

        // La imagen corresponde a la posición del spinner
        int image = imageIds[spinner.getSelectedItemPosition()];

        itemList.add(new Item(name, qty, image));
        adapter.notifyDataSetChanged();

        etName.setText("");
        etQuantity.setText("");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Opciones");
        menu.add(0, 1, 0, "Añadir");
        menu.add(0, 2, 0, "Eliminar");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 1: // añadir uno más
                Item it = itemList.get(info.position);
                it.setQuantity(it.getQuantity() + 1);
                adapter.notifyDataSetChanged();
                return true;

            case 2: // eliminar
                itemList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
