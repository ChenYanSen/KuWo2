package com.designers.kuwo.entity;

/**
 * Created by 跃 on 2017/2/22.
 * String sql_collection = "create table collection(SongName varchar(20) primary key," +
 * "singer varchar(20),"+
 * "songUri varchar(30),"+
 * "account varchar(20) references user(account))";
 * sqLiteDatabase.execSQL(sql_collection);
 */
public class ICollection {
    String songName;
    String singer;
    String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

}
