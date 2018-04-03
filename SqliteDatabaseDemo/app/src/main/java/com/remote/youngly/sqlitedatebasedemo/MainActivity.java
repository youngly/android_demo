package com.remote.youngly.sqlitedatebasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.remote.youngly.sqlitedatebasedemo.FeedReaderContract.FeedEntry;

public class MainActivity extends AppCompatActivity {

    private FeedReaderDbHelper mDbHelper;

    private TextView databaseInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseInfoView = (TextView) findViewById(R.id.database_info_view);
        mDbHelper = new FeedReaderDbHelper(this);
    }

    public void insert(View view) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "news");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "detail");
        long index = database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        databaseInfoView.setText(String.valueOf(index) + "has inserted");
    }

    public void delete(View view) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = FeedEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"2"};
        // Issue SQL statement.
        int deletedRows = database.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void update(View view) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

        // Which row to update, based on the title
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {"news"};

        int count = db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);

        
    }

    public void query(View view) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String[] projection = new String[]{FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE, FeedEntry.COLUMN_NAME_SUBTITLE};
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { "news" };
        Cursor cursor = database.query(FeedEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            stringBuilder.append(cursor.getString(0)).append(" ,")
                    .append(cursor.getString(1)).append(" ,")
                    .append(cursor.getString(2)).append("\n");
        }
        databaseInfoView.setText(stringBuilder.toString());
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
