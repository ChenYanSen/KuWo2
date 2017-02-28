package com.designers.kuwo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/12.
 */
public class Song implements Serializable {

    private String songName = null;
    private String singer = null;
    private String songUri = null;
    private String songImage = null;
    private String singLyrics = null;
    private String information = null;
    private String time = null;
    private String singerUri = null;
    private String albumUri = null;
    private String folder = null;

    public Song() {
    }

    public Song(String songName, String singer, String songUri, String songImage, String singLyrics, String information, String time) {
        this.songName = songName;
        this.singer = singer;
        this.songUri = songUri;
        this.songImage = songImage;
        this.singLyrics = singLyrics;
        this.information = information;
        this.time = time;
    }

    public Song(String songName, String singer, String songUri, String songImage, String singLyrics, String information, String time,
                String singerUri, String albumUri) {
        this.songName = songName;
        this.singer = singer;
        this.songUri = songUri;
        this.songImage = songImage;
        this.singLyrics = singLyrics;
        this.information = information;
        this.time = time;
        this.singerUri = singerUri;
        this.albumUri = albumUri;
    }

    public Song(String songName, String singer, String songUri, String songImage, String singLyrics, String information, String time,
                String singerUri, String albumUri, String folder) {
        this.songName = songName;
        this.singer = singer;
        this.songUri = songUri;
        this.songImage = songImage;
        this.singLyrics = singLyrics;
        this.information = information;
        this.time = time;
        this.singerUri = singerUri;
        this.albumUri = albumUri;
        this.folder = folder;
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

    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getSingLyrics() {
        return singLyrics;
    }

    public void setSingLyrics(String singLyrics) {
        this.singLyrics = singLyrics;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSingerUri(String singerUri) {
        this.singerUri = singerUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getSingerUri() {
        return singerUri;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", singer='" + singer + '\'' +
                ", songUri='" + songUri + '\'' +
                ", songImage='" + songImage + '\'' +
                ", singLyrics='" + singLyrics + '\'' +
                ", information='" + information + '\'' +
                ", time='" + time + '\'' +
                ", singerUri='" + singerUri + '\'' +
                ", albumUri='" + albumUri + '\'' +
                ", folder='" + folder + '\'' +
                '}';
    }
}
