package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.dao.AlbumDao;
import com.designers.kuwo.entity.Album;
import com.designers.kuwo.sqlite.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-CWB on 2017/2/22.
 */
public class AlbumDaoImpl implements AlbumDao {

    private SQLManager sqlManager;

    public AlbumDaoImpl() {
        this.sqlManager = new SQLManager();
    }

    /**
     * 插入专辑信息
     *
     * @param sqLiteDatabase
     * @param album
     * @throws SQLException
     */
    @Override
    public void insert(SQLiteDatabase sqLiteDatabase, Album album) throws SQLException {
        String albumName = album.getAlbumName();
        String songName = album.getSongName();
        String albumUri = album.getAlbumUri();
        String singer = album.getSinger();
        String sql = "insert into album(albumName,songName,singer,albumUri) values(?,?,?,?)";
        Log.i("数据库操作：", sql);
        String[] bindArgs = new String[]{albumName, songName, albumUri, singer};
        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
        Log.i("数据库操作：", "数据写入已经执行");
    }

    /**
     * 查询出专辑所需新的数据添加到专辑对象中
     *
     * @param sqLiteDatabase
     * @return
     */

    @Override
    public List<Album> selectAlbum(SQLiteDatabase sqLiteDatabase) {
        List<Album> albumList = new ArrayList<Album>();
        String sql = "select distinct albumName,albumUri,count(1) from album group by albumName";
        String[] selectionArgs = new String[]{};
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        Log.i("数据库执行sql:", sql);
        while (cursor.moveToNext()) {
            Album album = new Album();
            album.setAlbumName(cursor.getString(0));
            album.setAlbumUri(cursor.getString(1));
            album.setSongNum(cursor.getInt(2));
            albumList.add(album);
        }
        return albumList;
    }

    /**
     * 通过条件查询将所属专辑的歌曲查找出来
     *
     * @param sqLiteDatabase
     * @param albums
     * @return
     */
    @Override
    public List<Album> selectSongByAlbum(SQLiteDatabase sqLiteDatabase, String albums) {
        List<Album> songListByAlbum = new ArrayList<Album>();
        String sql = "select distinct albumName,songName from album where albumName = ?";
        String[] selectionArgs = new String[]{albums};
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        while (cursor.moveToNext()) {
            Album album = new Album();
            album.setAlbumName(cursor.getString(0));
            album.setSongName(cursor.getString(1));
            songListByAlbum.add(album);
        }
        return songListByAlbum;
    }
}
