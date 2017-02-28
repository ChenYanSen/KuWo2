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
         * ����user���û�����Ϣ ID �˺ţ�����  ����  �ǳ�  ����ǩ�� �Ա� ���� ͷ��
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
         * �����赥�� �赥��  ���� �˺ţ������û����˺ţ�
         */
        String sql_songList = "create table songList(songListName varchar(20) primary key," +
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_songList);

        /**
         * ����ר��    ר���� ���� �˺ţ������û��ĵ��˺ţ�
         */
        String sql_album = "create table album(albumName varchar(20) ," +
                "songName varchar(20),"+
                "singer varchar(20)," +
                "albumUri varchar(30)," +
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_album);

        //albumId INTEGER PRIMARY KEY autoincrement," + "、、
        //2017/2/21�ս�songUri ��Ϊ albumUri   ��

        /**
         * �������ֱ� ���� ���� �洢·�� ͼƬ ��� ��ϸ��Ϣ
         */
        String sql_songs = "create table songs(songName varchar(20) ," +
                "singer varchar(20)," +
                "singerUri varchar(30)," +
                "songUri varchar(30)," +
                "songImage varchar(100)," +
                "singlyrics text," +
                "albumUri varchar(30)," +
                "time varchar(20)," +
                "information varchar(50) ," +
                "folder varchar(50),"+
                "primary key(songName,singer))";
        sqLiteDatabase.execSQL(sql_songs);

        //2017/2/21����� singerUri  albumUri ��

        /**
         * ����collection��  ���� �˺ţ������û����˺ţ�
         */
        String sql_collection = "create table collection(SongName varchar(20)," +
                "singer varchar(20),"+
                "account varchar(20) references user(account))";
        sqLiteDatabase.execSQL(sql_collection);

        /**
         * ���������б� �������������ֵĸ���)�����б�����ظ�����
         */
        String sql_playList = "create table playList(songName varchar(20),"+
                "singer varchar(20),"+
                "songUri varchar(30),"+
                "songImage varchar(100),"+
                "singlyrics text)";
        sqLiteDatabase.execSQL(sql_playList);

        /**
         * ������������б�  ���� ����
         */
        String sql_recentlyPlayed = "create table recent(songName varchar(20)," +
                "singer varchar(20) ," +
                "songUri varchar(50)," +
                "primary key(songName,singer))";
        sqLiteDatabase.execSQL(sql_recentlyPlayed);
        /**
         * ����������Ƶ�����б�
         */
        String sql_videoPlay="create table videoPlay(videoName varchar (20),"+
                "videoUri varchar(50),"+
                "primary key(videoName,videoUri))";
        sqLiteDatabase.execSQL(sql_videoPlay);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
