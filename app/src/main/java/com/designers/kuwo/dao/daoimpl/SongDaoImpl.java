package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.dao.SongDao;
import com.designers.kuwo.entity.Song;
import com.designers.kuwo.sqlite.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
public class SongDaoImpl implements SongDao {

    private SQLManager sqlManager;

    public SongDaoImpl() {
        this.sqlManager = new SQLManager();
    }

    /**
     * 插入歌曲信息
     *
     * @param sqLiteDatabase
     * @param song
     */
    @Override
    public void insert(SQLiteDatabase sqLiteDatabase, Song song) throws SQLException {
        String songName = song.getSongName();
        String singer = song.getSinger();
        String songUri = song.getSongUri();
        String songImage = song.getSongImage();
        String singLyrics = song.getSingLyrics();
        String information = song.getInformation();
        String time = song.getTime();
        String singerUri=song.getSingerUri();
        String albumUri=song.getAlbumUri();
        String folder=song.getFolder();
        String sql = "insert into songs(songName, singer, songUri, songImage, singlyrics, information, time,songUri,albumUri,folder) values(?,?,?,?,?,?,?,?,?,?)";
        Log.i("数据库操作：",sql);
        String[] bindArgs = new String[]{songName, singer, songUri, songImage, singLyrics, information, time,singerUri,albumUri,folder};
        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
        Log.i("数据库操作：","数据写入已经执行");
    }

    /**
     * 查询所有歌曲信息
     *
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> select(SQLiteDatabase sqLiteDatabase) throws SQLException {
        List<Map<String, Object>> songList = new ArrayList<Map<String, Object>>();
        String sql = "select songName, singer, songUri, songImage, singlyrics, information, time from songs";
        String[] selectionArgs = new String[]{};
        Cursor cursor = this.sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        Log.i("数据库操作：","查询音乐列表数据"+sql);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("songName", cursor.getString(0));
            map.put("singer", cursor.getString(1));
            map.put("songUri", cursor.getString(2));
            map.put("songImage", cursor.getString(3));
            map.put("singLyrics", cursor.getString(4));
            map.put("information", cursor.getString(5));
            map.put("time", cursor.getString(6));
            map.put("expend", false);
            map.put("checked", false);
            songList.add(map);
        }
        return songList;
    }

    @Override
    public List<Map<String,Object>> selectFolder(SQLiteDatabase sqLiteDatabase) throws SQLException {
        List<Map<String,Object>> folderlist=new ArrayList<Map<String, Object>>();
        String sql="select distinct folder from songs";
        String [] selectionArgs=new String[]{};
        Cursor cursor=this.sqlManager.execRead(sqLiteDatabase,sql,selectionArgs);
        Log.i("数据库操作：","查询音乐文件夹  folder " + sql);
        while (cursor.moveToNext()){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("folder",cursor.getString(0));
            folderlist.add(map);
        }
        return folderlist;
    }

    @Override
    public List<Map<String,Integer>> selectSongNum(SQLiteDatabase sqLiteDatabase) throws SQLException {
        List<Map<String,Integer>> songnumList=new ArrayList<Map<String,Integer>>();
        String sql="select folder,count(1) from songs group by folder";
        String [] selectionArgs=new String[]{};
        Cursor cursor=this.sqlManager.execRead(sqLiteDatabase,sql,selectionArgs);
        while (cursor.moveToNext()){
            Map<String,Integer> map=new HashMap<String,Integer>();
            map.put("songNum",cursor.getInt(1));
            Log.i("SongImplDao","songNum"+cursor.getInt(1));
            songnumList.add(map);
        }
        return songnumList;
    }
}
