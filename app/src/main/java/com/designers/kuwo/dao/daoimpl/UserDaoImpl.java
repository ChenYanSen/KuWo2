package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.dao.UserDao;
import com.designers.kuwo.entity.User;
import com.designers.kuwo.sqlite.SQLManager;
import com.designers.kuwo.sqlite.TransactionManager;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by 跃 on 2017/1/12.
 */
public class UserDaoImpl implements UserDao {
    private SQLManager sqlManager = null;
    private TransactionManager transactionManager=null;
    public UserDaoImpl() {
        super();
        this.sqlManager = new SQLManager();
        this.transactionManager=new TransactionManager();
    }

    @Override
    public boolean findByName(String account, SQLiteDatabase sqLiteDatabase) throws SQLException {
        boolean flag = false;
        String sql = "select * from user where account  = ?";
        String[] selectionArgs = new String[] { account };
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        if (cursor.moveToNext()) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean insertUser(User user, SQLiteDatabase sqLiteDatabase) throws SQLException {
        boolean flag=false;
        //用户账号
         String account=user.getAccount();
        //密码
       String password=user.getPassword();
        //爱好
        /*   String hobby=user.getHobby();
        //昵称
         String nickname=user.getNickname();
        //个性签名
         String signature=user.getSignature();
        //性别
         String sex=user.getSex();
        //地区
         String area=user.getArea();
        //头像地址
         String avatarUri=user.getAvatarUri();*/


        String sql = "insert into user(account, password) values(?, ?)";
        String[] bindArgs = new String[] { account, password };
        flag = sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
        Date endDate = new Date(System.currentTimeMillis());
        return flag;
    }
}
