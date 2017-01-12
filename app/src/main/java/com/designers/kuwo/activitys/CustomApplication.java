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
    private String current_user;
    private Uri imageUri;
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

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

    public String getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(String current_user) {
        this.current_user = current_user;
    }



}
