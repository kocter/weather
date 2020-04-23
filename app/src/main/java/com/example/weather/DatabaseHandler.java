package com.example.weather;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

    public class DatabaseHandler extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "userstore.db"; // название бд
        private static final int SCHEMA = 1; // версия базы данных
        static final String TABLE_NAME = "users"; // название таблицы в бд
        // названия столбцов

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CITY = "CITY";
        public static final String COLUMN_TIME = "TIME";
        public static final String COLUMN_TEMPERATURE = "TEMPERATURE";
        public static final String COLUMN_WIND = "WIND";
        public static final String COLUMN_WINDDIRECTION = "WINDDIRECTION";
        public static final String COLUMN_WEATHER = "WEATHER";
        public static final String COLUMN_PRESSURE = "PRESSURE";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, SCHEMA);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE users (" + COLUMN_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY
                    + " TEXT, "  + COLUMN_TIME + " TEXT, " + COLUMN_TEMPERATURE + " TEXT, " + COLUMN_WIND + " TEXT, " + COLUMN_WINDDIRECTION + " TEXT, " + COLUMN_WEATHER + " TEXT, " + COLUMN_PRESSURE + " TEXT);");


        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
    }








