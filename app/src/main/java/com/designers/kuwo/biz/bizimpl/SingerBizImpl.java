package com.designers.kuwo.biz.bizimpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.biz.SingerBiz;
import com.designers.kuwo.dao.SingerDao;
import com.designers.kuwo.dao.daoimpl.SingerDaoImpl;
import com.designers.kuwo.entity.Singer;
import com.designers.kuwo.sqlite.SQLiteDatabaseManager;

import java.util.List;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public class SingerBizImpl implements SingerBiz {

    private SingerDao singerDao;
    private SQLiteDatabaseManager sqLiteDatabaseManager;

    public SingerBizImpl() {
        this.singerDao=new SingerDaoImpl();
        this.sqLiteDatabaseManager=new SQLiteDatabaseManager();
    }

    @Override
    public List<Singer> SingerFind(Context context) {

        //获得数据库连接
        SQLiteDatabase sqLiteDatabase= sqLiteDatabaseManager.getDatabaseByRead(context);
        Log.i("数据库操作：","获得数据库连接。。。。。");
        try {
        Log.i("数据库操作：","查询Singer。。。。。");
             return singerDao.selectSinger(sqLiteDatabase);//这句话没有执行
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
            Log.i("数据库操作：","数据库关闭");
        }
    }

    @Override
    public List<Singer> FindSongBySinger(Context context, String singer) {
        //获得数据库连接
        SQLiteDatabase sqLiteDatabase= sqLiteDatabaseManager.getDatabaseByRead(context);
        Log.i("数据库操作：","获得数据库连接。。。。。");
        try {
            Log.i("数据库操作：","条件查询Songs。。。。。");
            return singerDao.selectSongBySinger(sqLiteDatabase,singer);//这句话没有执行
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            sqLiteDatabaseManager.closeSQLiteDatabase(sqLiteDatabase);
            Log.i("数据库操作：","数据库关闭");
        }
    }
}
