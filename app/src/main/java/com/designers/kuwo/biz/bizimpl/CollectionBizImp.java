package com.designers.kuwo.biz.bizimpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.biz.CollectionBiz;
import com.designers.kuwo.dao.CollectionDao;
import com.designers.kuwo.dao.daoimpl.CollectionDaoImpl;
import com.designers.kuwo.entity.ICollection;
import com.designers.kuwo.entity.Song;
import com.designers.kuwo.sqlite.SQLiteDatabaseManager;
import com.designers.kuwo.sqlite.TransactionManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 跃 on 2017/2/22.
 */
public class CollectionBizImp implements CollectionBiz {
    private boolean flag = false;
    private SQLiteDatabaseManager sqLiteDatabaseManager;
    private CollectionDao collectionDao;

    public CollectionBizImp() {
        this.sqLiteDatabaseManager = new SQLiteDatabaseManager();
        this.collectionDao = new CollectionDaoImpl();
    }

    /**
     * 添加收藏
     *
     * @param iCollection
     * @param context
     * @return
     */
    @Override
    public boolean alterCollection(ICollection iCollection, Context context) {

        //获取数据库连接
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);
        //开启事务
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.beginTransaction(sqLiteDatabase);
        try {
            flag = collectionDao.insertCollection(iCollection, sqLiteDatabase);
            Log.i("数据库操作：", "biz层执行了插入收藏的操作。。。。flag=" + flag);
            if (flag) {
                transactionManager.commitTransaction(sqLiteDatabase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            transactionManager.endTransaction(sqLiteDatabase);
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }

        return flag;
    }

    /**
     * 删除收藏
     *
     * @param song
     * @param singer
     * @param account
     * @param context
     * @return
     */
    @Override
    public boolean removeCollection(String song, String singer, String account, Context context) {
        //获取数据库连接
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);
        //开启事务
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.beginTransaction(sqLiteDatabase);
        try {
            flag = collectionDao.deleteCollection(song, singer, account, sqLiteDatabase);
            Log.i("数据库操作：", "biz层执行了删除收藏的操作。。。。flag=" + flag);
            if (flag) {
                transactionManager.commitTransaction(sqLiteDatabase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            transactionManager.endTransaction(sqLiteDatabase);
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }

        return flag;
    }

    /**
     * 查询是否收藏
     *
     * @param song
     * @param singer
     * @param account
     * @param context
     * @return
     */
    @Override
    public boolean findCollection(String song, String singer, String account, Context context) {
        //获取数据库连接
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);
        try {
            flag = collectionDao.selectCollection(song, singer, account, sqLiteDatabase);
            Log.i("数据库操作：", "biz层执行了查询是否收藏的操作。。。。flag=" + flag);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
        return flag;
    }

    @Override
    public List<Song> findCollectionByName(Context context, String songName, String singer) {
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);
        try {

            Log.i("数据库操作：", "biz层执行了查询收藏的操作    selectCollectionByName");
            return collectionDao.selectCollectionByName(sqLiteDatabase, songName, singer);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }

    @Override
    public List<Song> findCollectionSortAll(Context context) {
        SQLiteDatabase sqLiteDatabase = sqLiteDatabaseManager.getDatabaseByRead(context);
        try {
            Log.i("数据库操作：", "biz层执行了查询收藏所有歌曲的操作    selectCollectionSortAll");
            return collectionDao.selectCollectionSortAll(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
        }
    }
}
