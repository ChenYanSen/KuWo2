package com.designers.kuwo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.entity.User;

import java.sql.SQLException;

/**
 * Created by 跃 on 2017/1/12.
 */
public interface UserDao {

    /**
     * 判断用户是否存在
     */
    public boolean findByName(String account, final SQLiteDatabase sqLiteDatabase)
            throws SQLException;

    //用户注册
    public boolean insertUser(final User user,final SQLiteDatabase sqLiteDatabase)throws SQLException;
}
