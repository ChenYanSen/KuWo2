package com.designers.kuwo.entity;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public class Singer {

    private String singer = null;
    private String singerUri = null;
    private String songName = null;
    private String songUri = null;
    private String songImage = null;
    private int songNum = 0;
    private String pinyinName = null;    //用于索引

    public Singer() {
        super();
    }

    public Singer(String singer) {
        super();
        this.singer = singer;
    }

    public Singer(String singer, String pinyinName) {
        super();
        this.singer = singer;
        this.pinyinName = pinyinName;
    }

    public Singer(String singer, int songNum, String singerUri) {
        super();
        this.singer = singer;
        this.songNum = songNum;
        this.singerUri = singerUri;

    }

    public Singer(String singer, String pinyinName, int songNum, String singerUri) {
        super();
        this.singer = singer;
        this.pinyinName = pinyinName;
        this.songNum = songNum;
        this.singerUri = singerUri;

    }

    public String getSinger() {
        return singer;
    }

    public String getSingerUri() {
        return singerUri;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongUri() {
        return songUri;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSingerUri(String singerUri) {
        this.singerUri = singerUri;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public int getSongNum() {
        return songNum;
    }

    //用于索引
    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getPinyinName() {
        return pinyinName;
    }


}

