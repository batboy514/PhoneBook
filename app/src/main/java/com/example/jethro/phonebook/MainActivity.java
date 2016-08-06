package com.example.jethro.phonebook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    DatabaseReference db;
    FireBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv=(ListView) findViewById(R.id.lv);
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FireBaseHelper(db);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,helper.retrieve());
         lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,helper.retrieve().get(i),Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               displayInputDialog();
            }
        });
    }
        private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        final EditText nameEditTxt=(EditText) d.findViewById(R.id.nameEditText);

        Button saveBtn=(Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=nameEditTxt.getText().toString();


                Spacecraft s=new Spacecraft();
                s.setName(name);



                if(name.length()>0 && name!=null)
                {
                    if(helper.save(s))
                    {
                        nameEditTxt.setText("");

                        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,helper.retrieve());

                        lv.setAdapter(adapter);
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();

                }



            }
        });

        d.show();
    }

}
