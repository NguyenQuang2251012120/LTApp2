package com.example.ltapp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDatabase {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;

    public MyDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();  // Close the database connection
        }
    }


    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_2, user.getU_USERNAME());
        values.put(DatabaseHelper.COL_3, user.getU_PASSWORD());
        values.put(DatabaseHelper.COL_4, user.getU_USERTYPE());
        database.update(DatabaseHelper.TEN_BANG_USER, values, DatabaseHelper.COL_1 + " = ?", new String[]{String.valueOf(user.getU_ID())});
    }

    public boolean insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, user.getU_USERNAME());
        contentValues.put(DatabaseHelper.COL_3, user.getU_PASSWORD());
        contentValues.put(DatabaseHelper.COL_4, user.getU_USERTYPE());

        long result = database.insert(DatabaseHelper.TEN_BANG_USER, null, contentValues); // Ensure correct table name

        return result != -1;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TEN_BANG_USER, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_1));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_3));
                String usertype = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4));
                userList.add(new User(id, username, password, usertype));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public void deleteUserN(User user) {
        database.delete(DatabaseHelper.TEN_BANG_USER, DatabaseHelper.COL_1 + " = ?", new String[]{String.valueOf(user.getU_ID())});
    }

    public void deleteUser(User user) {
        database.delete(DatabaseHelper.TEN_BANG_USER, "U_USERNAME = ?", new String[]{user.getU_USERNAME()});
    }

    public boolean checkUserExists(User user) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TEN_BANG_USER + " WHERE " + DatabaseHelper.COL_2 + "= ?", new String[]{user.getU_USERNAME()});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUsernameExistsExcludingCurrentUser(String username) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TEN_BANG_USER + " WHERE " + DatabaseHelper.COL_2 + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    @SuppressLint("Range")
    public String[] checkLogin(User user) {
        String[] columns = {DatabaseHelper.COL_2, DatabaseHelper.COL_4};
        String selection = "U_USERNAME = ? AND U_PASSWORD = ?";
        String[] selectionArgs = {user.getU_USERNAME(), user.getU_PASSWORD()};

        Cursor cursor = database.query(DatabaseHelper.TEN_BANG_USER, columns, selection, selectionArgs, null, null, null); // Ensure correct table name
        String[] result = null;

        if (cursor != null && cursor.moveToFirst()) {
            result = new String[2];
            result[0] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2));
            result[1] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4));
        }
        cursor.close();
        return result;
    }

    public void addCategory(category category) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CNAME, category.getName());
        database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }


    public List<category> getAllCategories() {
        List<category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CNAME));
                categoryList.add(new category(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return categoryList;
    }


    public void updateCategory(int id, category category) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CNAME, category.getName());
        database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_CID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteCategory(int id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_CID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean categoryExists(category Category) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_CNAME + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{Category.getName()});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public void addSport(Sport sport) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TYPE, sport.getS_TYPE());
        values.put(DatabaseHelper.COL_NAME, sport.getS_NAME());
        values.put(DatabaseHelper.COL_LOCATION, sport.getS_LOCATION());
        values.put(DatabaseHelper.COL_DISTRICT, sport.getS_DISTRICT());
        values.put(DatabaseHelper.COL_PRICE, sport.getS_PRICE());
        values.put(DatabaseHelper.COL_RATING, 0.0f);
        values.put(DatabaseHelper.COL_DESCRIPTION, sport.getS_DESCRIPTION());
        values.put(DatabaseHelper.COL_SERVICE, sport.getS_SERVICE());
        long id = database.insert(DatabaseHelper.TABLE_SPORT, null, values);
    }

    public List<Sport> getAllSports() {
        List<Sport> sportList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SPORT;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION));
                String district = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DISTRICT));
                String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PRICE));
                String rating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RATING));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DESCRIPTION));
                String service = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SERVICE));
                sportList.add(new Sport(id, type, name, location, district, rating, price, description, service));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return sportList;
    }

    public void updateSport(Sport sport) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TYPE, sport.getS_TYPE());
        values.put(DatabaseHelper.COL_NAME, sport.getS_NAME());
        values.put(DatabaseHelper.COL_LOCATION, sport.getS_LOCATION());
        values.put(DatabaseHelper.COL_DISTRICT, sport.getS_DISTRICT());
        values.put(DatabaseHelper.COL_PRICE, sport.getS_PRICE());
        values.put(DatabaseHelper.COL_DESCRIPTION, sport.getS_DESCRIPTION());
        values.put(DatabaseHelper.COL_SERVICE, sport.getS_SERVICE());
        database.update(DatabaseHelper.TABLE_SPORT, values, DatabaseHelper.COL_ID + " = ?", new String[]{sport.getS_ID()});
    }

    public void deleteSport(String id) {
        database.delete(DatabaseHelper.TABLE_SPORT, DatabaseHelper.COL_ID + " = ?", new String[]{id});
    }

    public List<Sport> getSportsByCategory(String categoryName) {
        List<Sport> sportList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SPORT + " WHERE " + DatabaseHelper.COL_TYPE + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{categoryName});

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION));
                String district = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DISTRICT));
                String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PRICE));
                String rating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RATING));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DESCRIPTION));
                String service = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SERVICE));
                sportList.add(new Sport(id, type, name, location, district, rating, price, description, service));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return sportList;
    }

    // Phương thức thêm booking mới
    public long addBooking(booking booking) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_DATE, booking.getStringdate());
        values.put(DatabaseHelper.COLUMN_USERNAMEORDER, booking.getUsernameorder());
        values.put(DatabaseHelper.COLUMN_TIME_SLOTS, booking.getTime_slots());
        values.put(DatabaseHelper.COLUMN_TOTAL_PRICE, booking.getTotal_price());
        values.put(DatabaseHelper.COLUMN_COURT_NUMBER, booking.getCourt_number());
        values.put(DatabaseHelper.COLUMN_COURT_NAME, booking.getCourt_name());

        long id = database.insert(DatabaseHelper.TABLE_BOOKINGS, null, values);
        return id;
    }

    // Phương thức thêm comment mới
    public long addComment(Comment comment) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_COMMENT_USERNAME, comment.getUsername());
        values.put(DatabaseHelper.COLUMN_COMMENT_CONTENT, comment.getContent());
        values.put(DatabaseHelper.COLUMN_COMMENT_RATING, comment.getRating());
        values.put(DatabaseHelper.COLUMN_COMMENT_COURT_ID, comment.getCourt_id());

        long id = database.insert(DatabaseHelper.TABLE_COMMENTS, null, values);
        return id;
    }


    // Phương thức lấy danh sách comments theo court_id
    public List<Comment> getCommentsByCourtName(String courtName) {
        List<Comment> commentList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_COMMENTS +
                " WHERE " + DatabaseHelper.COLUMN_COMMENT_COURT_ID + " = ?" + // Filter by court name (court_id)
                " ORDER BY " + DatabaseHelper.COLUMN_COMMENT_ID + " DESC";

        Cursor cursor = database.rawQuery(selectQuery, new String[]{courtName});

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_ID));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_USERNAME));
                String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_CONTENT));
                float rating = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_RATING));
                String courtId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_COURT_ID));

                // Create a Comment object and add it to the list
                Comment comment = new Comment(id, username, content, rating, courtId);
                commentList.add(comment);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return commentList;
    }

    public void updateAllSportsRatings() {
        // Retrieve all unique court IDs from the comments table
        String selectCourtIdsQuery = "SELECT DISTINCT " + DatabaseHelper.COLUMN_COMMENT_COURT_ID + " FROM " + DatabaseHelper.TABLE_COMMENTS;
        Cursor courtIdCursor = database.rawQuery(selectCourtIdsQuery, null);

        if (courtIdCursor.moveToFirst()) {
            do {
                String courtId = courtIdCursor.getString(courtIdCursor.getColumnIndex(DatabaseHelper.COLUMN_COMMENT_COURT_ID));
                // Update the sport rating for each court ID
                updateSportRating(courtId);
            } while (courtIdCursor.moveToNext());
        }
        courtIdCursor.close();
    }

    private void updateSportRating(String courtId) {
        // Calculate the average rating from the comments table for the given court_id
        String selectQuery = "SELECT AVG(" + DatabaseHelper.COLUMN_COMMENT_RATING + ") as avgRating " +
                "FROM " + DatabaseHelper.TABLE_COMMENTS + " " +
                "WHERE " + DatabaseHelper.COLUMN_COMMENT_COURT_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{courtId});

        if (cursor.moveToFirst()) {
            float avgRating = cursor.getFloat(cursor.getColumnIndex("avgRating"));
            // Update the sport's rating in the sport table
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_RATING, avgRating);
            database.update(DatabaseHelper.TABLE_SPORT, values, DatabaseHelper.COL_NAME + " = ?", new String[]{courtId});
        }
        cursor.close();
    }

    // Method to add a sport to favorites for the logged-in user
    public void addSportToFavorites(favorite favorite) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.C_FAVORITE_USER, favorite.getUser());
        values.put(DatabaseHelper.C_FAVORITE_NAME, favorite.getSport_name());
        values.put(DatabaseHelper.C_FAVORITE_PRICE, favorite.getSport_price());
        values.put(DatabaseHelper.C_FAVORITE_DISTRICT, favorite.getSport_district());

        // Insert the favorite sport into the database
        database.insert(DatabaseHelper.TABLE_FAVORITES, null, values);
    }

    // Method to remove a sport from favorites for the logged-in user
    public void removeSportFromFavorites(favorite favorite) {
        // Correct the WHERE clause with placeholders
        String whereClause = DatabaseHelper.C_FAVORITE_USER + " = ? AND " + DatabaseHelper.C_FAVORITE_NAME + " = ?";
        String[] whereArgs = new String[]{favorite.getUser(), favorite.getSport_name()};

        // Delete the favorite sport from the database
        database.delete(DatabaseHelper.TABLE_FAVORITES, whereClause, whereArgs);
    }


    // Method to check if a sport is already a favorite
    public boolean isSportFavorite(favorite favorite) {
        // Correct the WHERE clause with placeholders
        String selection = DatabaseHelper.C_FAVORITE_USER + " = ? AND " + DatabaseHelper.C_FAVORITE_NAME + " = ?";
        String[] selectionArgs = new String[]{favorite.getUser(), favorite.getSport_name()};

        // Query to check if the sport is already in the user's favorites
        Cursor cursor = database.query(DatabaseHelper.TABLE_FAVORITES, null, selection, selectionArgs,
                null, null, null);

        boolean isFavorite = cursor.getCount() > 0;  // If cursor returns any row, it means it's a favorite
        cursor.close();

        return isFavorite;
    }

    public Sport getSportByName(String sportName) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SPORT + " WHERE " + DatabaseHelper.COL_NAME + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{sportName});
        Sport sport = null;

        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
            String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION));
            String district = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DISTRICT));
            String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PRICE));
            String rating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RATING));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DESCRIPTION));
            String service = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SERVICE));

            sport = new Sport(id, type, name, location, district, rating, price, description, service);
        }

        cursor.close();
        return sport;
    }

    public Cursor getFavoritesByUser(String user) {
        return database.query(DatabaseHelper.TABLE_FAVORITES, null, DatabaseHelper.C_FAVORITE_USER + " = ?",
                new String[]{user}, null, null, null);
    }

    public ArrayList<String> getSNames() {
        ArrayList<String> sNames = new ArrayList<>();
        Cursor cursor = database.rawQuery(" SELECT " + DatabaseHelper.COL_NAME + " FROM " + DatabaseHelper.TABLE_SPORT, null);

        if (cursor.moveToFirst()) {
            do {
                sNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sNames;
    }

    public Map<String, Integer> calculateProfitByTenSan() {
        Map<String, Integer> profitMap = new HashMap<>();
        String query = "SELECT " + DatabaseHelper.COLUMN_COURT_NAME + ", SUM(" + DatabaseHelper.COLUMN_TOTAL_PRICE + ") as total FROM " + DatabaseHelper.TABLE_BOOKINGS + " GROUP BY " + DatabaseHelper.COLUMN_COURT_NAME;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tenSan = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COURT_NAME));
                int total = cursor.getInt(cursor.getColumnIndex("total"));
                profitMap.put(tenSan, total);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return profitMap;
    }

    public Map<String, Integer> calculateProfitByUser() {
        Map<String, Integer> profitMap = new HashMap<>();
        String query = "SELECT " + DatabaseHelper.COLUMN_USERNAMEORDER + ", SUM(" + DatabaseHelper.COLUMN_TOTAL_PRICE + ") as total FROM " + DatabaseHelper.TABLE_BOOKINGS + " GROUP BY " + DatabaseHelper.COLUMN_USERNAMEORDER;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String user = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAMEORDER));
                int total = cursor.getInt(cursor.getColumnIndex("total"));
                profitMap.put(user, total);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return profitMap;
    }
}