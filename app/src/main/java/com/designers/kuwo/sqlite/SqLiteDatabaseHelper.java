package com.designers.kuwo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLiteDatabaseHelper extends SQLiteOpenHelper {

    public SqLiteDatabaseHelper(Context context,
                                CursorFactory factory, int version) {
        super(context, "music", factory, version);
        // TODO Auto-generated constructor stub
    }

    public SqLiteDatabaseHelper(Context context) {
        super(context, "music", null, 1);
        // TODO Auto-generated constructor stub
    }

    public SqLiteDatabaseHelper(Context context, int version) {
        super(context, "music", null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql_cascade = "PRAGMA foreign_keys=ON";
        sqLiteDatabase.execSQL(sql_cascade);

        /**
         * 创建user表，用户的信息 ID 账号，密码  爱好  昵称  个性签名 性别 地区 头像
         */
        String sql_user = "create table user(account varchar(20) primary key," +
                "password varchar(20)," +
                "hobby varchar(30)," +
                "nickname varchar(20)," +
                "signature txt," +
                "sex varchar(10)," +
                "area varchar(50)," +
                "avatarUri varchar(100))";
        sqLiteDatabase.execSQL(sql_user);

        /**
         * 创建歌单表 歌单名  歌名 账号（参照用户的账号）
         */
        String sql_songList = "create table songList(songListName varchar(20) primary key," +
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_songList);

        /**
         * 创建专辑    专辑名 歌名 账号（参照用户的的账号）
         */
        String sql_album = "create table album(albumName varchar(20) primary key," +
                "songName varchar(20),"+
                "singer varchar(20)," +
                "songUri varchar(30)," +
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_album);

        /**
         * 创建音乐表， 歌名 歌手 存储路径 图片 歌词 详细信息
         */
        String sql_songs = "create table songs(songName varchar(20) ," +
                "singer varchar(20)," +
                "songUri varchar(30)," +
                "songImage varchar(100)," +
                "singlyrics text," +
                "information varchar(50) ," +
                "primary key(songName,singer))";
        sqLiteDatabase.execSQL(sql_songs);

        /**
         * 创建collection表  歌名 账号（参照用户的账号）
         */
        String sql_collection = "create table collection(SongName varchar(20) primary key," +
                "singer varchar(20),"+
                "songUri varchar(30),"+
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_collection);

        /**
         * 创建播放列表 歌名（参照音乐的歌名)播放列表可以重复歌名
         */
        String sql_playList = "create table playList(songName varchar(20),"+
                "singer varchar(20),"+
                "songUri varchar(30),"+
                "songImage varchar(100),"+
                "singlyrics text)";
        sqLiteDatabase.execSQL(sql_playList);

        /**
         * 创建最近播放列表  歌名 歌手
         */
        String sql_recentlyPlayed = "create table recent(songName varchar(20)," +
                "singer varchar(20) ," +
                "songUri varchar(50)," +
                "primary key(songName,singer))";
        sqLiteDatabase.execSQL(sql_recentlyPlayed);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
