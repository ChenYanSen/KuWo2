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
		// TODO Auto-generated method stub
		String sql = "create table user(uid integer primary key autoincrement,uname varchar(20),upwd varchar(20),sex varchar(10),province varchar(20),city varchar(20))";
        sqLiteDatabase.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
