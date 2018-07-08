package pawlliance.com.pawlliance.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pawlliance.com.pawlliance.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // setting database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "PawllianceDatabase.db";

    // setting up Pawlliance user (profile) table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_CITY = "user_city";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Create User Table SQL Query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_CITY + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // Drop User Table SQL Query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    /**
     * Constructor
     *
     * @param context
     */

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // drop User Table is exists
        db.execSQL(DROP_USER_TABLE);
        // create tables again
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getOwnerFullName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CITY, user.getCity());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getWritableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user database with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'email@email.com';
         */

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns, // columns to return
                selection, // columns for the where clsue
                selectionArgs, // the values for the WHERE clause
                null, //group by the rows
                null, // filter by row groups
                null); // the sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */

    public boolean checkUser(String email, String password){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'email@email.com';
         */
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        }
        return false;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */

    public List<User> getAllUser(){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_CITY
        };

        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";

        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        //query the user table
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setOwnerFullName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CITY)));

                //adding new user record to the list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user List
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getOwnerFullName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_CITY, user.getCity());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserID())});

    }

        // DNC - class closing
}
