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

public class PL extends AppCompatActivity {

    SearchView searchView;
    TextView back;
    static ListView listView;
    static ArrayList<String> itemsP;
    static ArrayAdapter<String> adapterP;
    EditText input;
    ImageView enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PL.this, Welcome.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listviewp);
        input = findViewById(R.id.inputP);
        enter = findViewById(R.id.addP);

        itemsP = new ArrayList<>();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PL.this, "Removed: " + itemsP.get(position), Toast.LENGTH_SHORT).show();
                removeItem(position);
                return false;
            }
        });

        adapterP = new ArrayAdapter<>(getApplicationContext(), R.layout.row, itemsP);
        listView.setAdapter(adapterP);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0){
                    Toast.makeText(PL.this, "Enter score before adding!", Toast.LENGTH_SHORT).show();
                }else{
                    addItem(text);
                    input.setText("");
                    Toast.makeText(PL.this, "Added: " + text, Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadContent();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapterP.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterP.getFilter().filter(newText);

                return false;
            }
        });

    }

    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "listp.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            itemsP = new ArrayList<>(Arrays.asList(split));
            adapterP = new ArrayAdapter<>(this, R.layout.row, itemsP);
            listView.setAdapter(adapterP);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "listp.txt"));
            writer.write(itemsP.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public static void removeItem(int remove){
        itemsP.remove(remove);
        listView.setAdapter(adapterP);
    }

    public void addItem(String item){
        itemsP.add(item);
        listView.setAdapter(adapterP);
    }
}