package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vanwingerdenbarrier.sheetmusictutor.Game.Question;

/**
 * Database for storing user information
 */

public class UserDB extends SQLiteOpenHelper{

    //Database name
    public static final String DATABASE_NAME = "user.db";

    // Current version of database
    public static final int DATABASE_VERSION = 4;

    // Database table name
    public static final String USER_TABLE = "UserTable";

    //All fields used in database table
    public static final String KEY_ID = "id";
    public static final String NAME = "name";
    public static final String ATTEMPTS  = "attempts";
    public static final String CORRECT = "correct";
    public static final String CURRENT_LEVEL = "current_level";
    public static final String IS_CURRENT = "is_current";
    public static final String NUM_POINTS_NEEDED = "num_points_needed";
    public static final String HERO_LEVEL = "hero_level";
    public static final String DEFENSE_LEVEL = "defense_level";
    public static final String QUIZ_LEVEL = "quiz_level";
    public static final String SHOW_KEY = "show_key";
    public static final String COMBO_PREF = "combo_pref";

    // Question Table Create Query in this string
    private static final String CREATE_USER_TABLE = "CREATE TABLE "
            + USER_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + NAME +" TEXT,"
            + ATTEMPTS + " INTEGER,"
            + CORRECT + " INTEGER, "
            + CURRENT_LEVEL + " INTEGER, "
            + IS_CURRENT + " INTEGER, "
            + HERO_LEVEL + " INTEGER, "
            + DEFENSE_LEVEL + " INTEGER, "
            + QUIZ_LEVEL + " INTEGER, "
            + NUM_POINTS_NEEDED + " INTEGER,"
            + COMBO_PREF + " INTEGER,"
            + SHOW_KEY + " INTEGER);";


    public UserDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /** Change back to this for production
     *
     * @param context
     */
    public UserDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println(CREATE_USER_TABLE);
    }


    /**
     * This is for testing only so we can access the database!
     *

     public DatabaseHelper(Context context) {
     super(context, "/mnt/sdcard/"+DATABASE_NAME, null, 1);
     }
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);//Create Question Table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase writeUserDB(){
        return this.getWritableDatabase();
    }

    public SQLiteDatabase readUserDb(){
        return this.getWritableDatabase();
    }

}