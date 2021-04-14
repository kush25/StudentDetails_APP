package com.hcl.sqlite_pk_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    TextView fnames,lnames,scores;
    EditText ed_fname,ed_lname,ed_score;
    Button insert_dt,view_dt,delete_dt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating instance of Database class
        mydb = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase dbobj = mydb.getReadableDatabase();

        fnames = findViewById(R.id.fname);
        lnames = findViewById(R.id.lname);
        scores = findViewById(R.id.score);

        ed_fname = findViewById(R.id.edit_fname);
        ed_lname = findViewById(R.id.edit_lname);
        ed_score = findViewById(R.id.edit_score);

        insert_dt = findViewById(R.id.insert_data);
        view_dt = findViewById(R.id.view_data);
        delete_dt = findViewById(R.id.delete_data);

        addData();
        viewData();
        deleteData();



    }


    public void addData(){
        insert_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fnm = ed_fname.getText().toString();
                String lnm = ed_lname.getText().toString();
                String scr = ed_score.getText().toString();


                if(fnm.equals("") || lnm.equals("") || scr.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                }


                else {

                    boolean insertDone = mydb.insertData(fnm, lnm, scr);
                    if (insertDone == true) {
                        Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
    }

    public void viewData(){
        view_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor vwdt = mydb.getData();
                if(vwdt.getCount() == 0){
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

                StringBuffer buffer = new StringBuffer();
                while(vwdt.moveToNext()){
                    buffer.append("Id " + vwdt.getString(0) + "\n");
                    buffer.append("First Name: " + vwdt.getString(1) + "\n");
                    buffer.append("Last Name: " + vwdt.getString(2) + "\n");
                    buffer.append("Score: " + vwdt.getString(3) + "\n\n");

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Student Details");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

    }

    public void deleteData(){
        delete_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input  = new EditText(MainActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setView(input);
                builder.setTitle("Enter Id");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input.setPadding(30,30,30,30);
                        String ids = input.getText().toString();
                        Integer values = mydb.deleteData(ids);

                        if(values>0){
                            Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "No Similar Data to Delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.show();


            }
        });

    }






}