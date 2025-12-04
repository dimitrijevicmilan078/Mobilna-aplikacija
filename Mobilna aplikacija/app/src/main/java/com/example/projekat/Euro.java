package com.example.projekat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class Euro extends AppCompatActivity{

    SearchView searchView;
    TextView back;
    static ListView listView;
    static ArrayList<String> items;
    static ArrayAdapter<String> adapter;
    EditText input;
    ImageView enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euro);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Euro.this, Welcome.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.searchE);
        listView = findViewById(R.id.listview);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);

         items = new ArrayList<>();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Euro.this, "Removed: " + items.get(position), Toast.LENGTH_SHORT).show();
                removeItem(position);
                return false;
            }
        });

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row, items);
        listView.setAdapter(adapter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0){
                    Toast.makeText(Euro.this, "Enter score before adding!", Toast.LENGTH_SHORT).show();
                }else{
                    addItem(text);
                    input.setText("");
                    Toast.makeText(Euro.this, "Added: " + text, Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadContent();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });

    }


    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "list.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            items = new ArrayList<>(Arrays.asList(split));
            adapter = new ArrayAdapter<>(this, R.layout.row, items);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public static void removeItem(int remove){
        items.remove(remove);
        listView.setAdapter(adapter);
    }

    public void addItem(String item){
        items.add(item);
        listView.setAdapter(adapter);
    }
}