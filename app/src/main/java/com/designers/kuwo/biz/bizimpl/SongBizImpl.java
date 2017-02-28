package com.designers.kuwo.biz.bizimpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.biz.SongBiz;
import com.designers.kuwo.dao.SongDao;
import com.designers.kuwo.dao.daoimpl.SongDaoImpl;
import com.designers.kuwo.entity.Song;
import com.designers.kuwo.sqlite.SQLiteDatabaseManager;
import com.designers.kuwo.sqlite.TransactionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
public class SongBizImpl implements SongBiz {

    private SongDao songDao = null;

    public SongBizImpl() {
        this.songDao = new SongDaoImpl();
    }

    @Override
    public void insert(Context context, Song song) {
        SQLiteDatabaseManager sqLiteDatabaseManager = new SQLiteDatabaseManager();
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByWrite(context);
        //开启事务
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.beginTransaction(sqLiteDatabase);

        try {
            this.songDao.insert(sqLiteDatabase, song);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            transactionManager.commitTransaction(sqLiteDatabase);
            transactionManager.endTransaction(sqLiteDatabase);
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }

    @Override
    public List<Map<String, Object>> findAll(Context context) {
        SQLiteDatabaseManager sqLiteDatabaseManager = new SQLiteDatabaseManager();
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);

        try {
            return this.songDao.select(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }

    @Override
    public List<Map<String,Object>> findFolder(Context context) {
        SQLiteDatabaseManager sqLiteDatabaseManager=new SQLiteDatabaseManager();
        SQLiteDatabase sqLiteDatabase=sqLiteDatabaseManager.getDatabaseByRead(context);
        try{
            return this.songDao.selectFolder(sqLiteDatabase);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }

    @Override
    public List<Map<String,Integer>> findSongNum(Context context) {
        SQLiteDatabaseManager sqLiteDatabaseManager=new SQLiteDatabaseManager();
        SQLiteDatabase sqLiteDatabase=sqLiteDatabaseManager.getDatabaseByRead(context);
        try{
            return this.songDao.selectSongNum(sqLiteDatabase);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }
}
