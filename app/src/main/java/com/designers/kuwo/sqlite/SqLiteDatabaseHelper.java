package com.designers.kuwo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLiteDatabaseHelper extends SQLiteOpenHelper {

	public SqLiteDatabaseHelper(Context context,
			CursorFactory factory, int version) {
		super(context, DBConfig.DBNAME, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public SqLiteDatabaseHelper(Context context) {
		super(context, DBConfig.DBNAME, null, DBConfig.VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public SqLiteDatabaseHelper(Context context,int version) {
		super(context, DBConfig.DBNAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		/**
		 * 级联操作
		 */
		String sql_cascade = "PRAGMA foreign_keys=ON";
		sqLiteDatabase.execSQL(sql_cascade);
		/**
		 * 创建user表，用户的信息 ID 账号，密码  爱好  昵称  个性签名 性别 地区 头像
		 */
		String sql_user = "create table user(userId integer primary key autoincrement,account varchar(20),Password varchar(20),hobby varchar(30),nickname varchar(20),signature txt,sex varchar(10),area varchar(50),avatar varchar(100))";
		sqLiteDatabase.execSQL(sql_user);
		/**
		 * 创建音乐表，ID 歌名 歌手 存储路径 图片 歌词 详细信息
		 */
		String sql_songs = "create table songs(songId integer primary key autoincrement,songName varchar(20),singer varchar(20),songUri varchar(30),songImage varchar(100)，singlyrics text，information varchar(50))";
		sqLiteDatabase.execSQL(sql_songs);
		/**
		 * 创建collection表  ID 歌名（参照音乐的歌名） 账号（参照用户的账号）
		 */
		String sql_collection= "create table collection(collectionId integer primary key autoincrement, collectionSongName varchar(20) references songs(songName),collectionAccount varchar(20) references user(account))";
		sqLiteDatabase.execSQL(sql_collection);
		/**
		 * 创建歌单表，ID 歌单名  歌名（参照音乐的歌名） 账号（参照用户的账号）
		 */
		String sql_songList= "create table songList(songListId integer primary key autoincrement,songListName varchar(20) ,songListSongName varchar(20) references songs(songName),songListAccount varchar(20) references user(account))";
		sqLiteDatabase.execSQL(sql_songList);
		/**
		 * 创建专辑  ID  专辑名 歌名（参照音乐的歌名）  账号（参照用户的的账号）
		 */
		String sql_album = "create table album(albumId integer primary key autoincrement,albumName varchar(20),albumSongName varchar(20) references songs(songName),albumAccount varchar(20) references user(account))";
		sqLiteDatabase.execSQL(sql_album);
		/**
		 * 创建播放列表  ID 歌名（参照音乐的歌名）
		 */
		String sql_playList = "create table playList(playListId integer primary key autoincrement,palyListSongName varchar(20) references songs(songName))";
		sqLiteDatabase.execSQL(sql_playList);
		/**
		 * 创建最近播放列表  ID 歌名（参照音乐的歌名）
		 */
		String sql_recentlyPlayed = "create table recentlyPlayed(recentlyPlayedId integer primary key autoincrement,recentlyPalyedSongName varchar(20) references songs(songName))";
		sqLiteDatabase.execSQL(sql_recentlyPlayed);

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
