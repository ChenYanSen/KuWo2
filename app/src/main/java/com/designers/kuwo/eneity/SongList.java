package com.designers.kuwo.eneity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/13.
 */
public class SongList implements Serializable {

    private String songListName;
    private String songName;
    private String account;

    public SongList() {
        super();
    }

    public SongList(String songListName, String songName, String account) {
        this.songListName = songListName;
        this.songName = songName;
        this.account = account;
    }

    public String getSongListName() {
        return songListName;
    }

    public void setSongListName(String songListName) {
        this.songListName = songListName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "songListName='" + songListName + '\'' +
                ", songName='" + songName + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}

