package com.designers.kuwo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.entity.Song;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
public interface SongDao {
    public abstract void insert(final SQLiteDatabase sqLiteDatabase, Song song) throws SQLException;
    public abstract List<Map<String, Object>> select(final SQLiteDatabase sqLiteDatabase) throws SQLException;

    public abstract List<Map<String,Object>> selectFolder(final SQLiteDatabase sqLiteDatabase)throws SQLException;

    public abstract List<Map<String,Integer>> selectSongNum(final SQLiteDatabase sqLiteDatabase)throws SQLException;
}
