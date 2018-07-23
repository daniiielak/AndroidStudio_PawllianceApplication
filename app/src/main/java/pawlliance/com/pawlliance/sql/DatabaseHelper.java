package pawlliance.com.pawlliance.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String COLUMN_USER_DOGNAME = "user_dogname";
    private static final String COLUMN_USER_DOGBREED ="user_dogbreed";
    private static final String COLUMN_USER_DOGBIRTHDAY = "user_dogbirthday";
    private static final String COLUMN_USER_DOGGENDER = "user_doggender";
    private static final String COLUMN_USER_DOGIMAGE = "user_dogimage";
    private static final String COLUMN_USER_DOGDESCRIPTION = "user_dogdescription";


    // Create User Table SQL Query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_CITY + " TEXT," + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_DOGNAME + " TEXT," + COLUMN_USER_DOGBREED + " TEXT," + COLUMN_USER_DOGBIRTHDAY + " TEXT,"
            + COLUMN_USER_DOGGENDER + " TEXT," + COLUMN_USER_DOGIMAGE + " BLOB," + COLUMN_USER_DOGDESCRIPTION + " TEXT" + ")";

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
        values.put(COLUMN_USER_DOGNAME, user.getDogName());
        values.put(COLUMN_USER_DOGBREED, user.getDogBreed());
        values.put(COLUMN_USER_DOGBIRTHDAY, user.getBirthday());
        values.put(COLUMN_USER_DOGGENDER, user.getDogGender());
        values.put(COLUMN_USER_DOGIMAGE, user.getImagePath());
        values.put(COLUMN_USER_DOGDESCRIPTION, user.getDescription());

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
                COLUMN_USER_CITY,
                COLUMN_USER_DOGNAME,
                COLUMN_USER_DOGBREED,
                COLUMN_USER_DOGBIRTHDAY,
                COLUMN_USER_DOGGENDER,
                COLUMN_USER_DOGIMAGE,
                COLUMN_USER_DOGDESCRIPTION
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
                user.setDogName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGNAME)));
                user.setDogBreed(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGBREED)));
                user.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGBIRTHDAY)));
                user.setDogGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGGENDER)));
                user.setImagePath(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGIMAGE)));
                user.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOGDESCRIPTION)));

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
     * This method is to fetch a single user based on the email address provided
     *
     * @return list
     */

    public User getSpecificUser(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USER_EMAIL + "= '" + userEmail + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

        User specificUser = new User();
        specificUser.setUserID(c.getInt(c.getColumnIndex(COLUMN_USER_ID)));
        specificUser.setEmail(c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)));
        specificUser.setOwnerFullName(c.getString(c.getColumnIndex(COLUMN_USER_NAME)));
        specificUser.setPassword(c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)));
        specificUser.setCity(c.getString(c.getColumnIndex(COLUMN_USER_CITY)));
        specificUser.setDogName(c.getString(c.getColumnIndex(COLUMN_USER_DOGNAME)));
        specificUser.setDogBreed(c.getString(c.getColumnIndex(COLUMN_USER_DOGBREED)));
        specificUser.setBirthday(c.getString(c.getColumnIndex(COLUMN_USER_DOGBIRTHDAY)));
        specificUser.setDogGender(c.getString(c.getColumnIndex(COLUMN_USER_DOGGENDER)));
        specificUser.setImagePath(c.getString(c.getColumnIndex(COLUMN_USER_DOGIMAGE)));
        specificUser.setDescription(c.getString(c.getColumnIndex(COLUMN_USER_DOGDESCRIPTION)));

        c.close();
        db.close();

        return specificUser;
        }

        c.close();
        db.close();
    return null;
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
        values.put(COLUMN_USER_DOGNAME, user.getDogName());
        values.put(COLUMN_USER_DOGBREED, user.getDogBreed());
        values.put(COLUMN_USER_DOGBIRTHDAY, user.getBirthday());
        values.put(COLUMN_USER_DOGGENDER, user.getDogGender());
        values.put(COLUMN_USER_DOGIMAGE, user.getImagePath());
        values.put(COLUMN_USER_DOGDESCRIPTION, user.getDescription());

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
        db.close();

    }


    /**
     * This method iis for the AndroidDataBaseHelper to show data
     *
     */
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

        // DNC - class closing
}
