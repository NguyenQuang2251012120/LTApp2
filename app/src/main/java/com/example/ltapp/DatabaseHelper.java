package com.example.ltapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DuLieuData.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_FAVORITES = "favorite";
    public static final String C_FAVORITE_USER = "user";
    public static final String C_FAVORITE_NAME = "Sport_name";
    public static final String C_FAVORITE_PRICE = "Sport_price";
    public static final String C_FAVORITE_DISTRICT = "Sport_district";

    // Tên bảng và các cột
    public static final String TEN_BANG_USER = "User"; // Ensure correct table name
    public static final String COL_1 = "U_ID";
    public static final String COL_2 = "U_USERNAME";
    public static final String COL_3 = "U_PASSWORD";
    public static final String COL_4 = "U_USERTYPE";
    // Tên bảng và các cột
    public static final String TABLE_BOOKINGS = "bookings";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_USERNAMEORDER = "user";
    public static final String COLUMN_TIME_SLOTS = "time_slots";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_COURT_NUMBER = "court_number";
    public static final String COLUMN_COURT_NAME = "ten_san";


    // Bảng comments
    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_COMMENT_ID = "id";
    public static final String COLUMN_COMMENT_USERNAME = "username";
    public static final String COLUMN_COMMENT_CONTENT = "content";
    public static final String COLUMN_COMMENT_RATING = "rating";
    public static final String COLUMN_COMMENT_COURT_ID = "court_id";

    public static final String TABLE_NAME = "categories";
    public static final String COLUMN_CID = "categories_id";
    public static final String COLUMN_CNAME = "categories_name";

    public static final String TABLE_SPORT = "sport";
    public static final String COL_ID = "S_ID";
    public static final String COL_TYPE = "S_TYPE";
    public static final String COL_NAME = "S_NAME";
    public static final String COL_LOCATION = "S_LOCATION";
    public static final String COL_DISTRICT = "S_DISTRICT";
    public static final String COL_RATING = "S_RATING";
    public static final String COL_PRICE = "S_PRICE";
    public static final String COL_DESCRIPTION = "S_DESCRIPTION";
    public static final String COL_SERVICE = "S_SERVICE";

    // Câu lệnh tạo bảng

    private static final String CREATE_TABLE_FAVORITE = ""
            + "CREATE TABLE " + TABLE_FAVORITES + " ( "
            + C_FAVORITE_USER + " TEXT NOT NULL, "
            + C_FAVORITE_NAME + " TEXT NOT NULL, "
            + C_FAVORITE_PRICE + " TEXT NOT NULL, "
            + C_FAVORITE_DISTRICT + " TEXT NOT NULL );";
    private static final String CREATE_TABLE_CATEGORIES = ""
            + "CREATE TABLE " + TABLE_NAME + " ( "
            + COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CNAME + " TEXT NOT NULL );";

    private static final String CREATE_TABLE_SPORT = ""
            + "CREATE TABLE " + TABLE_SPORT + " ( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TYPE + " TEXT NOT NULL, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_LOCATION + " TEXT NOT NULL, "
            + COL_DISTRICT + " TEXT NOT NULL, "
            + COL_RATING + " TEXT, "
            + COL_PRICE + " TEXT NOT NULL, "
            + COL_DESCRIPTION + " TEXT NOT NULL, "
            + COL_SERVICE + " TEXT NOT NULL );";

    private static final String CREATE_TABLE_USER = ""
            + "CREATE TABLE " + TEN_BANG_USER + " ( "
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_2 + " TEXT NOT NULL, "
            + COL_3 + " TEXT NOT NULL, "
            + COL_4 + " TEXT NOT NULL );";

    // Câu lệnh tạo bảng
    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAMEORDER + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_TIME_SLOTS + " TEXT NOT NULL, " +
                    COLUMN_TOTAL_PRICE + " INTEGER NOT NULL, " +
                    COLUMN_COURT_NUMBER + " INTEGER NOT NULL, " +
                    COLUMN_COURT_NAME + " TEXT NOT NULL);";

    // Câu lệnh tạo bảng comments
    private static final String CREATE_TABLE_COMMENTS =
            "CREATE TABLE " + TABLE_COMMENTS + "(" +
                    COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COMMENT_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_COMMENT_CONTENT + " TEXT NOT NULL, " +
                    COLUMN_COMMENT_RATING + " FLOAT NOT NULL, " +
                    COLUMN_COMMENT_COURT_ID + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_SPORT);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        db.execSQL(CREATE_TABLE_COMMENTS);
        db.execSQL(CREATE_TABLE_USER);
        insertAdminUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }


    private void insertAdminUser(SQLiteDatabase db) {
        String adminUsername = "admin";
        String adminPassword = "admin123";
        String adminUsertype = "admin";

        db.execSQL("INSERT INTO " + TEN_BANG_USER + " (U_USERNAME, U_PASSWORD, U_USERTYPE) VALUES (?, ?, ?)",
                new Object[]{adminUsername, adminPassword, adminUsertype});
    }
}
