
package com.chandubodar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.chandubodar.model.Item;

@Database(entities = {Item.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "myCart.db";
    private static AppDatabase instance;

    /**
     * Initializes the database.
     *
     * @param context the context.
     * @throws IllegalStateException if database is already initialized.
     */
    public static void init(Context context) {
        if (instance != null) {
            throw new IllegalStateException("AppDatabase already initialized");
        }
        instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
    }

    /**
     * Checks whether database is initialized or not.
     *
     * @return <code>true</code> if initialized already; <code>false</code> otherwise.
     */
    public static boolean isInitialized() {
        return instance != null;
    }

    /**
     * Returns the {@link AppDatabase} instance.
     *
     * @throws IllegalStateException if database is not initialized yet.
     */
    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AppDatabase not initialized yet.");
        }
        return instance;
    }

    public abstract AlbumDAO getAlbumDAO();

}

