package com.designers.kuwo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.entity.Album;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PC-CWB on 2017/2/22.
 */
public interface AlbumDao {

    public abstract void insert(final SQLiteDatabase sqLiteDatabase, Album album) throws SQLException;

    public abstract List<Album> selectAlbum(SQLiteDatabase sqLiteDatabase)throws SQLException;

    public abstract List<Album> selectSongByAlbum(SQLiteDatabase sqLiteDatabase, String albums)throws SQLException;
}
