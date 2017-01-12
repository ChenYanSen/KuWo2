package com.designers.kuwo.activitys;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.designers.kuwo.sqlite.SqLiteDatabaseHelper;

/**
 * Created by 跃 on 2017/1/12.
 */
public class CustomApplication extends Application{
    //用户账号
    private String current_user;
    //用户头像地址
    private Uri avatarUri;


    @Override
    public void onCreate() {

        SqLiteDatabaseHelper sqLiteDatabaseHelper = new SqLiteDatabaseHelper(getApplicationContext());
        //获取连接
        SQLiteDatabase SqliteDatabase = sqLiteDatabaseHelper.getReadableDatabase();
        //关闭连接
        SqliteDatabase.close();
        Log.i("test", "数据库创建完毕");
        super.onCreate();
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public String getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(String current_user) {
        this.current_user = current_user;
    }



}
