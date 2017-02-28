package com.designers.kuwo.dao;

import android.database.sqlite.SQLiteDatabase;


import com.designers.kuwo.entity.Singer;

import java.util.List;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public interface SingerDao {

    /**
     * select singer info for singer of basic
     * @param sqLiteDatabase
     * @return
     */
    public List<Singer> selectSinger(SQLiteDatabase sqLiteDatabase);

    /**
     * select song info by singer
     */
    public List<Singer> selectSongBySinger(SQLiteDatabase sqLiteDatabase,String singer);

}
