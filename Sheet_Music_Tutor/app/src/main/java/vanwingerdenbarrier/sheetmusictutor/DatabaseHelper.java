package vanwingerdenbarrier.sheetmusictutor;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;


/**
 * Created by Doriangh4 on 11/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    //Database name
    public static final String DATABASE_NAME = "questionStorage2.db";

    // Current version of database
    public static final int DATABASE_VERSION = 1;

    // Database table name
    public static final String QUESTION_TABLE = "QuestionStorage";

    //All fields used in database table
    public static final String KEY_ID = "id";
    public static final String QUESTION = "question";
    public static final String CHOICE1  = "choice1";
    public static final String CHOICE2 = "choice2";
    public static final String CHOICE3 = "choice3";
    public static final String DIFFICULTY = "difficulty";
    public static final String ANSWER = "answer";

    // Question Table Create Query in this string
    private static final String CREATE_QUESTION_TABLE = "CREATE TABLE "
            + QUESTION_TABLE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTION + " TEXT,"
            + CHOICE1 + " TEXT, " + CHOICE2 + " TEXT, " + CHOICE3 + " TEXT, "
            + DIFFICULTY + " TEXT, " + ANSWER + " TEXT);";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /** Change back to this for production
     *
     * @param context
     */
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
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
        db.execSQL(CREATE_QUESTION_TABLE);//Create Question Table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_TABLE);
        onCreate(sqLiteDatabase);
    }


    /**
     * This method is used to add initial question in the table
     * @param question
     * @return
     */
    public long addFirstQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        //Creatibg content values
        ContentValues values = new ContentValues();
        values.put(QUESTION, question.getQuestion());
        values.put(CHOICE1, question.getChoice(0));
        values.put(CHOICE2, question.getChoice(1));
        values.put(CHOICE3, question.getChoice(2));
        values.put(DIFFICULTY, question.getDifficulty());
        values.put(ANSWER, question.getAnswer());
        //Insert row in question table
        long insert = db.insert(QUESTION_TABLE, null, values);
        return insert;

    }

    public List<Question> getAllQuestionsList(){
        List<Question> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + QUESTION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Looping through all records and adding to list
        if(c.moveToFirst()){
            do {
                Question question = new Question();

                String questionText = c.getString(c.getColumnIndex(QUESTION));
                question.setQuestion(questionText);

                String choice1Text = c.getString(c.getColumnIndex(CHOICE1));
                question.setChoice(0,choice1Text);

                String choice2Text = c.getString(c.getColumnIndex(CHOICE2));
                question.setChoice(1,choice2Text);

                String choice3Text = c.getString(c.getColumnIndex(CHOICE3));
                question.setChoice(2,choice3Text);

                String difficultyText = c.getString(c.getColumnIndex(DIFFICULTY));
                question.setDifficulty(difficultyText);

                String answerText = c.getString(c.getColumnIndex(ANSWER));
                question.setAnswer(answerText);

                //adding questions to list
                questionArrayList.add(question);

            }while (c.moveToNext());
        }
        return questionArrayList;
    }


}