package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.dao.SingerDao;
import com.designers.kuwo.entity.Singer;
import com.designers.kuwo.sqlite.SQLManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public class SingerDaoImpl implements SingerDao {

    private SQLManager sqlManager = null;

    public SingerDaoImpl(){
        this.sqlManager = new SQLManager();
    }

    @Override
    public List<Singer> selectSinger(SQLiteDatabase sqLiteDatabase) {
        List<Singer> singerList =new ArrayList<Singer>();
        String sql="select singer,singerUri,count(1) from songs group by singer";
        String[] selectionArgs = new String[]{};
        Cursor cursor=sqlManager.execRead(sqLiteDatabase,sql,selectionArgs);
        Log.i("数据库执行sql:",sql);
        while (cursor.moveToNext()){
            Singer singer=new Singer();
            singer.setSinger(cursor.getString(0));
            singer.setSingerUri(cursor.getString(1));
            singer.setSongNum(cursor.getInt(2));
            singerList.add(singer);
        }
        return singerList;
    }

    @Override
    public List<Singer> selectSongBySinger(SQLiteDatabase sqLiteDatabase,String singer) {
        List<Singer> songList =new ArrayList<Singer>();
        String sql="select songName,songUri,singer from songs where singer=?";
        String[] selectionArgs = new String[]{singer};
        Cursor cursor=sqlManager.execRead(sqLiteDatabase,sql,selectionArgs);
        Log.i("数据库执行sql:",sql);
        while (cursor.moveToNext()){
            Singer singer1=new Singer();
            singer1.setSongName(cursor.getString(0));
            singer1.setSongUri(cursor.getString(1));
            singer1.setSinger(cursor.getString(2));
            songList.add(singer1);
        }
        return songList;
    }

}
