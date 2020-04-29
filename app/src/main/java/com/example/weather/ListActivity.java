package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    ListView userList;
    TextView header;
    DatabaseHandler databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    SimpleCursorAdapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        header = findViewById(R.id.header);
        userList = findViewById(R.id.list);

        databaseHelper = new DatabaseHandler(getApplicationContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from "+ DatabaseHandler.TABLE_NAME, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHandler.COLUMN_CITY,DatabaseHandler.COLUMN_TIME, DatabaseHandler.COLUMN_TEMPERATURE,DatabaseHandler.COLUMN_WIND,DatabaseHandler.COLUMN_WINDDIRECTION,DatabaseHandler.COLUMN_WEATHER,DatabaseHandler.COLUMN_PRESSURE};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.four_list,
                userCursor, headers, new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6, R.id.text7}, 0);
        header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        userList.setAdapter(userAdapter);



    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }


    public void onBackPressed() {

        // закрываем подключение
        db.close();
        this.finish();
    }


}
