package com.example.myblogapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.myblogapp.model.ModelClass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Blogging.db";
    public static final String TABLENAME = "articles";
    public static final int VER = 1;

    public static final String COLUMN_ARTICLE_ID = "id";
    public static final String COLUMN_BLOGGER_NAME = "name";
    public static final String COLUMN_ARTICLE_DESCRIPTION = "article_description";
    public static final String COLUMN_ARTICLE_TITLE = "article_title";
    public static final String COLUMN_ARTICLE_DATE = "article_date";
    public static final String COLUMN_ARTICLE_PICTURE = "picture";

    public DbHelper(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableContact = "CREATE TABLE " + TABLENAME + " (" + COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BLOGGER_NAME + " TEXT," + COLUMN_ARTICLE_DESCRIPTION + " TEXT,"
                + COLUMN_ARTICLE_TITLE + " TEXT," + COLUMN_ARTICLE_DATE + " INT,"
                + COLUMN_ARTICLE_PICTURE + " BLOB)";
        db.execSQL(createTableContact);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    //USER DEFINED FUNCTIONS
    public byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public List<ModelClass> getEveryone() {

        List<ModelClass> returnList = new ArrayList<>();

        // get data from the database

        String queryString = "SELECT * FROM " + TABLENAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {

            // loop through the cursor (result set) and create new customer object. Put them into the result list.

            do {

                int articleID = cursor.getInt(0);
                String articleName = cursor.getString(1);
                String articleDes = cursor.getString(2);
                String articleTitle = cursor.getString(3);
                Long articleDate = Long.valueOf(cursor.getInt(4));
                byte[] articleImage = cursor.getBlob(5);

                ModelClass modelClass = new ModelClass(articleID, articleName, articleDes, articleTitle, articleDate, articleImage);
                returnList.add(modelClass);


            } while (cursor.moveToNext());

        } else {
            // Failure. Do not add anything to the list.
        }

        // Close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    /*public boolean addContact(ModelClass modelClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(COLUMN_PERSON_NAME, modelClass.getName());
        cv.put(COLUMN_BLOGGER_NAME, modelClass.getBlogger_name());
        cv.put(COLUMN_ARTICLE_DESCRIPTION, modelClass.getArticle_Description());
        cv.put(COLUMN_ARTICLE_TITLE, modelClass.getArticle_Title());
        cv.put(COLUMN_ARTICLE_DATE, modelClass.getArticle_Date());
        cv.put(COLUMN_ARTICLE_PICTURE, ImageViewToByte()); // yahan pey image pass karni hai

        long insert = db.insert(TABLENAME, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }*/
}
