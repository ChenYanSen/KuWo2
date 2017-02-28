package com.designers.kuwo.utils;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

import com.designers.kuwo.biz.AlbumBiz;
import com.designers.kuwo.biz.SongBiz;
import com.designers.kuwo.biz.bizimpl.AlbumBizImpl;
import com.designers.kuwo.biz.bizimpl.SongBizImpl;
import com.designers.kuwo.entity.Album;
import com.designers.kuwo.entity.Song;
import com.designers.kuwo.sqlite.SqLiteDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public class CustomApplication extends Application {

    private String userName = "张三";
    private List<Song> songList = new ArrayList<Song>();
    private Song song;
    private Album album;
    private SongBiz songBiz = new SongBizImpl();
    private AlbumBiz albumBiz=new AlbumBizImpl();
    //private SongListBiz songListBiz = new SongListBizImpl();

    @Override
    public void onCreate() {
        SqLiteDatabaseHelper sqLiteDatabaseHelper = new SqLiteDatabaseHelper(getApplicationContext());
        //获取链接
        SQLiteDatabase sqliteDatabase = sqLiteDatabaseHelper.getReadableDatabase();
        //关闭连接
        sqliteDatabase.close();

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                song = new Song();
                album=new Album();
                String songUri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String albumName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String songTotalName = songUri.substring(songUri.lastIndexOf("/") + 1, songUri.indexOf("."));
                String singer = songTotalName.trim().substring(0, songTotalName.indexOf("-")).trim();
                String songName = songTotalName.trim().substring(songTotalName.indexOf("-") + 1, songTotalName.length()).trim();
                int time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String folder=songUri.substring(0,songUri.lastIndexOf("/"));
                song.setSinger(singer);
                song.setSongName(songName);
                song.setSongUri(songUri);
                song.setTime(time + "");
                song.setSingerUri("SingerUri");
                song.setAlbumUri("albumUri");
                song.setFolder(folder);
                album.setAlbumName(albumName);
                album.setSongName(songName);
                album.setAlbumUri("albumUri");
                album.setSinger(singer);
                songBiz.insert(getApplicationContext(), song);
                albumBiz.insert(getApplicationContext(),album);
                cursor.moveToNext();
            }
        }

        //songListBiz.insert(getApplicationContext(), new SongList("默认收藏", null, userName));
        super.onCreate();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Song> getSongList() {
        return songList;
    }
}
