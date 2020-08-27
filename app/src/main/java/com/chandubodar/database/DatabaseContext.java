
package com.chandubodar.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public class DatabaseContext extends ContextWrapper {
    private static final String TAG = DatabaseContext.class.getSimpleName();
    private static final String DATABASE_FOLDER_NAME = "album";

    @Nullable
    public Context context = null;

    public DatabaseContext(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public File getDatabasePath(String name) {
        Log.v(TAG, "getDatabasePath(): name = " + name);

        File sdcard = Environment.getExternalStorageDirectory();
        String dbFileName = sdcard.getAbsolutePath() + File.separator + DATABASE_FOLDER_NAME + File.separator + name;
        if (!dbFileName.endsWith(".db")) {
            dbFileName += ".db";
        }

        File dbFile = new File(dbFileName);
        if (!dbFile.getParentFile().exists()) {
            boolean directoryCreated = dbFile.getParentFile().mkdirs();
            Log.d(TAG, "DB directory created = " + directoryCreated);
        }
        Log.d(TAG, "Database path = " + dbFile.getAbsolutePath());

        return dbFile;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(getDatabasePath(name).getAbsolutePath(), mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return super.openOrCreateDatabase(getDatabasePath(name).getAbsolutePath(), mode, factory, errorHandler);
    }
}
