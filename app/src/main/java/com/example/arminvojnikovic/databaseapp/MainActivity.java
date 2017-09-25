package com.example.arminvojnikovic.databaseapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editName, editLastName, editGrade, editTextDeleteName;
    Button btnAddStudent,btnViewAll, btn_deleteStudent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editLastName = (EditText)findViewById(R.id.editText2_LastName);
        editGrade = (EditText)findViewById(R.id.editText3_Grade);
        btnAddStudent = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewall);
        btn_deleteStudent = (Button) findViewById(R.id.button_delete);
        editTextDeleteName = (EditText)findViewById(R.id.editText_deleteName);
        addStudentData();
        viewAll();
        deleteStudentData();


    }

    public void deleteStudentData(){
        btn_deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextDeleteName.getText().toString().length() <1){
                    Toast.makeText(MainActivity.this,"Enter player name",Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isDeleted = myDB.deleteData(editTextDeleteName.getText().toString());
                    if (isDeleted == true) {
                        Toast.makeText(MainActivity.this, "Student is Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Student not found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void addStudentData(){
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(editName.getText().toString(),editLastName.getText().toString(),editGrade.getText().toString());
                if(isInserted == true){
                    Toast.makeText(MainActivity.this,"Data is Inserted",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,"Data is NOT Inserted",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if (res.getColumnCount() == 0){
                 showMessage("Error","No data found");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                while (res.moveToNext()){
                    sb.append("Id : "+res.getString(0)+"\n");
                    sb.append("Name : "+res.getString(1)+"\n");
                    sb.append("LastName : "+res.getString(2)+"\n");
                    sb.append("Grade : "+res.getString(3)+"\n");
                }
                showMessage("Data",sb.toString());
            }
        });

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }

}
