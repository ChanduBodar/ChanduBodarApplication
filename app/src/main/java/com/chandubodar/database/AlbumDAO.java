
package com.chandubodar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.chandubodar.model.Item;

import java.util.List;

@Dao
public interface AlbumDAO {
    @Insert
    public void insert(Item item);

    @Delete
    public void delete(Item item);

    @Query("SELECT * FROM Item")
    public List<Item> getCartList();

}

