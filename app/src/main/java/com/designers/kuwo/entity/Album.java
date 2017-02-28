package com.designers.kuwo.entity;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public class Album {

    private String albumName = null;
    private String songName = null;
    private String albumUri = null;
    private String singer = null;
    private int songNum = 0;

    public Album() {
        super();
    }

    public Album(String albumName, String songName) {
        super();
        this.albumName = albumName;
        this.songName = songName;
    }

    public Album(String albumName, String songName, String albumUri) {
        this.albumName = albumName;
        this.songName = songName;
        this.albumUri = albumUri;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public int getSongNum() {
        return songNum;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumName='" + albumName + '\'' +
                ", songName='" + songName + '\'' +
                '}';
    }
}
