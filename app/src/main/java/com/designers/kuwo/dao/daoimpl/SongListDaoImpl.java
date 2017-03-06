package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.dao.SongListDao;
import com.designers.kuwo.eneity.SongList;
import com.designers.kuwo.sqlite.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21.
 */
public class SongListDaoImpl implements SongListDao {

    private SQLManager sqlManager;

    public SongListDaoImpl() {
        this.sqlManager = new SQLManager();
    }

    @Override
    public void insert(SQLiteDatabase sqLiteDatabase, SongList songList) throws SQLException {
        String songListName = songList.getSongListName();
        String account = songList.getAccount();
        String songName = songList.getSongName();
        String sql = "insert into songList(songListName, songName, account) values(?,?,?)";
        String[] bindArgs = new String[]{songListName, songName, account};

        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
    }

    @Override
    public List<Map<String, Object>> selectNameAndSize(SQLiteDatabase sqLiteDatabase, String userName) throws SQLException {
        List<Map<String, Object>> songFromList = new ArrayList<Map<String, Object>>();
        String sql = "select songListName, count(songName) from " +
                "(select songListName, songName from songList where account = ?) " +
                "group by songListName";
        String[] selectionArgs = new String[]{userName};
        Cursor cursor = this.sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", cursor.getString(0));
            map.put("size", cursor.getString(1) + "首");
            map.put("expend", false);
            songFromList.add(map);
        }
        return songFromList;
    }

    @Override
    public void update(SQLiteDatabase sqLiteDatabase, String oldName, String newName, String userName) throws SQLException {
        String sql = "update songList set songListName = ? where songListName = ? and account = ?";
        String[] bindArgs = new String[]{newName, oldName, userName};
        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
    }

    @Override
    public void delete(SQLiteDatabase sqLiteDatabase, String songListName, String userName) throws SQLException {
        String sql = "delete from songList where songListName = ? and account = ?";
        String[] bindArgs = new String[]{songListName, userName};
        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
    }

    @Override
    public List<Map<String, Object>> select(SQLiteDatabase sqLiteDatabase) throws SQLException {
        List<Map<String, Object>> songFromList = new ArrayList<Map<String, Object>>();
        String sql = "select songListName, songName, account from songList";
        String[] selectionArgs = new String[]{};
        Cursor cursor = this.sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("listName", cursor.getString(0));
            map.put("songName", cursor.getString(1));
            map.put("user", cursor.getString(2));
            songFromList.add(map);
        }
        return songFromList;
    }

    @Override
    public void deleteSong(SQLiteDatabase sqLiteDatabase, String songListName, String songName, String userName) throws SQLException {
        String sql = "delete from songList where songListName = ? and songName = ? and account = ?";
        String[] bindArgs = new String[]{songListName, songName, userName};
        this.sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
    }
}
